package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.service.AccountService;

import com.coderscampus.assignment13.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;

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
	
//	@PostMapping("/register")
//	public String postCreateUser (User user) {
//		System.out.println(user);
//		userService.saveUser(user, account);
//		return "redirect:/register";
//	}
	
	@GetMapping("/users")
	public String getAllUsers (ModelMap model) {
		Set<User> users = userService.findAll();
		
		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}

		// System.out.println("User account: " + );

		return "users";
	}
	
	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		Address address = addressService.getAddress(userId);
		//Account account =

		List<Account> accounts = user.getAccounts();
//		for (Account a : accounts) {
//			System.out.println(a.getAccountName());
//		}
//		List<Account> account = accountService.getAccounts(userId);
//
		model.put("accounts", accounts);
		model.put("address", address);
		model.put("users", Arrays.asList(user));
		model.put("user", user);
		return "users";
	}
	
	@PostMapping("/users/{userId}")
	public String postOneUser (User user, Address address, Account account) { //}, Account account) {

		System.out.println("user ID:" + user.getUserId());
		System.out.println("user.getAccounts: " + user.getAccounts().size());
		System.out.println("user.getName: " + user.getName());

		System.out.println("account id:" + account.getAccountId());
		System.out.println("account name:" + account.getAccountName());


		userService.saveUser(user, account);
		addressService.saveAddress(address);
		//accountService.saveAccount(account);
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
		return "account";
	}

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String postAccount (Account account, User user) {
		accountService.saveAccount(account);
		return "redirect:/users/{userId}/accounts/{accountId}";
		}

	@PostMapping("/users/{userId}/accounts")
	public String createAccount (@PathVariable Long userId) {
		//System.out.println("account name: " + account.getAccountName());
		//System.out.println("user: " + user.getName());

		System.out.println("USER CONTROLLER, user id: " + userId);  // my test code


		return "redirect:/users/" + userId + "/accounts/" + accountService.createAccount(userId);
	}

}
