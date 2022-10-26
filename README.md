# LAB 4 AREP
## Author
**Juan Andrés Pico**
## Fecha
**19/10/2022**
## Introducción
En ente laboratorio se busca entender sobre docker , contenedores y subirlo a AWS. Para esto se creo un servicio en Spark donde este tenga a conexion a mongoDB para así poder hacer nuevos inserts
## Implementacion
Se creo una clase en java llamada `SparkWebServer.java` el cual crea los endpoints que se van a llamar por la clase `UserController.java`

```java
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

```

Donde se ve que hay una instancia `LogService.java` a una interfaz la cual es la conexión a MongoDB que tiene sus respectivos metodos para buscar e insertar a la base de datos no relacional , se implemento esta interfaz en la clase 

`LogServiceImpl.java` para una correcta extensibilidad de codigo.

![](/img/extensible%20carpetas.png)

```java
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
```

Para que por ultimo tener una pagina sencilla html que en este caso se llama index y probar que funcionen los llamados al mongoDB

![](/img/local.png)

Para así comprobar que se mete la información a la base de datos y mostrar los anteriores registros.
![](/img/local%20post.png)

Creamos un contenedor el cual tenga 3 imagenes la cuales apunten a los puertos 34000,34001,340002
![](/img/images.png)

Y probamos que corra normalmente el codigo en estos puertos 
![](/img/imagen%201.png)

Ya con el correcto funcionamiento desde las imagenes montamos el contenedor a docker hub

![](/img/docker%20hub.png)

Con esto ya echo se creo la instancia en AWS y se conecta desde git bash

![](/img/conexion%20aws.png)

Se descargan los paquetes en la maquina virtual y se corre docker con el contenedor ya creado y además el repositorio ya anteriormente subido a 
docker hub.

![](/img/web%20aws%20.png)

Y su respectiva inserción a la base de datos 
![](/img/post%20aws.png)
