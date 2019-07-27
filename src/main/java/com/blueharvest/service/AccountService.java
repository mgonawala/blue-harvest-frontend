package com.reljicd.service;

import com.reljicd.model.Account;
import com.reljicd.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

  Optional<Account> findById(Long id);

  List<Account> findAllProductsPageable();

  Account createAccount(Account account, Long customerId);
}
