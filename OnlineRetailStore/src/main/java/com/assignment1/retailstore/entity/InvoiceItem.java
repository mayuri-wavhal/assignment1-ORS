package com.assignment1.retailstore.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	private Product product;
	
	private long quantity;

	public InvoiceItem() {
		super();
	}
	public InvoiceItem(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

}
