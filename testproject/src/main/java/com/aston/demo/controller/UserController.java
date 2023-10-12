package com.aston.demo.controller;

import com.aston.demo.model.Transaction;
import com.aston.demo.model.Users;
import com.aston.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
@Tag(
        name = "User",
        description = "Methods fot getting user info"
)
@RestController("/user")
public class UserController {
    private UserService userService;

    //todo make pageable
    @Operation(summary = "Get transactions history for user or for account")
    @GetMapping(value = { "/history/{accountId}"})
    public List<Transaction> getTransHistory (@PathVariable Long accountId){
        return userService.getTransHistory(accountId);
    }


    @Operation(summary = "Get user info")
    @GetMapping("/user/{id}")
    public Users getUserInfo (@PathVariable Long id){
        return userService.getUserInfo(id);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
