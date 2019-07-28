package com.blueharvest.service;

import com.blueharvest.model.Account;
import java.util.List;

public interface AccountService {

  List<Account> findById(Long id);

  List<Account> findAllProductsPageable();

  Account createAccount(Account account, Long customerId);

  Account deleteAccount(Long accountId);
}
