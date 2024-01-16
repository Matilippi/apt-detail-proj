package com.detail.app.mydetail.repository.mongo;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.detail.app.mydetail.model.Surgery;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class SurgeryMongoRepositoryTestcontainersIT {
	@SuppressWarnings("rawtypes")
	@ClassRule
	public static final MongoDBContainer mongo = 
		new MongoDBContainer("mongo:4.4.3");
	private MongoClient client;
	private SurgeryMongoRepository surgeryMongoRepository;
	private MongoCollection<Document> surgeryCollection;
	
	@Before
	public void setup() {
		client = new MongoClient(
				new ServerAddress(
						mongo.getContainerIpAddress(),
						mongo.getMappedPort(27017)));
		surgeriesRepository = new surgeryMongoRepository(client);
		MongoDatabase database = client.getDatabase(SURGERIES_DB_NAME);
		database.drop();
		surgeryCollection = database.getCollection(SURGERY_COLLECTION_NAME);
	}
	
	@After
	public void tearDown() {
		client.close();
	}
	
	
	@Test
	public void testFindAll() {
		addTestSurgeryToDatabase("1", "Paziente1");
		addTestSurgeryToDatabase("2", "Paziente2");
		assertThat(surgeriesRepository.findAll())
			.containsExactly(
					new Surgery("1","Paziente1"),
					new Surgery("2","Paziente2"));
	}
	
	@Test
	public void testFindById() {
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