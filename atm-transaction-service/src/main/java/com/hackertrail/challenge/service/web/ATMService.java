package com.hackertrail.challenge.service.web;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.hackertrail.challenge.service.dto.AccountStatement;
import com.hackertrail.challenge.service.exceptions.InsufficientFundsException;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ATMService {
	@WebMethod
	double checkBalance(long accountId);

	@WebMethod
	AccountStatement withdrawFunds(long accountId, double amount) throws InsufficientFundsException;

	@WebMethod
	AccountStatement creditFunds(long accountId, double amount);
}
