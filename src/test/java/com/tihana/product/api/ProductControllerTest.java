package com.tihana.product.api;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tihana.product.api.model.Product;
import com.tihana.product.api.service.ProductService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ProductControllerTest {

	public static final String FETCH = "/api/products";
	public static final String SAVE = "/api/products/save";
	public static final String UPDATE = "/api/products/update/";
	public static final String DELETE = "/api/products/delete/";

	@MockBean
	private ProductService productService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Test
	public void fetchAllProducts() throws Exception {
		List<Product> mockedProducts = new ArrayList<Product>();
		mockedProducts.add(new Product());
		when(productService.fetchAll()).thenReturn(mockedProducts);

		mockMvc.perform(get(FETCH).contentType("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", isA(List.class)));
	}

	@Test
	public void fetchProductById() throws Exception {
		Product mockedProduct = new Product();
		when(productService.fetchById(1)).thenReturn(mockedProduct);

		mockMvc.perform(get(FETCH).param("id", "1").contentType("application/json")).andExpect(status().isOk());
	}

	@Test
	public void fetchProductsByNamePart() throws Exception {
		List<Product> mockedProducts = new ArrayList<Product>();
		mockedProducts.add(new Product());
		when(productService.fetchByName("ana")).thenReturn(mockedProducts);

		mockMvc.perform(get(FETCH).queryParam("name", "ana").contentType("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", isA(List.class)));
	}

	@Test
	public void saveProduct() throws Exception {
		Product mockedProduct = new Product();
		when(productService.saveProduct(mockedProduct)).thenReturn(mockedProduct);

		mockMvc.perform(post(SAVE).contentType("application/json")).andExpect(status().is4xxClientError());

		Product product = new Product();
		product.setCode("1000000000");
		product.setName("Test");
		product.setDescription("Opis");
		product.setIsAvailable(false);
		product.setPriceHrk(new BigDecimal(1200));

		mockMvc.perform(post(SAVE).contentType("application/json").content(objectMapper.writeValueAsString(product)))
				.andExpect(status().isOk());
	}

}
