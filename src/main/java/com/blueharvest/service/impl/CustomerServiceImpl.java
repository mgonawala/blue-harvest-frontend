package com.reljicd.service.impl;

import com.reljicd.model.Account;
import com.reljicd.model.Customer;
import com.reljicd.service.CustomerService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    ResponseEntity<Customer> savedCustomer = restTemplate
        .postForEntity(apiBaseUrl + apiPath,
            customer, Customer.class);

    return savedCustomer.getBody();
  }
}
