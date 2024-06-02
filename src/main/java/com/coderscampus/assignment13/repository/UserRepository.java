package com.coderscampus.assignment13.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.assignment13.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	// select * from users where username = :username
	List<User> findByUsername(String username);
	
	// select * from users where name = :name
	List<User> findByName(String name);
	
	// select * from users where name = :name and username = :username
	List<User> findByNameAndUsername(String name, String username);
	
	List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2);
	
	@Query("select u from User u where username = :username")
	List<User> findExactlyOneUserByUsername(String username);
	
	@Query("select u from User u"
		+ " left join fetch u.accounts"
		+ " left join fetch u.address")
	Set<User> findAllUsersWithAccountsAndAddresses();
}
