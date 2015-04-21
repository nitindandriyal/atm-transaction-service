package com.hackertrail.challenge.service.repo;

import com.hackertrail.challenge.service.dto.AccountStatement;
import com.hackertrail.challenge.service.exceptions.InsufficientFundsException;

public interface AccountDAO {
	double checkBalance(long accountId);

	AccountStatement withdrawFunds(long accountId, double amount) throws InsufficientFundsException;

	AccountStatement creditFunds(long accountId, double amount);
}
