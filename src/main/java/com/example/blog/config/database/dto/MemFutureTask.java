package com.example.blog.config.database.dto;

import com.example.blog.config.database.dto.FutureTask;

public abstract class MemFutureTask<T> extends FutureTask<T> {
    public String type(){return "Mem";}
}
