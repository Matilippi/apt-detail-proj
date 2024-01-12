package com.detail.app.mydetail.view;

import java.util.List;

import com.detail.app.mydetail.model.Surgery;

public interface SurgeryView {

	void showAllSurgeries(List<Surgery> surgeries);

	void showError(String message, Surgery surgery);

	void surgeryAdded(Surgery surgery);

	void surgeryRemoved(Surgery surgery);

}