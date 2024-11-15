package com.coderscampus.assignment13.service;

import java.util.Set;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AddressRepository;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private AddressRepository addressRepo;

    public Set<User> findAll() {
        return userRepo.findAllUsersWithAccountsAndAddresses();
    }

    public User findById(Long userId) {
        return userRepo.findById(userId).orElse(new User());
    }

    public User saveNewUser(User user) {
        Account checking = new Account();
        checking.setAccountName("Checking Account");
        checking.getUsers().add(user);

        Account savings = new Account();
        savings.setAccountName("Savings Account");
        savings.getUsers().add(user);

        user.getAccounts().add(checking);
        user.getAccounts().add(savings);

        accountRepo.save(checking);
        accountRepo.save(savings);
        userRepo.save(user);

        Address address = new Address();
        address.setUser(user);
        user.setAddress(address);

        addressRepo.save(address);

        return user;
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public void delete(Long userId) {
        userRepo.customDeletion(userId);
    }
}
