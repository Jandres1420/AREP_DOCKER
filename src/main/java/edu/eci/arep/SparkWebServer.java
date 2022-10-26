package edu.eci.arep;

import static spark.Spark.*;


import edu.eci.arep.controller.UserController;

public class SparkWebServer {

    public static void main(String... args){
        port(getPort());
        staticFileLocation("/public");
        get("/data", (request, response) -> UserController.insertMessage(request,response));


    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }


}