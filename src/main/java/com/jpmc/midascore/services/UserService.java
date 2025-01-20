package com.jpmc.midascore.services;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    public boolean userIsValid(Long id) {
        return userRepo.existsById(id);
    }

    public Optional<UserRecord> getUser(Long userId) {
        return this.userRepo.findById(userId);
    }

    public boolean isSufficientBalance(Long userId, float amount) {
        return !(this.getUser(userId).get().getBalance() - amount < 0);
    }

    public void updateUsersBalance(UserRecord sender, UserRecord recipient, Transaction transaction) {
        try {
            recipient.setBalance((float) (recipient.getBalance() + transaction.getAmount() + transaction.getIncentiveAmount()));
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            userRepo.save(sender);
            userRepo.save(recipient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void printUserBalance(Long userId) {
        UserRecord user = this.getUser(userId).get();
        System.out.println(user);
    }
}
