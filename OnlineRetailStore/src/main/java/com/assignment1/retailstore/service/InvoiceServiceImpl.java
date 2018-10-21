package com.assignment1.retailstore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.assignment1.retailstore.bean.InvoiceDetails;
import com.assignment1.retailstore.bean.ProductDetailsForInvoice;
import com.assignment1.retailstore.entity.Invoice;
import com.assignment1.retailstore.entity.InvoiceItem;
import com.assignment1.retailstore.entity.Product;
import com.assignment1.retailstore.exception.CustomNotFoundException;
import com.assignment1.retailstore.repository.InvoiceRepository;
import com.assignment1.retailstore.repository.LineItemRepository;
import com.assignment1.retailstore.repository.ProductRepository;
import com.assignment1.retailstore.util.Category;
import com.assignment1.retailstore.util.InvoiceStatus;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private LineItemRepository lineItemRepository;

	final Logger logger = LoggerFactory.getLogger(getClass());

	public Iterable<Invoice> getAllInvoices() {
		logger.info("Returning all invoices");
		return invoiceRepository.findAll();
	}

	public Invoice getInvoiceById(Long id) {
		logger.info("Returning an invoice by id");
		return invoiceRepository
		          .findById(id)
		          .orElseThrow(() -> new CustomNotFoundException("Invoice not found!"));
	}
	
	@Override
	public Invoice createInvoice() {
		logger.info("Creating a new invoice");
		Invoice newInvoice = new Invoice(0.0, 0, InvoiceStatus.IN_PROGRESS);
		newInvoice.setDate(new Date());
		newInvoice = invoiceRepository.save(newInvoice);
		logger.info("Successfully created an invoice" + newInvoice.getId());
		return newInvoice;

	}

	@SuppressWarnings("unused")
	@Override
	public Invoice updateInvoice(InvoiceDetails invoicedetails) {
		logger.info("Updating an invoice by id");
		Long invoiceId = invoicedetails.getInvoiceId();
		if (null == invoicedetails) {
			throw new CustomNotFoundException("No information to update for an invoice: " + invoiceId);
		}
		Invoice oldInvoice = 	invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new CustomNotFoundException("Invoice not found!"));
		if (oldInvoice == null) {
			throw new CustomNotFoundException("Invoice does not exists! - " + invoiceId);
		}
		List<ProductDetailsForInvoice> products = null;
		if (null != invoicedetails.getAddedProducts()) {
			products = invoicedetails.getAddedProducts();
			products.forEach(p -> addProduct(oldInvoice, p));
		}
		if (null != invoicedetails.getRemovedProducts()) {
			products = invoicedetails.getRemovedProducts();
			products.forEach(p -> removeProduct(oldInvoice, p.getBarCode()));
		}
		oldInvoice.setInvoiceStatus(invoicedetails.getStatus());
		calculateTotalAmount(oldInvoice);
		return oldInvoice;
	}

	private Invoice addProduct(Invoice invoice, ProductDetailsForInvoice product) {
		logger.debug("Adding product to invoice");
		Product selectedProduct = isProductExists(product.getBarCode());
		InvoiceItem item = new InvoiceItem(selectedProduct, product.getQuantity());
		lineItemRepository.save(item);
		List<InvoiceItem> currentItems = invoice.getItems();
		if (null != currentItems) { 
			InvoiceItem existingItem = getItemWithBarCode(product.getBarCode(), currentItems);
			if (null == existingItem) {
				invoice.getItems().add(item);
			} else {
				long newQty = existingItem.getQuantity() + product.getQuantity();
				existingItem.setQuantity(newQty); // increment the quantity
			}
		} else {
			currentItems = new ArrayList<>();
			currentItems.add(item);
			invoice.setItems(currentItems);
		}
		invoiceRepository.save(invoice);
		logger.debug("Product added to invoice : " + item.getId());
		return invoice;
	}

	private InvoiceItem getItemWithBarCode(String barCode, List<InvoiceItem> currentLineItems) {
		Optional<InvoiceItem> item = currentLineItems.stream().filter(i -> i.getProduct().getBarCode().equals(barCode)).findFirst();
		if(item.isPresent())
			return item.get();
		return null;
	}

	private Invoice removeProduct(Invoice invoice, String barCode) {
		logger.debug("Removing product from invoice");
		List<InvoiceItem> currentItems = invoice.getItems();
		isProductExists(barCode);
		if (!CollectionUtils.isEmpty(currentItems)) {
			InvoiceItem item = getItemWithBarCode(barCode, currentItems);
			if (null == item) {
				logger.info("Product does not exist in current list of products.");
				throw new CustomNotFoundException("Product does not exist in current list of products.");
			}
			currentItems.remove(item);
			invoice.setItems(currentItems);
			invoiceRepository.save(invoice);
		}
		logger.debug("Product removed from invoice : " + invoice.getId());
		return invoice;
	}
	
	private void calculateTotalAmount(Invoice invoice) {
		logger.info("Calculating total amount");
		int noOfItems = 0;
		double totalValue = 0;
		double totalCost = 0;

		if (!CollectionUtils.isEmpty(invoice.getItems())) {
			List<InvoiceItem> items = invoice.getItems();
			for (InvoiceItem item : items) {
				double saleValue = calculatePriceWithTaxForItem(item);
				totalValue += saleValue;
				totalCost += item.getQuantity() * item.getProduct().getPrice();
				noOfItems++;
			}
		}
		invoice.setNoOfItems(noOfItems);
		invoice.setTotalValue(totalValue);
		invoice.setTotalCost(totalCost);
		invoice.setTotalTax(totalValue - totalCost);
		invoiceRepository.save(invoice);
	}

	private double calculatePriceWithTaxForItem(InvoiceItem item) {
		logger.info("Calculating sale price for each item");
		long quantity = item.getQuantity();
		Category category = item.getProduct().getCategory();
		double rate = item.getProduct().getPrice();
		double price = 0;
		switch(category) {
		case A:
			price = quantity * rate * 1.1;
			break;
		case B:
			price = quantity * rate * 1.2; 
			break;
		case C:
			price = quantity * rate;
			break;
		default:
			break;
		}
		return price;
	}

	private Product isProductExists(String barCode) {
		List<Product> products = productRepository.findByBarCode(barCode);
		if (CollectionUtils.isEmpty(products)) {
			logger.info("BarCode does not exist!");
			throw new CustomNotFoundException("BarCode does not exist!");
		}
		return products.get(0);
	}
	
	@Override
	public void deleteInvoice(Long id) {
		logger.info("Deleing an invoice");
		Invoice invoice = invoiceRepository.findById(id)
				.orElseThrow(() -> new CustomNotFoundException("Invoice not found!"));
		if(null != invoice) 
			invoiceRepository.deleteById(id);
		logger.info("Invoice deleted");
	}
	

}
