package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping
public class BalanceController {
    UserService userService;

    public BalanceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/balance")
    @ResponseBody
    public Balance getUserBalance(@RequestParam Long userId) {
        Optional<UserRecord> user = this.userService.getUser(userId);
//        System.out.println("getting user balance");
        return user.map(userRecord -> new Balance(userRecord.getBalance())).orElseGet(() -> new Balance(0));

    }
}
