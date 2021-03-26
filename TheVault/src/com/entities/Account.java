package com.entities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.utilities.*;
import static com.utilities.GenerateEncryptionPassword.*;
import static com.utilities.GeneratePlainPassword.*;

public class Account {
	private static Cipher cipher;  
	
	private Integer accountId;
	private String domain;
	private String username;
	private String password;
	private String email;
	private String misc;
	public Account() {
		
	}
	public Account(String domain, String username, String password,
					String email, String misc) {
		super();
		this.domain = domain;
		this.username = username;
		this.password = password;
		this.email = email;
		this.misc = misc;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		
		this.username = username;
	}
	public String getPassword() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, InvalidAlgorithmParameterException, IOException {
        return setDecryptedPassword(this.password);
	}
	public void setPassword(String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, IOException{
		this.password = getDecryptedPassword(password);
	}
	public void setPassword(String password, int i) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, IOException{
		if(i==1) {
		this.password = password;
		}
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMisc() {
		return misc;
	}
	public void setMisc(String misc) {
		this.misc = misc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
						+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((misc == null) ? 0 : misc.hashCode());
		result = prime * result
						+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
						+ ((username == null) ? 0 : username.hashCode());
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
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (misc == null) {
			if (other.misc != null)
				return false;
		} else if (!misc.equals(other.misc))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [accountId=");
		builder.append(accountId);
		builder.append(", domain=");
		builder.append(domain);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", misc=");
		builder.append(misc);
		builder.append("]");
		return builder.toString();
	}
}
