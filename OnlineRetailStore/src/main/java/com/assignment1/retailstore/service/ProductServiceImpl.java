package com.assignment1.retailstore.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment1.retailstore.bean.ProductDetails;
import com.assignment1.retailstore.entity.Product;
import com.assignment1.retailstore.exception.CustomNotFoundException;
import com.assignment1.retailstore.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Iterable<Product> getAllProducts() {
		logger.info("Fetch all products");
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		logger.info("Fetch product by id");
		return productRepository
		          .findById(id)
		          .orElseThrow(() -> new CustomNotFoundException("Product not found"));
	}
	
	public Product createProduct(ProductDetails productDetails) {
		logger.info("Create new Product");
		Product product = new Product();
		product.setBarCode(productDetails.getBarCode());
		product.setName(productDetails.getName());
		product.setCategory(productDetails.getProductCategory());
		product.setPrice(productDetails.getPrice());
		product = productRepository.save(product);
		logger.info(product.getName().toUpperCase() + " product is created successfully!");
		return product;

	}

}
