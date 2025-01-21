package com.jpmc.midascore.services;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TransactionService {
    TransactionRecordRepository transactionRecordRepository;
    UserService userService;

    @Autowired
    public TransactionService(UserService userService, TransactionRecordRepository transactionRecordRepository) {
        this.userService = userService;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    public boolean processNewTransaction(Transaction transaction) {
        if (!this.validateTransactionUsers(transaction.getSenderId(), transaction.getRecipientId())) return false;
        if (!this.validateTransactionAmount(transaction.getSenderId(), transaction.getAmount())) return false;
        return this.completeTransaction(transaction);
    }

    // validate users exists
    public boolean validateTransactionUsers(Long senderId, Long recepientId) {
        return this.userService.userIsValid(senderId) && this.userService.userIsValid(recepientId);
    }

    //validate sufficient balance
    public boolean validateTransactionAmount(Long senderId, float transactionAmount) {
        return this.userService.isSufficientBalance(senderId, transactionAmount);
    }

    public boolean completeTransaction(Transaction transaction) {
        Incentive incentive = getIncentiveAmount(transaction);
        transaction.setIncentiveAmount(incentive.getAmount());

        UserRecord sender = this.userService.getUser(transaction.getSenderId()).get();
        UserRecord recipient = this.userService.getUser(transaction.getRecipientId()).get();

        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction);

        try {
            transactionRecordRepository.save(transactionRecord);
            this.userService.updateUsersBalance(sender, recipient, transaction);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Incentive getIncentiveAmount(Transaction transaction) {
        RestClient restClient = RestClient.create();
        return restClient.post()
                .uri("http://localhost:8080/incentive")
                .body(transaction)
                .retrieve()
                .body(Incentive.class);
    }
}


