package com.blueharvest.controller;

import com.blueharvest.model.Account;
import com.blueharvest.model.Customer;
import com.blueharvest.service.AccountService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

  private static final int INITIAL_PAGE = 0;


  private final AccountService accountService;

  @Autowired
  public AccountController( AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/accounts")
  public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

    // Evaluate page. If requested parameter is null or less than 0 (to
    // prevent exception), return initial size. Otherwise, return value of
    // param. decreased by 1.
    int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

    List<Account> accounts = accountService.findAllProductsPageable();
    ModelAndView modelAndView = new ModelAndView();
    Account account = new Account();
    Customer customer = new Customer();
    account.setCustomer(customer);
    //modelAndView.addObject("products", products);
    modelAndView.addObject("accounts", accounts);
    modelAndView.addObject("account", account);
    modelAndView.setViewName("/accounts");
    return modelAndView;
  }

  @GetMapping("/accounts/create")
  public ModelAndView createAccountPage() {
    ModelAndView modelAndView = new ModelAndView();
    Account account = new Account();
    Customer customer = new Customer();
    account.setCustomer(customer);
    modelAndView.addObject("account", account);
    modelAndView.setViewName("/registeraccount");
    return modelAndView;
  }

  @PostMapping("/accounts/create")
  public ModelAndView createAccount(@ModelAttribute Account account) {
    ModelAndView modelAndView = new ModelAndView();
    Account account1 = accountService.createAccount(account, account.getCustomer().getId());
    modelAndView.addObject("account", account1);
    if (account1.getErrors() != null && !account1.getErrors().isEmpty()) {
      modelAndView.addObject("error_msg", account1.getErrors());
      // redirectAttributes.addAttribute("error_msg", account1.getErrors());
    } else {
      modelAndView.addObject("success_msg", "Account created successfully.");
      //redirectAttributes.addAttribute("success_msg", "Account created successfully.");
    }
    modelAndView.setViewName("redirect:/accounts");
    return modelAndView;
  }

  @PostMapping("/accounts/{id}")
  public ModelAndView deleteAccount(@PathVariable Long id) {
    Account account1 = accountService.deleteAccount(id);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("account", account1);
    if (account1.getErrors() != null && !account1.getErrors().isEmpty()) {
      modelAndView.addObject("error_msg", account1.getErrors());
      // redirectAttributes.addAttribute("error_msg", account1.getErrors());
    } else {
      modelAndView.addObject("success_msg", "Account Deleted successfully.");
      //redirectAttributes.addAttribute("success_msg", "Account created successfully.");
    }
    modelAndView.setViewName("redirect:/accounts");
    return modelAndView;
  }

  @GetMapping("/accounts/{id}")
  public ModelAndView getAccount(@PathVariable Long id) {
    List<Account> accounts = accountService.findById(id);
    ModelAndView modelAndView = new ModelAndView();
    Account account = new Account();
    Customer customer = new Customer();
    account.setCustomer(customer);
    //modelAndView.addObject("products", products);
    modelAndView.addObject("accounts", accounts);
    modelAndView.addObject("account", account);
    modelAndView.setViewName("/accounts");
    return modelAndView;
  }
}
