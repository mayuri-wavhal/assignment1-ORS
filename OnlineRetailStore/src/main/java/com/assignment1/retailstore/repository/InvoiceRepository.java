package com.assignment1.retailstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.assignment1.retailstore.entity.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}
