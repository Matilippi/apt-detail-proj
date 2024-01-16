package com.detail.app.mydetail.controller

import com.detail.app.mydetail.model.Surgery;

public class SurgeryControllerIT{
	@Mock 
	private SurgeryView surgeryView;
	private SurgeryRepository surgeryRepository;
	private SurgeryController surgeryController;
	private AutoCloseable closeable;
	private static int mongoPort = Integer.parseInt(System.getProperty("mongo.port", "27017"))
	
	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		surgeryRepository = new SurgeryMongoRepository(
				new MongoClient(
						new ServerAddress("localhost", mongoPort)));
		for (Surgery surgery :
			surgeryRepository.findAll()) {
			surgeryRepository.delete(surgery.getId());
		}
		surgeryController = new SurgeryController(surgeryView, surgeryRepository);
	}
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
	@Test
	public void testAllSurgeries() {
		Surgery surgery = new Surgery("1","Paziente1");
		surgeryRepository.save(surgery);
		surgeryController.allSurgeries();
		verify(surgeryView).showAllSurgeries(asList(surgery));
	}
	
	
	@Test
	public void testNewSurgery() {
		Surgery surgery = new Surgery("1", "Paziente1");
		surgeryController.newSurgery(surgery);
		verify(surgeryView).surgeryAdded(surgery);
	}
	
	@Test
	public void testDeleteSurgery() {
		Surgery surgeryToDelete = new Surgery("1","Paziente1");
		surgeryRepository.save(surgeryToDelete);
		surgeryController.deleteSurgery(surgeryToDelete);
		verify(surgeryView).surgeryRemoved(surgeryToDelete);
	}
}