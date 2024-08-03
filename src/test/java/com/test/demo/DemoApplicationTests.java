package com.test.demo;

import com.jayway.jsonpath.JsonPath;
import com.test.demo.models.Customer;
import com.test.demo.repository.CustomerRepository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepository customerRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private static String userIdToDelete;

	private String obtainAccessToken(String username, String password) throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"" + username + "\", \"password\":\"" + 
				password + "\"}"))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		return JsonPath.read(response, "$.accessToken");
	}

	@Test
	@Order(1)
	public void getCustomerPositive() throws Exception {
		// Create a sample customer
		Customer customer = new Customer();
		customer.setId(UUID.randomUUID().toString());
		customer.setName("John Doe 1");
		customer.setGender("Male");
		customer.setAge(30);

		// Create a Page object containing the sample customer
		Page<Customer> customerPage = new PageImpl<>(List.of(customer),
		PageRequest.of(0, 25), 1);

		// Mock the repository to return the Page object
		when(customerRepository.findAll(PageRequest.of(0, 25))).thenReturn(customerPage);

		// Perform a GET request to the endpoint that retrieves customers
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/api/v1/customers/get?page=0&size=25")
				.header("Authorization", "Bearer " + 
				obtainAccessToken("sokchan", "123456"))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println("Response Body: " + responseBody);

		// Check the status and print the response content if it's not 200 OK
		if (result.getResponse().getStatus() != 200) {
			System.out.println("Response Status: " + result.getResponse().getStatus());
			System.out.println("Response Content: " + result.getResponse().getContentAsString());
		}
	}

	@Test
	@Order(2)
	public void testCreateCustomer_Positive() throws Exception {
		Faker faker = new Faker();
		String token = obtainAccessToken("sokchan", "123456");
		String randomName = faker.name().fullName();
		String randomGender = faker.options().option("Male", "Female");
		int randomAge = faker.number().numberBetween(18, 100);

		Customer customer = new Customer(UUID.randomUUID().toString(),
		 randomName, randomGender, randomAge);
		String customerJson = objectMapper.writeValueAsString(customer);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/customers/create")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(customerJson))
				.andDo(print())
				.andExpect(status().isCreated())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println("Response Body: " + responseBody);

		// Check the status and print the response content if it's not 201 Created
		if (result.getResponse().getStatus() != 201) {
			System.out.println("Response Status: " + result.getResponse().getStatus());
			System.out.println("Response Content: " + result.getResponse().getContentAsString());
		} else {
			Customer cusResult = objectMapper.readValue(responseBody, Customer.class);
			userIdToDelete = cusResult.getId();
		}

	}

	@Test
	@Order(3)
	public void testUpdateCustomer_Positive() throws Exception {
		Faker faker = new Faker();
		String token = obtainAccessToken("sokchan", "123456");

		String randomName = faker.name().fullName();
		String randomGender = faker.options().option("Male", "Female");
		int randomAge = faker.number().numberBetween(18, 100);

		Customer customer = new Customer();
		customer.setId("f69d22fc-456c-4033-8506-8d4bc73a9937");
		customer.setName(randomName);
		customer.setGender(randomGender);
		customer.setAge(randomAge);
		String customerJson = objectMapper.writeValueAsString(customer);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/update")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(customerJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println("Response Body: " + responseBody);

		// Check the status and print the response content if it's not 201 Created
		if (result.getResponse().getStatus() != 201) {
			System.out.println("Response Status: " + result.getResponse().getStatus());
			System.out.println("Response Content: " + result.getResponse().getContentAsString());
		}
	}

	@Test
	@Order(4)
	public void testDeleteCustomer_Positive() throws Exception {
		String token = obtainAccessToken("sokchan", "123456");

		// Mock the repository to return the customer when deleteById is called
		doNothing().when(customerRepository).deleteById(anyString());

		// Perform a DELETE request to the endpoint that deletes a customer
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/customers/delete?id=" + userIdToDelete)
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println("Response Body: " + responseBody);

		System.out.println("Response Status: " + result.getResponse().getStatus());
		System.out.println("Response Content: " + result.getResponse().getContentAsString());
	}

	@Test
	@Order(5)
	public void testCreateCustomer_Negative_Name_Empty() throws Exception {
		Faker faker = new Faker();
		String token = obtainAccessToken("sokchan", "123456");

		String randomGender = faker.options().option("Male", "Female");
		int randomAge = faker.number().numberBetween(18, 100);

		Customer customer = new Customer();
		customer.setId(UUID.randomUUID().toString());
		customer.setName("");
		customer.setGender(randomGender);
		customer.setAge(randomAge);
		String customerJson = objectMapper.writeValueAsString(customer);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/create")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(customerJson))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("Name is required!"));
	}

	@Test
	@Order(6)
	public void testCreateCustomer_Negative_Gender_Empty() throws Exception {
		Faker faker = new Faker();
		String token = obtainAccessToken("sokchan", "123456");

		String randomGender = faker.options().option("Male", "Female");
		int randomAge = faker.number().numberBetween(18, 100);
		String randomName = faker.name().fullName();

		Customer customer = new Customer();
		customer.setId(UUID.randomUUID().toString());
		customer.setName(randomName);
		customer.setGender("");
		customer.setAge(randomAge);
		String customerJson = objectMapper.writeValueAsString(customer);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/create")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(customerJson))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.gender").value("Gender is required!"));
	}

	@Test
	@Order(7)
	public void testCreateCustomer_Negative_Age_Grader_Than_One() throws Exception {
		Faker faker = new Faker();
		String token = obtainAccessToken("sokchan", "123456");

		String randomGender = faker.options().option("Male", "Female");
		int randomAge = faker.number().numberBetween(18, 100);
		String randomName = faker.name().fullName();

		Customer customer = new Customer();
		customer.setId(UUID.randomUUID().toString());
		customer.setName(randomName);
		customer.setGender(randomGender);
		customer.setAge(-1);
		String customerJson = objectMapper.writeValueAsString(customer);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/create")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(customerJson))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.age").value("Age must be greater than 1!"));
	}


}
