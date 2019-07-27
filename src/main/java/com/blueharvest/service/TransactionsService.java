package com.blueharvest.service;

import com.blueharvest.model.Transaction;
import java.util.List;

public interface TransactionsService {

  List<Transaction> getAllTransactionByAccount(Long id);

  Transaction newTransaction(Transaction id, Long accountId);

}
