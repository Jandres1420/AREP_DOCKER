package edu.eci.arep.service;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.Date;

public class LogServiceImpl implements LogService {
    private static MongoCollection<Document> customers;
    private static FindIterable<Document> iterable;

    public  LogServiceImpl(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:admin@cluster0.urkiqdr.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        //Obtener objeto base de datos. Si no existe lo crea
        MongoDatabase database = mongoClient.getDatabase("ARSWDB");
        //Obtener objeto colección. Si no existe lo crea
         customers = database.getCollection("customer");

        //Obtiene un iterable
        iterable = customers.find();

    }
    @Override
    public String getCurrent() {
        String allUsers = "";
        FindIterable<Document> currentUsers = customers.find();
        for(Document document:currentUsers){
            allUsers+="<tr><td>"
            + document.get("firstName").toString()
            + "<p>" + document.get("fecha").toString()
            + "<p>"
            + "</tr></td>";
        }
        return allUsers;
    }

    @Override
    public void insertDocument(String firstName) {
        Document document = new Document("_id",new ObjectId());
        document.append("firstName",firstName);
        document.append("fecha", new Date().toString());
        customers.insertOne(document);
    }
}
