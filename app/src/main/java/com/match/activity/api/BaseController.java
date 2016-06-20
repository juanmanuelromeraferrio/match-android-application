package com.match.activity.api;

/**
 * Created by Juan Manuel Romera on 21/5/2016.
 */
public interface BaseController {

    void initTask();

    void finishTask();

    void onResult(Object result);
}
