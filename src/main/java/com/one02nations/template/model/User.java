package com.one02nations.template.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ac-users")
public class User {
	@Id
	private String Id;
	@Field("userId")
	private String userId;
	@Field("firstName")
	private String firstName;
	@Field("lastName")
	private String lastName;
	@Field("phone")
	private String phone;
	@Field("image")
	private String image;
	@Field("gender")
	private String gender;
	@Email
	@Field("email")
	private String email;
	@Field("officeAddress")
	private String officeAddress;
	@Field("address")
	private Address address;
	@Field("role")
	private List<String> role;
	@Field("notification")
	private boolean notification;
	@Field("miscellaneous")
	private Object miscellaneous;
	@Field("isActive")
	private boolean isActive;
	@Field("isDeleted")
	private boolean isDeleted;
	@Field("addedOn")
	private Date addedOn;
	@Field("modifiedOn")
	private Date modifiedOn;
	@DBRef
	private Set<Role> roles = new HashSet<>();

}
