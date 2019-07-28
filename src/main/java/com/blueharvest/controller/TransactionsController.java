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

  @PostMapping("/transactions/{id}")
  public ModelAndView transactions(@ModelAttribute Transaction transaction, @PathVariable Long
      id) {

    ModelAndView modelAndView = new ModelAndView();
    //modelAndView.addObject("products", products);
    transaction = transactionsService.newTransaction(transaction, id);
    if (transaction.getErrors() != null && !transaction.getErrors().isEmpty()) {
      modelAndView.addObject("error_msg", transaction.getErrors());
    } else {
      modelAndView.addObject("success_msg", "Transaction was successful.");
    }
    modelAndView.setViewName("redirect:/transactions/" + id);
    return modelAndView;
  }

  @PostMapping("/accounts/{accountId}/transactions/{transactionId}")
  public ModelAndView delete(@PathVariable Long accountId, @PathVariable Long
      transactionId) {

    ModelAndView modelAndView = new ModelAndView();
    //modelAndView.addObject("products", products);
    Transaction transaction = transactionsService.deleteTransaction(accountId, transactionId);
    if (transaction.getErrors() != null && !transaction.getErrors().isEmpty()) {
      modelAndView.addObject("error_msg", transaction.getErrors());
    } else {
      modelAndView.addObject("success_msg", "Transaction revert was successful.");
    }
    modelAndView.setViewName("redirect:/transactions/"+accountId);
    return modelAndView;
  }
}
