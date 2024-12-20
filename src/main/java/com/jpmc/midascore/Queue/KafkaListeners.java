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
        var result = transactionService.processNewTransaction(transaction);

        System.out.println(transaction);
        System.out.println("New Transaction added: " + result);
    }
}
