package com.one02nations.template.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ac-address")
class Address {
	@Field("addressLine1")
	private String addressLine1;
	@Field("city")
	private String city;
	@Field("zipCode")
	private String zipCode;
	@Field("state")
	private String state;

}
