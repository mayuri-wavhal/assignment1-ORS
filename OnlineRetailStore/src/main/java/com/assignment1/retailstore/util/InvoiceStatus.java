package com.assignment1.retailstore.util;

public enum InvoiceStatus {
	IN_PROGRESS("In Progress"), COMPLETED("Completed");
	
	private String status;
	
	InvoiceStatus(final String status) {
		this.status = status;
	}

	public String getDescrip() {
		return status;
	}
}
