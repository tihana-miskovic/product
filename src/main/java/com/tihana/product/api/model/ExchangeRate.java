package com.tihana.product.api.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {
	
	@JsonProperty("Datum primjene")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private LocalDate date; 
	
	@JsonProperty("Šifra valute")
	private Integer currencyCode; 
	
	@JsonProperty("Valuta")
	private String currency;
	
	@JsonProperty("Kupovni za devize")
	private BigDecimal buyRate;
	
	@JsonProperty("Srednji za devize")
	private BigDecimal middleRate;

	@JsonProperty("Prodajni za devize")
	private BigDecimal sellRate;
	
	public void setBuyRate(String rateStr) throws ParseException {
		buyRate = new BigDecimal(rateStr.replace(",", "."));
	}
	
	public void setMiddleRate(String rateStr) throws ParseException {
		middleRate = new BigDecimal(rateStr.replace(",", "."));
	}
	
	public void setSellRate(String rateStr) throws ParseException {
		sellRate = new BigDecimal(rateStr.replace(",", "."));
	}

}
