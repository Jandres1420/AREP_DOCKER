package edu.eci.arep;

import edu.eci.arep.connetion.MongoDB;
import static spark.Spark.get;
import static spark.Spark.port;

public class SparkWebServer {

    public static void main(String... args){
        port(getPort());
        get("hello", (req,res) -> "Hello Docker!");
        MongoDB mongoDB = new MongoDB();

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}