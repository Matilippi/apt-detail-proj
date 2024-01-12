package com.detail.app.mydetail.repository.mongo;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.detail.app.mydetail.model.Surgery;
import com.detail.app.mydetail.repository.SurgeriesRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class SurgeryMongoRepository implements SurgeriesRepository{
	public static final String SURGERY_DB_NAME = "surgeries";
	public static final String SURGERY_COLLECTION_NAME = "surgery";
	private MongoCollection<Document> surgeryCollection;
	
	public SurgeryMongoRepository(MongoClient client) {
		surgeryCollection = client.getDatabase(SURGERY_DB_NAME).getCollection(SURGERY_COLLECTION_NAME);
	}

	@Override
	public List<Surgery> findAll() {
		return StreamSupport
				.stream(surgeryCollection.find().spliterator(), false)
	           .map(this::fromDocumentToSurgery)
	           .collect(Collectors.toList());
	}
	private Surgery fromDocumentToSurgery(Document d) {
		return new Surgery(""+ d.get("id"), ""+ d.get("patientName"));
	}
	@Override
	public Surgery findById(String id) {
		Document d = surgeryCollection.find(Filters.eq("id",id)).first();
		if(d != null)
			return fromDocumentToSurgery(d);
		return null;
	}

	@Override
	public void save(Surgery surgery) {
		surgeryCollection.insertOne(
				new Document()
				.append("id", surgery.getId())
				.append("patientName", surgery.getPatientName())
				);
		
	}

	@Override
	public void delete(String id) {
		surgeryCollection.deleteOne(Filters.eq("id",id));
		
	}
}