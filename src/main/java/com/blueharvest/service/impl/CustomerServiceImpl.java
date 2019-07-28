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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Value("${system.api.baserUrl}")
  private String apiBaseUrl;

  @Value(("${customers.contextPath}"))
  private String apiPath ;

  @Value("${get.customers.byId.uri}")
  private String getCustomers;

  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public Optional<Customer> findById(Long id) {

    Map<String, String> vars = new HashMap<String, String>();
    vars.put("id", id.toString());
    Customer result = restTemplate.getForObject(apiBaseUrl+apiPath+getCustomers, Customer.class, vars);
    return Optional.of(result);
  }

  @Override
  public List<Customer> findAllProductsPageable() {
    ResponseEntity<List<Customer>> rateResponse =
        restTemplate.exchange(apiBaseUrl + apiPath,
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {
            });
    List<Customer> accounts = rateResponse.getBody();
    return accounts;
  }

  @Override
  public Customer createNewCustomer(Customer customer) {
    try {
      ResponseEntity<Customer> savedCustomer = restTemplate
          .postForEntity(apiBaseUrl + apiPath,
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
      HttpEntity<Customer> customerHttpEntity = new HttpEntity<>(customer);
   /* ResponseEntity<Customer> rateResponse =
        restTemplate.exchange(apiBaseUrl + apiPath +"/"+id,
            HttpMethod.PUT, customerHttpEntity, new ParameterizedTypeReference<Customer>() {
            });*/
      restTemplate.put(apiBaseUrl + apiPath + "/" + id, customer);

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
