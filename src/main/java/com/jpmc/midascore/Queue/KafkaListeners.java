package com.jpmc.midascore.Queue;

import com.jpmc.midascore.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import com.jpmc.midascore.foundation.Transaction;

@Configuration
public class KafkaListeners {
    ApplicationContext context;

    @Autowired
    KafkaListeners(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @KafkaListener(id = "${general.kafka-topic}", topics = "${general.kafka-topic}") // Listener for listener container
    public void listener(Transaction transaction) {
        TransactionService transactionService = context.getBean(TransactionService.class);
        System.out.println("-------------Task 2 Print out------------------------------ ");
        System.out.println(transaction);
        if(transactionService.processNewTransaction(transaction)) {
            System.out.println("-----Transaction Successful------");

            // send transaction to incentive api
        } else {
            System.out.println("-----Transaction Failed-------");
        }
    }
}
