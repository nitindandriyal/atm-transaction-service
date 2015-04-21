package com.hackertrail.challenge.service;

import javax.xml.ws.Endpoint;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hackertrail.challenge.service.repo.Account;
import com.hackertrail.challenge.service.repo.AccountDAO;
import com.hackertrail.challenge.service.repo.AccountRepository;
import com.hackertrail.challenge.service.web.ATMServiceImpl;

public class Main {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(JPAConfiguration.class);
		ctx.refresh();
		
		AccountDAO accountDAO = ctx.getBean(AccountDAO.class);
		ATMServiceImpl atmServiceImpl = new ATMServiceImpl();
		atmServiceImpl.setAccountDAO(accountDAO);
		Endpoint.publish("http://localhost:9999/ws/atm", atmServiceImpl);
		
		// Just create one account for demo
		AccountRepository repository = ctx.getBean(AccountRepository.class);
		Account account = new Account();
		repository.save(account);
		
		Account account1 = new Account();
		repository.save(account1);
		
		Iterable<Account> accounts = repository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Account a : accounts) {
            System.out.println(a);
        }
	}
}
