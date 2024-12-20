package com.jpmc.midascore.services;

import com.jpmc.midascore.entity.UserRecord;
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

    //FIX Optional: Assuming user exists at this point
    public boolean isSufficientBalance(Long userId, float amount) {
        return !(this.getUser(userId).get().getBalance() - amount < 0);
    }

    public boolean updateUsersBalance(UserRecord sender, UserRecord receipient, float amount) {
        try {
            receipient.setBalance(receipient.getBalance() + amount);
            sender.setBalance(sender.getBalance() - amount);
            userRepo.save(sender);
            userRepo.save(receipient);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void printUserBalance(Long userId) {
        UserRecord user = this.getUser(userId).get();
        System.out.println(user);
    }
}
