package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final UserService userService;

    public AccountService(AccountRepository accountRepo, @Lazy UserService userService) {
        this.accountRepo = accountRepo;
        this.userService = userService;
    }

    public Account getAccount(Long accountId) {
        return accountRepo.findById(accountId).orElse(null);
    }

    public void saveAccount(Account account) {
        accountRepo.save(account);
    }

    public long createAccount(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        int numberOfAccounts = user.getAccounts().size() + 1;
        Account newAccount = new Account();
        newAccount.setAccountName("Account #" + numberOfAccounts);
        newAccount.getUsers().add(user);

        accountRepo.save(newAccount);

        user.getAccounts().add(newAccount);
        userService.saveUser(user);

        return newAccount.getAccountId();
    }
}