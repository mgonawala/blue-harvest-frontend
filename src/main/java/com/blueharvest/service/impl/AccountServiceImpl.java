package com.blueharvest.service.impl;

import com.blueharvest.model.Account;
import com.blueharvest.model.ErrorModle;
import com.blueharvest.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImpl implements AccountService {

  @Value("${system.api.baserUrl}")
  private String apiBaseUrl;


  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public List<Account> findById(Long id) {
    ResponseEntity<List<Account>> accounts =
        restTemplate.exchange(apiBaseUrl + "/customers/" + id + "/accounts",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {
            });
    return accounts.getBody();
  }


  @Override
  public List<Account> findAllProductsPageable() {
    ResponseEntity<List<Account>> rateResponse =
        restTemplate.exchange(apiBaseUrl + "/accounts",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {
            });
    List<Account> accounts = rateResponse.getBody();
    return accounts;
  }

  @Override
  public Account createAccount(Account account, Long customerId) {
    Map<String, String> vars = new HashMap<String, String>();
    vars.put("id", customerId.toString());
    try {
      ResponseEntity<Account> result = restTemplate
          .postForEntity(apiBaseUrl + "/customers/" + customerId + "/accounts",
              account,
              Account.class);
      return result.getBody();
    } catch (HttpClientErrorException ex) {
      Account transaction1 = new Account();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        ErrorModle errorModle = objectMapper
            .readValue(ex.getResponseBodyAsString(), ErrorModle.class);
        transaction1.setErrors(errorModle.getErrors().get(0));
      } catch (IOException e) {
      }
      return transaction1;
    }
  }

  @Override
  public Account deleteAccount(Long accountId) {
    Map<String, String> vars = new HashMap<String, String>();
    try {
      restTemplate.delete(apiBaseUrl + "/accounts/" + accountId);
      return new Account();
    } catch (HttpClientErrorException ex) {
      Account transaction1 = new Account();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        if (!ex.getResponseBodyAsString().isEmpty()) {
          ErrorModle errorModle = objectMapper
              .readValue(ex.getResponseBodyAsString(), ErrorModle.class);
          transaction1.setErrors(errorModle.getErrors().get(0));
        }

      } catch (IOException e) {
      }
      return transaction1;
    }
  }
}
