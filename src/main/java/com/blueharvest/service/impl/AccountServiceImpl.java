package com.blueharvest.service.impl;

import com.blueharvest.model.Account;
import com.blueharvest.service.AccountService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImpl implements AccountService {

  private String apiBaseUrl = "http://localhost:8585/";

  private String apiPath = "accounts";

  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public Optional<Account> findById(Long id) {

    Map<String, String> vars = new HashMap<String, String>();
    vars.put("id", id.toString());
    Account result = restTemplate.getForObject("http://localhost:8585/accounts/{id}", Account.class, vars);
    return Optional.of(result);
  }

  @Override
  public List<Account> findAllProductsPageable() {
    ResponseEntity<List<Account>> rateResponse =
        restTemplate.exchange("http://localhost:8585/api/v1/accounts",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {
            });
    List<Account> accounts = rateResponse.getBody();
    return accounts;
  }

  @Override
  public Account createAccount(Account account, Long customerId) {
    Map<String, String> vars = new HashMap<String, String>();
    vars.put("id", customerId.toString());

    ResponseEntity<Account> result = restTemplate.postForEntity("http://localhost:8585/api/v1/customers/"+customerId+"/accounts",
        account,
        Account.class);
    return  result.getBody();
  }
}
