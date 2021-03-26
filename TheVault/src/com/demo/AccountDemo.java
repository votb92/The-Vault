package com.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.dao.AccountDao;
import com.entities.Account;
import static com.utilities.Util.*;

public class AccountDemo {

	private static AccountDao dao = new AccountDao();
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, InvalidAlgorithmParameterException, IOException {
		ArrayList<Account> accounts = dao.findByDomain("nextflix");
		printCollection(accounts);
}
}
