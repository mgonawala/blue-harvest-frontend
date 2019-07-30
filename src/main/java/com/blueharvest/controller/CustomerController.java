package com.blueharvest.controller;

import com.blueharvest.model.Customer;
import com.blueharvest.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {


  private static final int INITIAL_PAGE = 0;


  private final CustomerService customerService;

  @Autowired
  public CustomerController( CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/customers")
  public ModelAndView home() {

    List<Customer> accounts = customerService.findAllCustomers();
    ModelAndView modelAndView = new ModelAndView();
    Customer customer = new Customer();
    modelAndView.addObject("customers", accounts);
    modelAndView.addObject("customer", customer);
    modelAndView.setViewName("/customers");
    return modelAndView;
  }

  @GetMapping("/customers/create")
  public ModelAndView createAccountPage() {
    ModelAndView modelAndView = new ModelAndView();
    Customer customer = new Customer();
    modelAndView.addObject("customer", customer);
    modelAndView.setViewName("/registercustomer");
    return modelAndView;
  }

  @PostMapping("/customers/create")
  public ModelAndView createAccount(@ModelAttribute Customer input) {
    ModelAndView modelAndView = this.home();
    Customer account1 = customerService.createNewCustomer(input);
    if (account1.getErrors() != null && !account1.getErrors().isEmpty()) {
      modelAndView.addObject("error_msg", account1.getErrors());
    } else {
      modelAndView.addObject("success_msg", "Customer created successfully.");
    }
    return modelAndView;
  }

  @PostMapping("/customers/{id}")
  public ModelAndView updateCustomer(@ModelAttribute Customer input, @PathVariable Long id) {
    ModelAndView modelAndView = this.home();
    Customer account1 = customerService.updateCustomer(input, id);
    if (account1.getErrors() != null && !account1.getErrors().isEmpty()) {
      modelAndView.addObject("error_msg", account1.getErrors());
    } else {
      modelAndView.addObject("success_msg", "Customer details updated successfully.");
    }
    return modelAndView;
  }

}
