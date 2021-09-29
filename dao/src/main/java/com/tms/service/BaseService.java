package com.tms.service;

import com.tms.config.DBConfig;
import com.tms.config.DBConfigMap;
import com.tms.dto.DBResponse;
import com.tms.dto.FutureTask;
import com.tms.exception.ConfigNotFoundException;
import com.tms.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public abstract class BaseService {

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    DBConfigMap dbConfigMap;

    protected DBConfig getConfig(String fucName) throws ConfigNotFoundException {
        DBConfig lookup = dbConfigMap.lookup(fucName);
        if (lookup == null) {
            throw new ConfigNotFoundException(fucName,"Can not get config for method: " + fucName);
        }
        return lookup;
    }


    protected <T> DBResponse<T> query(String _sessionId, DBConfig config, FutureTask<DBResponse<T>> memTask, FutureTask<DBResponse<T>> dbTask){
        if (config.isDBQuery() || !config.isFallBack()) {
            //execute 1 task with no fallback
            AppUtils.printLine(logger, _sessionId, "Query from database without fallback");
            return executeTask(_sessionId, config, dbTask, null);

        } else {
            //execute fallback in case can not get from first task
            AppUtils.printLine(logger, _sessionId, "Query from memory and fallback db");
            return executeTask(_sessionId, config, memTask, dbTask);
        }
    }

    /**
     * because we not need to apply timeout so just call it directly
     * @param _sessionId
     * @param config
     * @param fileTask
     * @param dbTask
     * @return
     */
    protected DBResponse insert(String _sessionId, DBConfig config,  FutureTask<DBResponse> fileTask, FutureTask<DBResponse> dbTask){
        DBResponse rs = new DBResponse();
        //try to call database
        try {
            logger.info(_sessionId + ": Call insert to database");
            rs = dbTask.get();
            if (config.getAlwaysWriteFile()) {
                logger.info(_sessionId +  ": Row is updated on db, so we write to file");
                try {
                    //String err = rs.getErrorMsg();
                    //no need response
                    DBResponse fileRs = fileTask.get();
                    //for tracking write file error because we must override db msg (wrong way)
                    if (fileRs.getErrorCode() > 0) {
                        logger.error(_sessionId + ": Fail write on file: " + fileRs.getErrorMsg());
                    }
                    //should keep errorMsg from db
                    //rs.setErrorMsg(err);
                } catch (Exception e1) {
                    logger.error(_sessionId, "Fail write on file", e1);
                    //rs.setErrorCode(1);
                    //rs.setErrorMsg(e1.getMessage());
                }
            }
        } catch (Exception e) {
            //fallback immediately
            logger.error(_sessionId, "Fail write on database, try write to file", e);
            rs.setErrorCode(1);
            rs.setErrorMsg(e.getMessage());
            try {
                fileTask.get();
            } catch (Exception e1) {
                logger.error(_sessionId, "Fail write on file", e1);
                //rs.setErrorCode(1);
                //rs.setErrorMsg(e1.getMessage());
            }

        }
        return rs;
    }

    private <T> DBResponse executeTask(String _sessionId, DBConfig cf, FutureTask<DBResponse<T>> firstTask, FutureTask<DBResponse<T>> fallbackTask) {

        DBResponse result = null;
        long firstTime = cf.getMemTimeout(), lastTime = cf.getDBTimeout(), totalTime = firstTime + lastTime, currTime = System.currentTimeMillis();
        //execute first task
        CompletableFuture<DBResponse<T>> firstFutureTask = CompletableFuture.supplyAsync(firstTask, executor);
        result = waitAndGetResult(_sessionId, firstFutureTask, firstTask.type(), firstTime);
        AppUtils.printLine(logger, _sessionId,  "Try on first task: " + firstTask.type() + ", with timeout: " + firstTime +
                ", exeTime: " + (System.currentTimeMillis() - currTime) + ", hasResult: " + (result != null));


        //execute fallback task if first task is null and fallback exist
        if (result == null && fallbackTask != null) {
            long remainTimeout = totalTime - (System.currentTimeMillis() - currTime);
            currTime = System.currentTimeMillis();
            CompletableFuture<DBResponse<T>> secondFutureTask = CompletableFuture.supplyAsync(fallbackTask, executor);
            result = waitAndGetResult(_sessionId, secondFutureTask, fallbackTask.type(), remainTimeout);
            AppUtils.printLine(logger, _sessionId, "Try on fallback task: " + fallbackTask.type() + ", with timeout: " + remainTimeout +
                    ", exeTime: " + (System.currentTimeMillis() - currTime) + ", hasResult: " + (result != null));
        }

        return result;
    }

    private <T> DBResponse<T> waitAndGetResult(String _sessionId, CompletableFuture<DBResponse<T>> task, String type, long timeout) {
        DBResponse<T> result = null;
        try {
            result = task.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            task.cancel(true);
            logger.error(_sessionId + type + " task is timeout", e);
        } catch (Exception e) {
            logger.error(_sessionId + type + " task has exception ", e);
        }
        return result;
    }

}
