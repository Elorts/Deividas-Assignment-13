package com.coderscampus.assignment13.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserAccount {

    private Long userId;
    private Long accountId;

    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
