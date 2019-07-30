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
    modelAndView.addObject("account_id",id);
    modelAndView.addObject("transaction",transaction);
    modelAndView.addObject("transactions", transactions);
    modelAndView.setViewName("/transactions");
    return modelAndView;
  }

  @PostMapping("/transactions/{id}")
  public ModelAndView transactions(@ModelAttribute Transaction transaction, @PathVariable Long
      id) {

    transaction = transactionsService.newTransaction(transaction, id);
    ModelAndView view = this.transactions(id);
    if (transaction.getErrors() != null && !transaction.getErrors().isEmpty()) {
      view.addObject("error_msg", transaction.getErrors());
    } else {
      view.addObject("success_msg", "Transaction was successful.");
    }
    return view;
  }

  @PostMapping("/accounts/{accountId}/transactions/{transactionId}")
  public ModelAndView delete(@PathVariable Long accountId, @PathVariable Long
      transactionId) {
    Transaction transaction = transactionsService.deleteTransaction(accountId, transactionId);
    ModelAndView view = this.transactions(accountId);
    if (transaction.getErrors() != null && !transaction.getErrors().isEmpty()) {
      view.addObject("error_msg", transaction.getErrors());
    } else {
      view.addObject("success_msg", "Transaction revert was successful.");
    }
    return view;
  }
}
