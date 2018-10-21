package com.assignment1.retailstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.assignment1.retailstore.entity.Product;


public interface ProductRepository extends CrudRepository<Product, Long>{
	
	public List<Product> findByBarCode(String barCode);

}
