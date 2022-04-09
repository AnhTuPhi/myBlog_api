package com.example.blog.config.database.dto;

import com.example.blog.config.database.dto.FutureTask;

public abstract class FileFutureTask<T> extends FutureTask<T> {
    public String type(){return "FILE";}
}
