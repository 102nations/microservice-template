package com.one02nations.template.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.one02nations.template.model.User;
import com.one02nations.template.repository.UserRepository;

import jakarta.annotation.PostConstruct;

/**
 * Integration tests to verify that our endpoints are properly secured.
 */

@SpringBootTest()
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles("test")
class TemplateControllerTest {

	static MockMvc mockMvc;

	@Autowired
	private WebApplicationContext controller;

	@PostConstruct
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(controller).apply(springSecurity()).build();
	}

	@MockBean
	@SuppressWarnings("unused")
	private JwtDecoder jwtDecoder;

	@Test
	public void testPrivateEndpointReturnsUnauthorizedWhenCalled() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/me")).andExpect(status().isUnauthorized()).andReturn();
	}

	@Test
	@WithMockUser(username = "testUser")
	public void testPrivateEndpointReturnsOkWhenAuthorized() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/api/v1/me")).andExpect(status().isOk()).andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void testPublicEndpointReturnsOkWhenCalled() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/api/v1/test")).andExpect(status().isOk()).andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());

	}

	@Test
	@WithMockUser(username = "testUser")
	public void testPrivateEndpointReturnsOkWhenCalled() throws Exception {

		User usr = com.one02nations.template.model.User.builder().email("test").userId("testUser").build();
		when(repository.findByUserId("testUser")).thenReturn(Optional.of(usr));
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/testUser")).andExpect(status().isOk()).andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());

	}
	@Test
	@WithMockUser(username = "testUser")
	public void testPrivateEndpointReturnsOkWhenCalledThrowsException() throws Exception {

		User usr = com.one02nations.template.model.User.builder().email("test").userId("testUser").build();
		when(repository.findByUserId("testUser")).thenReturn(Optional.empty());
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/testUser")).andExpect(status().isNotFound()).andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());

	}


	@Test
	@WithMockUser(username = "testUser2")
	public void testPrivateCreateUserWhenCalledThrowsNotFound() throws Exception {
		User usr = com.one02nations.template.model.User.builder().email("test").userId("testUser2").build();
		when(repository.findByUserId("testUser2")).thenReturn(Optional.of(usr));
		MvcResult mvcResult = mockMvc.perform(post("/api/v1/users/testUser2")).andExpect(status().isBadRequest())
				.andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());

	}

	@Test
	@WithMockUser(username = "testUser2")
	public void testPrivateCreateUserWhenCalledCreatesUser() throws Exception {
		User usr = com.one02nations.template.model.User.builder().email("test").userId("testUser2").build();
		when(repository.findByUserId("testUser2")).thenReturn(Optional.empty());
		MvcResult mvcResult = mockMvc.perform(post("/api/v1/users/testUser2")).andExpect(status().isOk()).andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());

	}

	@MockBean
	private UserRepository repository;
}
