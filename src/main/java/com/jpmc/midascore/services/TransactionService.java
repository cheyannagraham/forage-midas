package com.jpmc.midascore.services;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean validateTransactionUsers(Long senderId, Long recepientId) {
        return this.userService.userIsValid(senderId) && this.userService.userIsValid(recepientId);
    }

    public boolean validateTransactionAmount(Long senderId, float transactionAmount) {
        return this.userService.isSufficientBalance(senderId, transactionAmount);
    }

    public boolean completeTransaction(Transaction transaction) {
        UserRecord sender = this.userService.getUser(transaction.getSenderId()).get();
        UserRecord recipient = this.userService.getUser(transaction.getRecipientId()).get();

        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount());

        try {
            transactionRecordRepository.save(transactionRecord);
            this.userService.updateUsersBalance(sender, recipient, transaction.getAmount());

            System.out.println("---------------------------TASK 3 PrintOut-------------------------");
            System.out.println("SENDER:");
            this.userService.printUserBalance(sender.getId());
            System.out.println("RECIPIENT:");
            this.userService.printUserBalance(recipient.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
