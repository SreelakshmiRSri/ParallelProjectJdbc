package com.cg.pwa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.management.Query;

import com.cg.pwa.bean.Account;
import com.cg.pwa.db.AccountDbUtil;
import com.cg.pwa.exception.AccountException;

public class AccountDaoImpl implements IAccountDao {

	public Account createAccount(Account acc) throws AccountException {
		Connection con = AccountDbUtil.getConnection();
		PreparedStatement stat;

		try {
			con.setAutoCommit(false);

			stat = con.prepareStatement(IQueryMapper.insert);

			stat.setString(1, acc.getCustomerName());
			stat.setString(2, acc.getEmailId());
			stat.setString(3, acc.getMobileNum());
			stat.setDouble(4, acc.getBalance());
			int res = stat.executeUpdate();
			if (res == 1) {
				con.commit();
				return acc;
			} else {
				
				throw new AccountException("The account already exist");
			}
		} catch (AccountException | SQLException e) {
			throw new AccountException(e.getMessage());
		}
	}


	public double showBalance(String mobNo) throws AccountException {
		Connection con = AccountDbUtil.getConnection();
		PreparedStatement stat;
		try {

			con.setAutoCommit(false);
			stat = con.prepareStatement(IQueryMapper.getBal);
			stat.setString(1, mobNo);
			ResultSet res = stat.executeQuery();

			if (res.next()) {
				con.commit();
				return res.getDouble(1);
			} else {
				throw new AccountException("the mobile Number entered is invalid");
			}
		} catch (AccountException | SQLException e) {
			throw new AccountException(e.getMessage());
		}
	}


	public double deposit(String mobNo, double amount) throws AccountException {
		Connection con = AccountDbUtil.getConnection();
		PreparedStatement stat;
		try {
			con.setAutoCommit(false);
			double oldBal = showBalance(mobNo);
			double remainBal = oldBal + amount;

			stat = con.prepareStatement(IQueryMapper.updateBalance);
			stat.setDouble(1, remainBal);
			stat.setString(2, mobNo);
			int bal = stat.executeUpdate();
			if (bal == 1) {
				con.commit();
				return remainBal;

			} else {
				throw new AccountException("balance cannot be updated");
			}

		} catch (AccountException | SQLException e) {
			throw new AccountException(e.getMessage());
		}
	}


	public double withdraw(String mobNo, double amount) throws AccountException {
		Connection con = AccountDbUtil.getConnection();
		PreparedStatement stat;
		try {
			con.setAutoCommit(false);
			if (amount < showBalance(mobNo)) {
				double remainBalance = showBalance(mobNo) - amount;
				stat = con.prepareStatement(IQueryMapper.updateBalance);
				stat.setDouble(1, remainBalance);
				stat.setString(2, mobNo);
				int bal = stat.executeUpdate();
				if (bal == 1) {
					con.commit();
					return remainBalance;
				} else {
					throw new AccountException("the destination mobile number is invalid");
				}
			} else {

				throw new AccountException(
						"the amount entered should be less than balance");
			}
		} catch (AccountException | SQLException e) {
			throw new AccountException(e.getMessage());
		}

	}


	public double fundTransfer(String srcMobNo, String destMobNo, double amount)
			throws AccountException {
		if(showBalance(destMobNo)!=0) {
		double balance = withdraw(srcMobNo, amount);
		deposit(destMobNo, amount);
		return balance;
		}else {
			throw new AccountException("the destination mobile number is invalid");
		}
	}

	public Account printTransaction(String mobNo) throws AccountException {
		Connection con = AccountDbUtil.getConnection();
		PreparedStatement stat;
		try {
			con.setAutoCommit(false);
			stat = con.prepareStatement(IQueryMapper.getacc);
			stat.setString(1, mobNo);
			ResultSet res = stat.executeQuery();

			if (res.next()) {
				con.commit();
				Account acc=new Account();
				acc.setCustomerName(res.getString(2));
				acc.setMobileNum(res.getString(1));
				acc.setEmailId(res.getString(3));
				acc.setBalance(res.getDouble(4));
				return acc;
			} else {
				throw new AccountException("the mobile Number entered is invalid");
			}
		} catch (AccountException | SQLException e) {
			throw new AccountException(e.getMessage());
		}
		

	}
}
