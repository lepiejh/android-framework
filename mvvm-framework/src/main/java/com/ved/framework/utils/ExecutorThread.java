package com.ved.framework.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorThread {
    private final ExecutorService executors = Executors.newCachedThreadPool();

    private final static class Inner {
        private final static ExecutorThread instance = new ExecutorThread();
    }

    public static ExecutorThread newInstance() {
        return Inner.instance;
    }

    public ExecutorService getGlobalThreadPool(){
        return executors;
    }
}
