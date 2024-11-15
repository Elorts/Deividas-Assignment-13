package com.coderscampus.assignment13.web;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.AddressService;
import com.coderscampus.assignment13.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/welcome")
    public String getWelcomeView(ModelMap model) {

        return "welcome";
    }

    @GetMapping("/register")
    public String getCreateUserView(ModelMap model) {
        model.put("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String postCreatedUser(User user) {
        userService.saveNewUser(user);

        return "redirect:/register";
    }

    @GetMapping("/users")
    public String getAllUsers(ModelMap model) {
        Set<User> users = userService.findAll();
        model.put("users", users);

        if (users.size() == 1) {
            User user = users.iterator().next();
            Address address = user.getAddress();
            model.put("user", user);

            if (address != null) {
                model.put("address", address);
            } else {
                Address defaultAddress = new Address();
                defaultAddress.setAddressLine1(" ");
                defaultAddress.setAddressLine2(" ");
                defaultAddress.setCity(" ");
                defaultAddress.setRegion(" ");
                defaultAddress.setCountry(" ");
                defaultAddress.setZipCode(" ");
                model.put("address", defaultAddress);
            }
        }

        return "users";
    }

    @GetMapping("/user/{userId}")
    public String getOneUser(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        Address address = addressService.getAddress(userId);
        List<Account> accounts = user.getAccounts();
        model.put("accounts", accounts);
        model.put("address", address);
        model.put("users", Arrays.asList(user));
        model.put("user", user);

        return "user";
    }

    @PostMapping("/user/{userId}")
    public String postOneUser(User user, Address address) {

        System.out.println("user id from view: " + user.getUserId());

        System.out.println("user.getPassword() after edit:" + user.getPassword());


        if (user.getPassword().isEmpty()) {
            System.out.println("user.getPassword() from view is empty SO WE ARE POPULATING IT WITH ONE FROM DB!");
            user.setPassword(userService.findById(user.getUserId()).getPassword());
        } else {
            System.out.println("CHANGED PASSWORD IN VIEW LEFT AS IS");
            System.out.println("new password:" + user.getPassword());
        }


        userService.saveUser(user);
        addressService.saveAddress(address);

        return "redirect:/user/" + user.getUserId();
    }

    @PostMapping("/user/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        addressService.delete(userId);
        userService.delete(userId);

        return "redirect:/users";
    }

    @GetMapping("/user/{userId}/accounts/{accountId}")
    public String editAccount(@PathVariable Long userId, @PathVariable Long accountId, ModelMap model) {
        model.put("account", accountService.getAccount(accountId));
        model.put("userId", userId);

        return "account";
    }

    @PostMapping("/user/{userId}/accounts/{accountId}")
    public String postAccount(Account account, User user) {
        accountService.saveAccount(account);

        return "redirect:/user/{userId}/accounts/{accountId}";
    }

    @PostMapping("/user/{userId}/accounts")
    public String createAccount(@PathVariable Long userId) {

        return "redirect:/user/" + userId + "/accounts/" + accountService.createAccount(userId);
    }
}
