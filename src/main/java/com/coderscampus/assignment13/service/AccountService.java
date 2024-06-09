package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepo;

    public Account getAccount(Long accountId) {
        if (accountId == 0) {
            Account newAccount = new Account();
            System.out.println(newAccount.getAccountId());
        }
        return accountRepo.getOne(accountId);
    }

    public void saveAccount(Account account) {
        accountRepo.save(account);
    }

}
