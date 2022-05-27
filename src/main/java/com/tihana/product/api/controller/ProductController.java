package com.tihana.product.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tihana.product.api.model.Product;
import com.tihana.product.api.service.ProductService;

@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping()
	public ResponseEntity<List<Product>> fetchAll() {
		List<Product> products = productService.fetchAll();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> fetchById(@PathVariable Integer id) {
		return new ResponseEntity<Product>(productService.fetchById(id), HttpStatus.OK);
	}

	@GetMapping(params = "name")
	public ResponseEntity<List<Product>> fetchByName(@RequestParam(value = "name") String name) {
		List<Product> products = productService.fetchByName(name);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@PostMapping(value = "/save", produces = { "application/json" })
	public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
		Product newProduct = productService.saveProduct(product);
		return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
	}

	@PutMapping(value = "/update/{id}", produces = { "application/json" })
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id,
			@Valid @RequestBody Product newProductData) {
		Product product = productService.fetchById(id);
		if (product != null) {
			product.setName(newProductData.getName());
			product.setDescription(newProductData.getDescription());
			product.setPriceHrk(newProductData.getPriceHrk());
			product.setIsAvailable(newProductData.getIsAvailable());
			Product updatedProduct = productService.updateProduct(product);
			return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<HttpStatus> deleteProductl(@PathVariable("id") Integer id) {
		Product product = productService.fetchById(id);
		if (product != null) {
			productService.deleteProductById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
