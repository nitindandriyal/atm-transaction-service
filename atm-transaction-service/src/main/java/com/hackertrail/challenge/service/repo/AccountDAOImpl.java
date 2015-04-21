package com.hackertrail.challenge.service.repo;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hackertrail.challenge.service.dto.AccountStatement;
import com.hackertrail.challenge.service.exceptions.InsufficientFundsException;

@Repository
@Transactional(readOnly = true)
public class AccountDAOImpl implements AccountDAO {

	private static final Logger logger = LoggerFactory.getLogger(AccountDAOImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@PersistenceContext
	@Autowired
	private EntityManager em;

	@Autowired
	PlatformTransactionManager transactionManager;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public double checkBalance(long accountId) {
		try {
			Account account = this.getEm().find(Account.class, accountId);
			this.getEm().refresh(account, LockModeType.PESSIMISTIC_READ);
			return account.getAvailableBalance();
		} finally {
			this.getEm().close();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public AccountStatement withdrawFunds(long accountId, double amount) throws InsufficientFundsException {
		try {
			Account account = this.getEm().find(Account.class, accountId);
			this.getEm().refresh(account, LockModeType.PESSIMISTIC_WRITE);
			if (account.getAvailableBalance() < amount) {
				throw new InsufficientFundsException("Insufficient funds for Withdrawl");
			}
			double lastBalance = account.getAvailableBalance();
			account.setAvailableBalance(account.getAvailableBalance() - amount);
			AccountStatement accountStatement = new AccountStatement(accountId, account.getAvailableBalance(), lastBalance, "WITHDRAW");
			logger.info("TXN:" + accountStatement);
			return accountStatement;
		} finally {
			this.getEm().close();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public AccountStatement creditFunds(long accountId, double amount) {
		try {
			Account account = this.getEm().find(Account.class, accountId);
			this.getEm().refresh(account, LockModeType.PESSIMISTIC_WRITE);
			double lastBalance = account.getAvailableBalance();
			account.setAvailableBalance(account.getAvailableBalance() + amount);
			AccountStatement accountStatement = new AccountStatement(accountId, account.getAvailableBalance(), lastBalance, "CREDIT");
			logger.info("TXN:" + accountStatement);
			return accountStatement;
		} finally {
			this.getEm().close();
		}
	}
}
