package com.blueharvest.service;

import com.blueharvest.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

  Optional<Customer> findById(Long id);

  List<Customer> findAllCustomers();

  Customer createNewCustomer(Customer customer);


  Customer updateCustomer(Customer customer, Long id);
}
