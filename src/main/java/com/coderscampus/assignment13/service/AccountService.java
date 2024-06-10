package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    UserRepository userRepo;

    public Account getAccount(Long accountId) {

        return accountRepo.getOne(accountId);
    }

    public void saveAccount(Account account, Long userId) {

        if (account.getAccountName() == null) {
            User user = new User();
            //user.
            Account newAccount = new Account();
            newAccount.setAccountName("Account #" + newAccount.getAccountId());
            newAccount.getUsers().add(userRepo.getOne(userId));
            System.out.println("NAUJAS ACCOUNTAS:" + newAccount.getAccountName());
        }

        accountRepo.save(account);
    }

}
