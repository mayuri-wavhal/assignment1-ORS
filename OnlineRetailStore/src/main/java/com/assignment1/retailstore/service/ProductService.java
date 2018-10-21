package com.assignment1.retailstore.service;

import com.assignment1.retailstore.bean.ProductDetails;
import com.assignment1.retailstore.entity.Product;

public interface ProductService {

	public Iterable<Product> getAllProducts();
	
	public Product getProductById(Long id);

	public Product createProduct(ProductDetails productDetails);
}
