package com.coderscampus.assignment13.service;

import java.time.LocalDate;
import java.util.*;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private AddressService addressService;

    public List<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findByNameAndUsername(String name, String username) {
        return userRepo.findByNameAndUsername(name, username);
    }

    public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
        return userRepo.findByCreatedDateBetween(date1, date2);
    }

    public User findExactlyOneUserByUsername(String username) {
        List<User> users = userRepo.findExactlyOneUserByUsername(username);
        if (users.size() > 0)
            return users.get(0);
        else
            return new User();
    }

    public Set<User> findAll() {
        return userRepo.findAllUsersWithAccountsAndAddresses();
    }

    public User findById(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        return userOpt.orElse(new User());
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

        return null;
    }

    public User saveUser(User user) {
        userRepo.save(user);
        return null;
    }

    public void delete(Long userId) {
        userRepo.customDeletion(userId);
    }
}
