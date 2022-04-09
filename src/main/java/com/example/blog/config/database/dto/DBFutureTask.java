package com.example.blog.config.database.dto;

public abstract class DBFutureTask<T> extends FutureTask<T> {
    public String type(){return "DB";}
}
