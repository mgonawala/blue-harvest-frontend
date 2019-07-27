package com.reljicd.service.impl;

import com.reljicd.model.Account;
import com.reljicd.service.AccountService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
