package com.coderscampus.assignment13.repository;

import com.coderscampus.assignment13.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
