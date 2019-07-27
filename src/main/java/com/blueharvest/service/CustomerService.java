package com.reljicd.service;

import com.reljicd.model.Account;
import com.reljicd.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

  Optional<Customer> findById(Long id);

  List<Customer> findAllProductsPageable();

  Customer createNewCustomer(Customer customer);
}
