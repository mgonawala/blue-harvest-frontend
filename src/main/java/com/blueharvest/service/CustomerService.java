package com.blueharvest.service;

import com.blueharvest.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

  Optional<Customer> findById(Long id);

  List<Customer> findAllProductsPageable();

  Customer createNewCustomer(Customer customer);
}
