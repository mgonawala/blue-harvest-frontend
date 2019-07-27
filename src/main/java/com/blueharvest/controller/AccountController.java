package com.reljicd.controller;

import com.reljicd.model.Account;
import com.reljicd.model.Customer;
import com.reljicd.model.Product;
import com.reljicd.model.User;
import com.reljicd.service.AccountService;
import com.reljicd.service.ProductService;
import com.reljicd.util.Pager;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    //modelAndView.addObject("products", products);
    modelAndView.addObject("accounts", accounts);
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
    modelAndView.setViewName("redirect:/accounts");
    return modelAndView;
  }
}
