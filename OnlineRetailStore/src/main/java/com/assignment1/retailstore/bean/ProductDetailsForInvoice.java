package com.assignment1.retailstore.bean;

import javax.validation.constraints.NotNull;

public class ProductDetailsForInvoice {

	@NotNull
	private String barCode;
	private int quantity;

	public ProductDetailsForInvoice() {
		super();
	}
	public ProductDetailsForInvoice(String barCode, int quantity) {
		super();
		this.barCode = barCode;
		this.quantity = quantity;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
