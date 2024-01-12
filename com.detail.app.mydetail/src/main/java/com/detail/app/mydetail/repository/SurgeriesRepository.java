package com.detail.app.mydetail.repository;

import java.util.List;

import com.detail.app.mydetail.model.Surgery;

public interface SurgeriesRepository {
	public List<Surgery> findAll();
	public Surgery findById(String id);
	public void save(Surgery surgery);
	public void delete(String id);	
}
