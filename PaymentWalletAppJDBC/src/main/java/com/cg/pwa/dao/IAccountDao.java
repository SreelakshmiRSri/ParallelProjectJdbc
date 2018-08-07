package com.cg.pwa.dao;

import com.cg.pwa.bean.Account;
import com.cg.pwa.exception.AccountException;

public interface IAccountDao {
	
	Account createAccount(Account acc) throws AccountException;

	double showBalance(String mobNo) throws AccountException;

	double deposit(String mobNo, double amount) throws AccountException;

	double withdraw(String mobNo, double amount) throws AccountException;

	double fundTransfer(String srcMobNo, String destMobNo, double amount)throws AccountException;

	Account printTransaction(String mobNo) throws AccountException;
}
