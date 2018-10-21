package com.assignment1.retailstore.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.assignment1.retailstore.util.InvoiceStatus;


@Entity
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private int noOfItems;
	
	private double totalCost;

	private double totalTax;

	private double totalValue;
	
	private Date date;

	@NotNull
	@Enumerated(EnumType.STRING)
	private InvoiceStatus invoiceStatus;

	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private List<InvoiceItem> items;

	public Invoice() {
		super();
	}
	public Invoice(double totalValue, int noOfItems, InvoiceStatus invoiceStatus) {
		super();
		this.totalValue = totalValue;
		this.noOfItems = noOfItems;
		this.invoiceStatus = invoiceStatus;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getNoOfItems() {
		return noOfItems;
	}
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(InvoiceStatus billStatus) {
		this.invoiceStatus = billStatus;
	}
	public List<InvoiceItem> getItems() {
		return items;
	}
	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}

}
