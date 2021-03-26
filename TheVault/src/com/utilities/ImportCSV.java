package com.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.dao.AccountDao;
import com.entities.Account;

@SuppressWarnings("unused")
public class ImportCSV {
	private static AccountDao dao = new AccountDao();
	protected static void grabFile(String path) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(path));
		String row;
		row = input.readLine();
		while((row = input.readLine()) != null) {
			String[] account = row.split("," , -1);
			try {
//				System.out.println(account[1]);
//				System.out.println(account[2]);
//				System.out.println(account[3]);
//				System.out.println(account[4]);
//				System.out.println(account[5]);
				Account accountObj = new Account(account[1],account[2],
								account[3],account[4],account[5]);
				dao.save(accountObj);
			}catch(IndexOutOfBoundsException ex) {
			
			}
		}
		input.close();
	}
//	private static String checkCSVNull(String info) throws IndexOutOfBoundsException{
//		try {
//			return info;
//		}catch (IndexOutOfBoundsException ex){
//			return null;
//		}
//	}
}
