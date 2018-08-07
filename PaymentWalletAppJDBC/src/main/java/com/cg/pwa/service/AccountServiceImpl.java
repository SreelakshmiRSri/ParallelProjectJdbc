package com.cg.pwa.service;

import com.cg.pwa.bean.Account;
import com.cg.pwa.dao.AccountDaoImpl;
import com.cg.pwa.dao.IAccountDao;
import com.cg.pwa.exception.AccountException;

public class AccountServiceImpl implements IAccountService {
	IAccountDao accountDao = new AccountDaoImpl();

	@Override
	public Account createAccount(Account acc) throws AccountException {
		if (validateMobNo(acc.getMobileNum())
				&& validateName(acc.getCustomerName())
				&& validateBalance(acc.getBalance())
				&& validateEmail(acc.getEmailId())) {
			return accountDao.createAccount(acc);
		}
		throw new AccountException("Account cannot be created");
	}

	@Override
	public double showBalance(String mobNo) throws AccountException {
		if (!validateMobNo(mobNo)) {
			throw new AccountException("enter 10 digit number ");

		}
		return accountDao.showBalance(mobNo);

	}

	@Override
	public double deposit(String mobNo, double amount) throws AccountException {
		if (validateMobNo(mobNo) && validateAmt(amount)) {
			return accountDao.deposit(mobNo, amount);
		}
		throw new AccountException("Amount cannot be deposited");
	}

	@Override
	public double withdraw(String mobNo, double amount) throws AccountException {
		if (validateMobNo(mobNo) && validateAmt(amount)) {
			return accountDao.withdraw(mobNo, amount);
		}
		throw new AccountException("Amount Cannot be withdrawn");

	}

	
	  @Override public double fundTransfer(String srcMobNo, String destMobNo,
	  double amount) throws AccountException { if (validateMobNo(srcMobNo) &&
	  validateMobNo(destMobNo) && validateAmt(amount)) { return
	  accountDao.fundTransfer(srcMobNo, destMobNo, amount); } throw new
	  AccountException("Amount cannot be transfered"); }
	  
	 @Override public Account printTransaction(String mobNo) throws
	  AccountException { if (validateMobNo(mobNo)){ return
	  accountDao.printTransaction(mobNo);
	  
	  } throw new AccountException("Enter Valid Phone Num"); }
	 

	@Override
	public boolean validateMobNo(String mobileNo) throws AccountException {
		if (!mobileNo.matches("\\d{10}")) {

			throw new AccountException("phone num should contain 10 digits");

		}
		return true;

	}

	@Override
	public boolean validateEmail(String email) throws AccountException {
		if (!email.matches("[a-z0-9]+@[a-z]+\\.com")) {
			throw new AccountException("enter valid email id");
		}
		return true;

	}

	@Override
	public boolean validateName(String name) throws AccountException {
		if (name.isEmpty() || name == null) {
			throw new AccountException("Customer name cannot be empty");

		} else {
			if (!name.matches("[A-Z][A-Za-z\\s]{2,}")) {
				throw new AccountException(
						"name should start with capital letter");
			}
		}
		return true;
	}

	@Override
	public boolean validateBalance(double balance) throws AccountException {
		if (balance <= 0) {
			throw new AccountException(
					"minimum balance should be greater than 0");
		}
		return true;
	}

	@Override
	public boolean validateAmt(double amount) throws AccountException {
		if (amount <= 0) {
			throw new AccountException("amount should be greater than 0");
		}
		return true;

	}

}
