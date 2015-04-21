package com.hackertrail.challenge.service.web;

import javax.jws.WebService;

import com.hackertrail.challenge.service.dto.AccountStatement;
import com.hackertrail.challenge.service.exceptions.InsufficientFundsException;
import com.hackertrail.challenge.service.repo.AccountDAO;

@WebService(endpointInterface = "com.hackertrail.challenge.service.web.ATMService")
public class ATMServiceImpl implements ATMService {

	private AccountDAO accountDAO;

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Override
	public double checkBalance(long accountId) {
		return accountDAO.checkBalance(accountId);
	}

	@Override
	public AccountStatement withdrawFunds(long accountId, double amount) throws InsufficientFundsException {
		return accountDAO.withdrawFunds(accountId, amount);
	}

	@Override
	public AccountStatement creditFunds(long accountId, double amount) {
		return accountDAO.creditFunds(accountId, amount);
	}
}