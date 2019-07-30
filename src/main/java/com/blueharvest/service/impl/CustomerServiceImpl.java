package com.blueharvest.service.impl;

import com.blueharvest.model.Customer;
import com.blueharvest.model.ErrorModle;
import com.blueharvest.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Value("${system.api.baserUrl}")
  private String apiBaseUrl;


  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public Optional<Customer> findById(Long id) {

    Map<String, String> vars = new HashMap<String, String>();
    vars.put("id", id.toString());
    Customer result = restTemplate
        .getForObject(apiBaseUrl + "/customers/" + id, Customer.class, vars);
    return Optional.of(result);
  }

  @Override
  public List<Customer> findAllCustomers() {
    ResponseEntity<List<Customer>> rateResponse =
        restTemplate.exchange(apiBaseUrl + "/customers",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {
            });
    List<Customer> accounts = rateResponse.getBody();
    return accounts;
  }

  @Override
  public Customer createNewCustomer(Customer customer) {
    try {
      ResponseEntity<Customer> savedCustomer = restTemplate
          .postForEntity(apiBaseUrl + "/customers",
              customer, Customer.class);

      return savedCustomer.getBody();
    } catch (
        HttpClientErrorException ex) {
      Customer transaction1 = new Customer();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        ErrorModle errorModle = objectMapper
            .readValue(ex.getResponseBodyAsString(), ErrorModle.class);
        transaction1.setErrors(errorModle.getErrors().get(0));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return transaction1;
    }
  }

  @Override
  public Customer updateCustomer(Customer customer, Long id) {
    try {
      restTemplate.put(apiBaseUrl + "/customers/" + id, customer);
      return customer;
    } catch (
        HttpClientErrorException ex) {
      Customer transaction1 = new Customer();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        ErrorModle errorModle = objectMapper
            .readValue(ex.getResponseBodyAsString(), ErrorModle.class);
        transaction1.setErrors(errorModle.getErrors().get(0));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return transaction1;
    }
  }
}
