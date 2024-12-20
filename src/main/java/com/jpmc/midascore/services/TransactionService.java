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
        // Validate recepient and sender exist
        if (this.validateTransactionUsers(transaction.getSenderId(), transaction.getRecipientId())) {

//             validate sufficient funds
            if (this.validateTransactionAmount(transaction.getSenderId(), transaction.getAmount())) {

                //Comlpete Transaction
                return this.completeTransaction(transaction);
            }else System.out.println("Insufficient balance: " + transaction.getAmount());
        }
        else System.out.println("Invalid sender or recipient");
        return false;

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
//        this.userService.printUserBalance(sender.getId());
//        this.userService.printUserBalance(recipient.getId());

        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount());
        try {
            transactionRecordRepository.save(transactionRecord);
            this.userService.updateUsersBalance(sender, recipient, transaction.getAmount());
            this.userService.printUserBalance(sender.getId());
            this.userService.printUserBalance(recipient.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
