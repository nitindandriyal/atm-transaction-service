package com.hackertrail.challenge.service.dto;

public class AccountStatement {
	private long accountId;
	private double availableBalance;
	private double lastBalance;
	private String lastTransaction;
	
	public AccountStatement(long accountId, double availableBalance, double lastBalance, String lastTransaction) {
		this.setAccountId(accountId);
		this.setAvailableBalance(availableBalance);
		this.setLastBalance(lastBalance);
		this.setLastTransaction(lastTransaction);
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public double getLastBalance() {
		return lastBalance;
	}

	public void setLastBalance(double lastBalance) {
		this.lastBalance = lastBalance;
	}

	public String getLastTransaction() {
		return lastTransaction;
	}

	public void setLastTransaction(String lastTransaction) {
		this.lastTransaction = lastTransaction;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountStatement [accountId=");
		builder.append(accountId);
		builder.append(", availableBalance=");
		builder.append(availableBalance);
		builder.append(", lastBalance=");
		builder.append(lastBalance);
		builder.append(", lastTransaction=");
		builder.append(lastTransaction);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(availableBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lastBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lastTransaction == null) ? 0 : lastTransaction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountStatement other = (AccountStatement) obj;
		if (accountId != other.accountId)
			return false;
		if (Double.doubleToLongBits(availableBalance) != Double.doubleToLongBits(other.availableBalance))
			return false;
		if (Double.doubleToLongBits(lastBalance) != Double.doubleToLongBits(other.lastBalance))
			return false;
		if (lastTransaction == null) {
			if (other.lastTransaction != null)
				return false;
		} else if (!lastTransaction.equals(other.lastTransaction))
			return false;
		return true;
	}
}
