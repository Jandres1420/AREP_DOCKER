package edu.eci.arep.controller;

import edu.eci.arep.service.LogService;
import edu.eci.arep.service.LogServiceImpl;

import static spark.Spark.get;

public class UserController {
    private static LogService logService = new LogServiceImpl();

    public static String getMessage(spark.Request request, spark.Response response){
        String message = logService.getCurrent();
        return message;
    }

    public static String insertMessage(spark.Request request, spark.Response response){
        logService.insertDocument(request.queryParams("firstname"));
        return getMessage(request,response);
    }
}
