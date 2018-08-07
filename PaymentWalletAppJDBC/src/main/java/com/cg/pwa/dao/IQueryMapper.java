package com.cg.pwa.dao;

public interface IQueryMapper {
	public String insert="insert into account(customerName,emailId,mobileNum,balance) values(?,?,?,?)";
	public String getBal="select balance from account where mobileNum=?";
	public String updateBalance="update account set balance=? where mobileNum=?";
	public String getacc="select * from account where mobileNum=?";
}
