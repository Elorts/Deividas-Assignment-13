package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.UserAccount;
import com.coderscampus.assignment13.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    UserAccount



    public List<Account> getAccount (Long userId) {
        return accountRepo.findAll(acc);
    }

}
