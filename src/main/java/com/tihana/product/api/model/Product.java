package com.tihana.product.api.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "code", unique = true, length = 10)
	@Size(min = 10, max = 10)
	@NotNull
	private String code;

	@Column(name = "name")
	@NotBlank
	private String name;

	@Column(name = "price_hrk")
	@Min(value = 0L, message = "The price must be positive")
	private BigDecimal priceHrk;

	@Column(name = "description")
	@NotBlank
	private String description;

	@Column(name = "is_available")
	@NotNull
	private Boolean isAvailable;

	@Transient
	private BigDecimal priceEur;

}
