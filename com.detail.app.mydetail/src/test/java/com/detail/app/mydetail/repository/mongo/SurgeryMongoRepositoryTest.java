package com.detail.app.mydetail.repository.mongo;

import static com.detail.app.mydetail.repository.mongo.SurgeryMongoRepository.SURGERY_DB_NAME;
import static com.detail.app.mydetail.repository.mongo.SurgeryMongoRepository.SURGERY_COLLECTION_NAME;
import static org.assertj.core.api.Assertions.*;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.assertj.core.api.Assert;
import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.detail.app.mydetail.model.Surgery;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class SurgeryMongoRepositoryTest {
	
	private static MongoServer server;
	private static InetSocketAddress serverAddress;
	private MongoClient client;
	private SurgeryMongoRepository surgeriesRepository;
	private MongoCollection<Document> surgeryCollection;
	
	private List<Surgery> readAllSurgeriesFromDatabase(){
		return StreamSupport.
				stream(surgeryCollection.find().spliterator(),false)
				.map(d -> new Surgery("" + d.get("id"), "" + d.get("patientName")))
				.collect(Collectors.toList());
	}
	
	private void addTestSurgeryToDatabase(String id, String patientName) {
		surgeryCollection.insertOne(new Document().append("id", id).append("patientName", patientName));
	}
	
	@BeforeClass
	public static void setupServer() {
		server = new MongoServer(new MemoryBackend());
		serverAddress = server.bind();
	}
	@AfterClass
	public static void shutdownServer() {
		server.shutdown();
	}
	@Before
	public void setup() {
		client = new MongoClient(new ServerAddress(serverAddress));
		surgeriesRepository = new SurgeryMongoRepository(client);
		MongoDatabase database = client.getDatabase(SURGERY_DB_NAME);
		database.drop();
		surgeryCollection = database.getCollection(SURGERY_COLLECTION_NAME);
	}
	
	@After
	public void tearDown() {
		client.close();
	}
	@Test
	public void testFindAllWhenDatabaseIsEmpty() {
		assertThat(surgeriesRepository.findAll()).isEmpty();
	}
	@Test
	public void testFindAllWhenDatabaseIsNotEmpty() {
		addTestSurgeryToDatabase("1", "Paziente1");
		addTestSurgeryToDatabase("2", "Paziente2");
		assertThat(surgeriesRepository.findAll())
			.containsExactly(
					new Surgery("1","Paziente1"),
					new Surgery("2","Paziente2"));
	}
	
	@Test
	public void testFindByIdNotFound() {
		assertThat(surgeriesRepository.findById("1")).isNull();
	}
	@Test
	public void testFindByIdFound() {
		addTestSurgeryToDatabase("1", "Paziente1");
		addTestSurgeryToDatabase("2", "Paziente2");
		assertThat(surgeriesRepository.findById("2")).isEqualTo(new Surgery("2", "Paziente2"));
	}
	
	@Test
	public void testSave() {
		Surgery surgery = new Surgery("1", "Paziente1");
		surgeriesRepository.save(surgery);
		assertThat(readAllSurgeriesFromDatabase()).containsExactly(surgery);
	}
	
	@Test
	public void testDelete() {
		addTestSurgeryToDatabase("1", "Paziente1");
		surgeriesRepository.delete("1");
		assertThat(readAllSurgeriesFromDatabase()).isEmpty();
	}

}
