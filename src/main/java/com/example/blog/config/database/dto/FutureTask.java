package com.example.blog.config.database.dto;

import java.util.function.Supplier;

public abstract class FutureTask<T> implements Supplier<T> {
    public abstract String type();
}
