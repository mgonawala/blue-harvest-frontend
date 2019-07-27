package com.blueharvest.service;

import com.blueharvest.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountService {

  Optional<Account> findById(Long id);

  List<Account> findAllProductsPageable();

  Account createAccount(Account account, Long customerId);
}
