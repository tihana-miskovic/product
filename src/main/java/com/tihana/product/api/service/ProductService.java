package com.tihana.product.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tihana.product.api.dao.ProductRepository;
import com.tihana.product.api.model.ExchangeRate;
import com.tihana.product.api.model.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Value("${hnb.api.url}")
	private String hnbApiUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ProductRepository productRepository;

	private ExchangeRate exchangeRate = new ExchangeRate();

	private BigDecimal fetchHrkEurExchangeRate() {
		if (exchangeRate.getDate() != null && exchangeRate.getDate().isEqual(LocalDate.now())) {
			return exchangeRate.getMiddleRate();
		} else {
			log.info("Calling HNP api, " + LocalDateTime.now());
			ResponseEntity<List<ExchangeRate>> response = restTemplate.exchange(hnbApiUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<ExchangeRate>>() {
					});
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				exchangeRate = response.getBody().get(0);
				return exchangeRate.getMiddleRate();
			} else {
				log.error("Error response received from HNB api: " + response.getBody());
			}

			return exchangeRate.getMiddleRate();
		}
	}

	private Product updateEurPrice(Product product) {
		if (fetchHrkEurExchangeRate() != null) {
			product.setPriceEur(product.getPriceHrk().divide(fetchHrkEurExchangeRate(), 2, RoundingMode.HALF_UP));
		}
		return product;
	}

	public List<Product> fetchAll() {
		List<Product> products = productRepository.findAll();
		for (Product product : products) {
			updateEurPrice(product);
		}
		return products;
	}

	public Product fetchById(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			return updateEurPrice(product.get());
		} else {
			return null;
		}
	}

	public List<Product> fetchByName(String name) {
		List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
		for (Product product : products) {
			updateEurPrice(product);
		}
		return products;
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	public void deleteProductById(int productId) {
		productRepository.deleteById(productId);
	}

}
