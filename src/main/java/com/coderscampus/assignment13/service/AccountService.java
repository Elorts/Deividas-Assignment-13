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
    AccountRepository accountRepo;

    @Autowired
    UserRepository userRepo;

    public Account getAccount(Long accountId) {

        return accountRepo.getOne(accountId);
    }

    public void saveAccount(Account account) {
        accountRepo.save(account);
    }

    public long createAccount(Long userId) {
        User user = new User();
        user = userRepo.getOne(userId);

        Integer numberOfAccounts = user.getAccounts().size() + 1;
        Account newAccount = new Account();

        newAccount.setAccountName("Account #" + numberOfAccounts);
        newAccount.getUsers().add(userRepo.getOne(userId));

        accountRepo.save(newAccount);

        user.getAccounts().add(newAccount);

        userRepo.save(user);

        return newAccount.getAccountId();
    }

}
