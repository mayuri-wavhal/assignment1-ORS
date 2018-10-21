package com.assignment1.retailstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.assignment1.retailstore.entity.InvoiceItem;

public interface LineItemRepository extends CrudRepository<InvoiceItem, Long> {
	
	public List<InvoiceItem> findByProduct_id(long prodId);

}
