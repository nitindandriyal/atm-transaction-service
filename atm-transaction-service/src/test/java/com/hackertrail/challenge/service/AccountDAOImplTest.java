package com.hackertrail.challenge.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hackertrail.challenge.service.exceptions.InsufficientFundsException;
import com.hackertrail.challenge.service.repo.Account;
import com.hackertrail.challenge.service.repo.AccountDAO;
import com.hackertrail.challenge.service.repo.AccountRepository;
import com.hackertrail.challenge.service.web.ATMServiceImpl;

public class AccountDAOImplTest {
	// Class under test
	private AccountDAO accountDAO;
	private AnnotationConfigApplicationContext ctx;

	@Before
	public void setUp() {
		ctx = new AnnotationConfigApplicationContext();
		ctx.register(JPAConfiguration.class);
		ctx.refresh();
		accountDAO = ctx.getBean(AccountDAO.class);
		ATMServiceImpl atmServiceImpl = new ATMServiceImpl();
		atmServiceImpl.setAccountDAO(accountDAO);
		AccountRepository repository = ctx.getBean(AccountRepository.class);
		Account account = new Account();
		repository.save(account);

		Iterable<Account> accounts = repository.findAll();
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Account a : accounts) {
			System.out.println(a);
		}
	}

	@Test
	public void testCheckBalance() {
		AccountRepository repository = ctx.getBean(AccountRepository.class);
		Account account = new Account();
		repository.save(account);
		assertTrue(accountDAO.checkBalance(account.getAccountNumber()) == 0.0);

		double availableBalance = accountDAO.checkBalance(1);
		assertTrue(accountDAO.checkBalance(1) == availableBalance);
	}

	@Test(expected = InsufficientFundsException.class)
	public void testWithDrawOverAvailableBalance() {
		accountDAO.withdrawFunds(1, 100000);
	}

	@Test
	public void testCreditAmount() {
		double currentBalance = accountDAO.checkBalance(1);
		accountDAO.creditFunds(1, 1000);
		assertTrue(currentBalance + 1000 == accountDAO.checkBalance(1));
	}

	@Test
	public void testConcrrentTransactionsConsistency() {
		List<Thread> list = new ArrayList<Thread>();
		double initialAvailableBalance = accountDAO.checkBalance(1);
		for (int i = 0; i < 5; i++) {
			Thread t = new Thread() {
				@Override
				public void run() {
					System.out.println("TXN:" + accountDAO.creditFunds(1, 200));
					System.out.println("TXN:" + accountDAO.withdrawFunds(1, 100));
				};
			};
			list.add(t);
			t.start();
		}

		for (Thread t : list) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		assertTrue(initialAvailableBalance + 500 == accountDAO.checkBalance(1));
	}
}
