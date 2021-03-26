package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import static com.utilities.GeneratePlainPassword.*;
import com.entities.Account;
import com.utilities.DbCon;

import java.awt.datatransfer.StringSelection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import static com.utilities.Util.*;

public class AccountDao implements Dao<Account> {

	public static String TABLE = "accounts";

	public ArrayList<Account> findAll() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		Connection con = DbCon.getConnection();

		try {
			String query = "select * from " + TABLE;
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Account account = new Account();
				account.setAccountId(resultSet.getInt(1));
				account.setDomain(resultSet.getString(2));
				account.setUsername(resultSet.getString(3));
				account.setPassword(resultSet.getString(4));
				account.setEmail(resultSet.getString(5));
				account.setMisc(resultSet.getString(6));

				accounts.add(account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeConnection();
		}

		return accounts;
	}

	public Account save(Account account) {
		Connection con = DbCon.getConnection();

		try {
			String query = "insert into " + TABLE
							+ " (domain, username, password, email, misc) values (?,?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, account.getDomain());
			statement.setString(2, account.getUsername());
			statement.setString(3, account.getPassword());
			statement.setString(4, account.getEmail());
			statement.setString(5, account.getMisc());

			statement.executeUpdate();
			System.out.println(
							"An account " + account.toString() + " was added");

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Same account existed");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeConnection();
		}
		return account;
	}

	public void update(Account account) {
		Connection con = DbCon.getConnection();

		try {
			String query = "update " + TABLE
							+ " set domain = ?, username = ?, password = ?, email = ?, misc = ? where account_id = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, account.getDomain());
			statement.setString(2, account.getUsername());
			statement.setString(3, account.getPassword());
			statement.setString(4, account.getEmail());
			statement.setString(5, account.getMisc());

			statement.setInt(6, account.getAccountId());
			statement.executeUpdate();
			System.out.println("An account " + account.toString()
							+ " was updated");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeConnection();
		}

	}

	public void delete(Account account) {
		Connection con = DbCon.getConnection();
		try {
			String query = "delete from " + TABLE + " where account_id = ?";
			PreparedStatement statement = con.prepareStatement(query);

			statement.setInt(1, account.getAccountId());
			statement.executeUpdate();
			System.out.println("An account " + account.toString()
							+ " was deleted");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeConnection();
		}
	}

	public void delete(int id) {
		Connection con = DbCon.getConnection();
		try {
			String query = "delete from " + TABLE + " where account_id = ?";
			PreparedStatement statement = con.prepareStatement(query);

			statement.setInt(1, id);
			statement.executeUpdate();
			System.out.println("An account at id: " + id + " was deleted");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeConnection();
		}
	}

	public Account findById(int id) {
		Connection con = DbCon.getConnection();
		Account account = new Account();
		try {
			String query = "select * from " + TABLE + " where account_id = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				account.setAccountId(resultSet.getInt(1));
				account.setDomain(resultSet.getString(2));
				account.setUsername(resultSet.getString(3));
				account.setPassword(resultSet.getString(4));
				account.setEmail(resultSet.getString(5));
				account.setMisc(resultSet.getString(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeConnection();
		}
		return account;
	}

	public ArrayList<Account> findByDomain(String domain)
					throws InvalidKeyException, NoSuchAlgorithmException,
					NoSuchPaddingException, IllegalBlockSizeException,
					BadPaddingException, FileNotFoundException,
					InvalidAlgorithmParameterException, IOException {
		String prepDomain = "%" + domain + "%";
		ArrayList<Account> accounts = new ArrayList<Account>();
		Connection con = DbCon.getConnection();

		try {
			String query = "select * from " + TABLE + " where domain like ?";
			;
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, prepDomain);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Account account = new Account();
				account.setAccountId(resultSet.getInt(1));
				account.setDomain(resultSet.getString(2));
				account.setUsername(resultSet.getString(3));
				account.setPassword(resultSet.getString(4));
				account.setEmail(resultSet.getString(5));
				account.setMisc(resultSet.getString(6));

				accounts.add(account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeConnection();
		}
		copyToClipBoard(accounts);
		return accounts;
	}

	public static void copyToClipBoard(ArrayList<Account> accounts)
					throws InvalidKeyException, NoSuchAlgorithmException,
					NoSuchPaddingException, IllegalBlockSizeException,
					BadPaddingException, FileNotFoundException,
					InvalidAlgorithmParameterException, IOException {
		int size = accounts.size();
		if (size == 1) {
			printCollection(accounts);
			System.out.println(
							"1 account found, password is copied to clipboard.");
			String pw = getDecryptedPassword(accounts.get(0).getPassword())
							.trim();
			StringSelection stringSelection = new StringSelection(pw);
			Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} else {
			printCollection(accounts);
			System.out.println(size + " accounts found!!!");
		}
	}

	public static void copyToClipBoard(Account account)
					throws InvalidKeyException, NoSuchAlgorithmException,
					NoSuchPaddingException, IllegalBlockSizeException,
					BadPaddingException, FileNotFoundException,
					InvalidAlgorithmParameterException, IOException {

		System.out.println(account);
		System.out.println("1 account found, password is copied to clipboard.");
		String pw = getDecryptedPassword(account.getPassword()).trim();
		StringSelection stringSelection = new StringSelection(pw);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

	}
}
