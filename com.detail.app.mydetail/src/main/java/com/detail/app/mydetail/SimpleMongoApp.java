package com.detail.app.mydetail; 
import java.net.InetSocketAddress;


import org.bson.Document;
 
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;


import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class SimpleMongoApp {
	public static void main(String[] args) {
		/**in memory **/
		MongoServer server = new MongoServer(new MemoryBackend());
		InetSocketAddress serverAddress = server.bind();
		MongoClient client = new MongoClient(new ServerAddress(serverAddress));
		
		
		/** Mongo server external
	       String mongoHost = "localhost";
		   if (args.length > 0)
		      mongoHost = args[0];
		   // default port for MongoDB is 27017
		   MongoClient mongoClient = new MongoClient(mongoHost);
		   MongoDatabase db = mongoClient.getDatabase("mydb");
		   MongoCollection<Document> collection = db.getCollection("surgery");
		   Document doc = new Document("id", "id")
		     .append("id", "A patient");
		   collection.insertOne(doc);
		   System.out.println(collection.find().first().get("id"));
		   mongoClient.close();
		   **/
	}
}