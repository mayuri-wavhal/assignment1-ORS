package com.assignment1.retailstore.bean;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.assignment1.retailstore.util.Category;

public class ProductDetails {

	@NotNull
	private String barCode;
	@NotNull
	private String name;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Category productCategory;
	@NotNull
	private double price;

	public ProductDetails() {
		super();
	}
	public ProductDetails(String barCode, double price, String name, Category productCategory) {
		super();
		this.barCode = barCode;
		this.name = name;
		this.productCategory = productCategory;
		this.price = price;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(Category productCategory) {
		this.productCategory = productCategory;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
