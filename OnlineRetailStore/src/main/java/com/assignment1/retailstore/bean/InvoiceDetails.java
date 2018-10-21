package com.assignment1.retailstore.bean;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.assignment1.retailstore.util.InvoiceStatus;

public class InvoiceDetails {
	
	private Long InvoiceId;
	private List<ProductDetailsForInvoice> addedProducts;
	private List<ProductDetailsForInvoice> removedProducts;
	@NotNull
	private InvoiceStatus status;

	
	public Long getInvoiceId() {
		return InvoiceId;
	}
	public void setInvoiceId(Long InvoiceId) {
		this.InvoiceId = InvoiceId;
	}
	public InvoiceDetails() {
		super();
	}
	public List<ProductDetailsForInvoice> getAddedProducts() {
		return addedProducts;
	}
	public void setAddedProducts(List<ProductDetailsForInvoice> addedProducts) {
		this.addedProducts = addedProducts;
	}
	public List<ProductDetailsForInvoice> getRemovedProducts() {
		return removedProducts;
	}
	public void setRemovedProducts(List<ProductDetailsForInvoice> removedProducts) {
		this.removedProducts = removedProducts;
	}
	public InvoiceStatus getStatus() {
		return status;
	}
	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}

}
