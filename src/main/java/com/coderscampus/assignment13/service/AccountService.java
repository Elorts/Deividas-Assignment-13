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

//    @Autowired
//    UserAccountRepository userAccountRepo;



//    public List<Account> getAccounts (Long userId) {
//        return accountRepo.findAll(acc);
//    }

}
