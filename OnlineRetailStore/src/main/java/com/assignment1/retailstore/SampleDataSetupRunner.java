package com.assignment1.retailstore;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.assignment1.retailstore.bean.InvoiceDetails;
import com.assignment1.retailstore.bean.ProductDetails;
import com.assignment1.retailstore.bean.ProductDetailsForInvoice;
import com.assignment1.retailstore.entity.Invoice;
import com.assignment1.retailstore.service.InvoiceService;
import com.assignment1.retailstore.service.ProductService;
import com.assignment1.retailstore.util.InvoiceStatus;
import com.assignment1.retailstore.util.Category;

@Component
public class SampleDataSetupRunner implements CommandLineRunner {

	@Autowired
	private InvoiceService billService;

	@Autowired
	private ProductService productService;

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void run(String... arg0) throws Exception {
		logger.info("Inside Runner..");
		setUpProductData();
		setupBillData();
		logger.info("Exiting Runner.. ");
	}

	public void setupBillData() {
		// create a new Bill to update information.
		Invoice o1 = billService.createInvoice();

		Long billId = o1.getId();
		InvoiceDetails billUpdateInfo = new InvoiceDetails();
		List<ProductDetailsForInvoice> productsToBeAdded = new ArrayList<>();
		List<ProductDetailsForInvoice> productsToBeRemoved = new ArrayList<>();

		productsToBeAdded.add(new ProductDetailsForInvoice("ABC-abc-0001", 2));
		productsToBeAdded.add(new ProductDetailsForInvoice("ABC-abc-0002", 2));
		productsToBeAdded.add(new ProductDetailsForInvoice("ABC-abc-0003", 2));
		productsToBeAdded.add(new ProductDetailsForInvoice("ABC-abc-0004", 2));
		billUpdateInfo.setAddedProducts(productsToBeAdded);
		billUpdateInfo.setRemovedProducts(productsToBeRemoved);
		billUpdateInfo.setStatus(InvoiceStatus.COMPLETED);

		logger.info("billUpdateInfo = " + billUpdateInfo);
		billUpdateInfo.setInvoiceId(billId);
		billService.updateInvoice(billUpdateInfo);
		Invoice retrieveUpdatedbill = billService.getInvoiceById(o1.getId());
		logger.info("retrieveUpdatedbill = " + retrieveUpdatedbill.getNoOfItems() + "  value ="
				+ retrieveUpdatedbill.getTotalValue());

	}

	private void setUpProductData() {
		productService.createProduct(new ProductDetails("ABC-abc-0001", 20.0, "Tomato", Category.A));
		productService.createProduct(new ProductDetails("ABC-abc-0002", 30.0, "Onion", Category.B));
		productService.createProduct(new ProductDetails("ABC-abc-0003", 40.0, "Potato", Category.C));
		productService.createProduct(new ProductDetails("ABC-abc-0004", 50.0, "Bread", Category.A));
		productService.createProduct(new ProductDetails("ABC-abc-0005", 60.0, "Apples", Category.B));
		productService.createProduct(new ProductDetails("ABC-abc-0006", 70.0, "Banana", Category.C));
		productService.createProduct(new ProductDetails("ABC-abc-0007", 80.0, "Strawberry", Category.A));
		productService.createProduct(new ProductDetails("ABC-abc-0008", 90.0, "Apricot", Category.B));
		productService.createProduct(new ProductDetails("ABC-abc-0009", 100.0, "Raisins", Category.C));
		productService.createProduct(new ProductDetails("ABC-abc-0010", 110.0, "CashewNut", Category.A));
	}
}
