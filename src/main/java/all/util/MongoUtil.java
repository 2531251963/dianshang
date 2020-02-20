package all.util;


import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

/**
 * Demo class
 *
 * @author ls
 * @date 20-1-29
 */
public class MongoUtil {
    private static MongoDatabase mongoDatabase;
    public static MongoDatabase getConnect() {
        if (mongoDatabase==null){
            synchronized (MongoDatabase.class) {
            MongoClient mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder ->
                                    builder.hosts(Arrays.asList(
                                            new ServerAddress("127.0.0.1", 27017)
                                    )))
                            .build());
            mongoDatabase = mongoClient.getDatabase("dianshang");
        }
        }
        return mongoDatabase;

    }
}
