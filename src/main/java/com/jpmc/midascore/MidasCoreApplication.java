package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class MidasCoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MidasCoreApplication.class, args);

//        KafkaTemplate<String, Transaction> kafkaTemplate = context.getBean(KafkaTemplate.class);
//        kafkaTemplate.send("TaskOne",new Transaction(123,323,234)); // should fail
//
//        UserRepository userRepository = context.getBean(UserRepository.class);
//
//        UserRecord chey = new UserRecord("chey", 1);
//        UserRecord tyler = new UserRecord("tyler", 15000);
//
//        System.out.println(chey);
//        System.out.println(tyler);
//
//        userRepository.save(chey);
//        userRepository.save(tyler);
//
//        Transaction t = new Transaction(tyler.getId(), chey.getId(), 0);
//
//        for(float f: Arrays.asList(123,43,1246,53,467,24356)){
//            t.setAmount(f);
//            MidasCoreApplication.sendTransaction(t, kafkaTemplate);
//        }
    }

    public static void sendTransaction(Transaction t, KafkaTemplate kf) {
        kf.send("TaskOne", t);
    }
}
