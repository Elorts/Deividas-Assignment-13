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
	
	@GetMapping("/register")
	public String getCreateUser (ModelMap model) {
		model.put("user", new User());

    	return "register";
	}

	@GetMapping("/users")
	public String getAllUsers (ModelMap model) {
		Set<User> users = userService.findAll();
		
		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}

		return "users";
	}
	
	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		Address address = addressService.getAddress(userId);
		List<Account> accounts = user.getAccounts();

		model.put("accounts", accounts);
		model.put("address", address);
		model.put("users", Arrays.asList(user));
		model.put("user", user);

		return "users";
	}
	
	@PostMapping("/users/{userId}")
	public String postOneUser (User user, Address address, Account account, @PathVariable Long userId) {
		userService.saveUser(user, account);
		addressService.saveAddress(address);

		return "redirect:/users/"+user.getUserId();
	}
	
	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		addressService.delete(userId);
		userService.delete(userId);

    	return "redirect:/users";
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String editAccount (@PathVariable Long userId, @PathVariable Long accountId, ModelMap model)	{
		model.put("account", accountService.getAccount(accountId));
		model.put("userId", userId);
		return "account";
	}

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String postAccount (Account account, User user) {
		accountService.saveAccount(account);

		return "redirect:/users/{userId}/accounts/{accountId}";
		}

	@PostMapping("/users/{userId}/accounts")
	public String createAccount (@PathVariable Long userId) {
		System.out.println("USER CONTROLLER, user id: " + userId);  // my test code

		return "redirect:/users/" + userId + "/accounts/" + accountService.createAccount(userId);
	}

}
