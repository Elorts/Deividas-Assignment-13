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
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final AddressService addressService;
    private final AccountService accountService;

    public UserController(UserService userService, AddressService addressService, AccountService accountService) {
        this.userService = userService;
        this.addressService = addressService;
        this.accountService = accountService;
    }

    @GetMapping("/welcome")
    public String getWelcomeView() {
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
        return "redirect:/users";
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
                model.put("address", createDefaultAddress());
            }
        }

        return "users";
    }

    @GetMapping("/user/{userId}")
    public String getOneUser(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        Address address = addressService.getAddress(userId);
        model.put("user", user);
        model.put("users", Arrays.asList(user));
        model.put("address", address);
        model.put("accounts", user.getAccounts());
        return "user";
    }

    @PostMapping("/user/{userId}")
    public String postOneUser(User user, Address address) {
        if (user.getPassword().isEmpty()) {
            user.setPassword(userService.findById(user.getUserId()).getPassword());
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
    public String postAccount(Account account) {
        accountService.saveAccount(account);
        return "redirect:/user/{userId}/accounts/{accountId}";
    }

    @PostMapping("/user/{userId}/accounts")
    public String createAccount(@PathVariable Long userId) {
        Long accountId = accountService.createAccount(userId);
        return "redirect:/user/" + userId + "/accounts/" + accountId;
    }

    private Address createDefaultAddress() {
        Address address = new Address();
        address.setAddressLine1(" ");
        address.setAddressLine2(" ");
        address.setCity(" ");
        address.setRegion(" ");
        address.setCountry(" ");
        address.setZipCode(" ");
        return address;
    }
}
