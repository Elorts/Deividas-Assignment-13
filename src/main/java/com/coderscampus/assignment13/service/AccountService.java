package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    public Account getAccount(Long accountId) {
        return accountRepo.findById(accountId).orElse(null);
    }

    public void saveAccount(Account account) {
        accountRepo.save(account);
    }

    public long createAccount(Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        int numberOfAccounts = user.getAccounts().size() + 1;
        Account newAccount = new Account();
        newAccount.setAccountName("Account #" + numberOfAccounts);
        newAccount.getUsers().add(user);

        accountRepo.save(newAccount);

        user.getAccounts().add(newAccount);
        userRepo.save(user);

        return newAccount.getAccountId();
    }
}