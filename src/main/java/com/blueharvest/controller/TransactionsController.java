package com.blueharvest.controller;

import com.blueharvest.model.Transaction;
import com.blueharvest.service.TransactionsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TransactionsController {

  @Autowired
  TransactionsService transactionsService;

  @GetMapping("/transactions/{id}")
  public ModelAndView transactions(@PathVariable Long id) {

    List<Transaction> transactions = transactionsService.getAllTransactionByAccount(id
    );
    ModelAndView modelAndView = new ModelAndView();
    Transaction transaction = new Transaction();
    //modelAndView.addObject("products", products);
    modelAndView.addObject("account_id",id);
    modelAndView.addObject("transaction",transaction);
    modelAndView.addObject("transactions", transactions);
    modelAndView.setViewName("/transactions");
    return modelAndView;
  }

  @PostMapping("/transactions")
  public ModelAndView transactions(@ModelAttribute Transaction transaction) {

    ModelAndView modelAndView = new ModelAndView();
    //modelAndView.addObject("products", products);
    Long accountId = transaction.getId();
    transaction = transactionsService.newTransaction(transaction, transaction.getId());
    modelAndView.addObject("success_msg","Transaction was successful.");
    modelAndView.setViewName("redirect:/transactions/"+accountId);
    return modelAndView;
  }
}
