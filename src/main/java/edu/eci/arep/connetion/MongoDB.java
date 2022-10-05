package edu.eci.arep.connetion;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;


public class MongoDB {


    public MongoDB(){
        //String connstr ="mongodb://localhost:40000/?retryWrites=true&w=majority";
        ConnectionString connectionString = new ConnectionString("mongodb+srv://dbAdmin:opDM6ufM0sEVURr6@cluster0.tnqcz.mongodb.net/?retryWrites=true&w=majority");
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
        MongoCollection<Document> customers = database.getCollection("customer");

        //Obtiene un iterable
        FindIterable<Document> iterable = customers.find();
        MongoCursor<Document> cursor = iterable.iterator();

        //Recorre el iterador obtenido del iterable
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }


        //Crea un documento BSON con el cliente
        Document customer = new Document("_id", new ObjectId());
        customer.append("firstName", "Daniel");
        customer.append("lastName", "Benavides");
        customer.append("_class", "co.edu.escuelaing.mongodemo.Customer.Customer");



        //Agrega el nuevo cliente a la colección
        customers.insertOne(customer);



        //Lee el iterable directamente para imprimir documentos
        for (Document d : iterable) {
            System.out.println(d);
        }

    }

    public void write(MongoClient mongoClient){



        }


}
