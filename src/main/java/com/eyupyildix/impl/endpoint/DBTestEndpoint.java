package com.eyupyildix.impl.endpoint;

import com.eyupyildix.config.Config;
import com.eyupyildix.endpoint.Endpoint;
import com.eyupyildix.endpoint.EndpointException;
import com.eyupyildix.endpoint.EndpointMeta;
import com.eyupyildix.endpoint.HttpMethod;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.sun.net.httpserver.HttpExchange;
import org.bson.Document;

@EndpointMeta(path="/db", supportedMethods= HttpMethod.GET, authNeeded=false)
public class DBTestEndpoint extends Endpoint {

    private final String connectionString;

    @Inject
    public DBTestEndpoint(@Named("bootstrap-config") Config config) {
        connectionString = String.format("mongodb+srv://%s:%s@cluster0.rpcjjmy.mongodb.net/?retryWrites=true&w=majority", config.getValue("db_name"), config.getValue("db_password"));
    }

    @Override
    public Object responseGet(HttpExchange exchange) throws EndpointException {
        try (MongoClient client = MongoClients.create(connectionString)) {
            try {
                MongoDatabase database = client.getDatabase("restAPI");
                MongoCollection<Document> collection = database.getCollection("info");
                Document document = collection.find(Filters.eq("index", 0)).first();
                if (document == null) {
                    return "None";
                }

                collection.updateOne(Filters.eq("index", 0), Updates.set("connects", (int)document.get("connects") + 1));
                return "Connects: " + ((int) document.get("connects") + 1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return "None";
    }

}
