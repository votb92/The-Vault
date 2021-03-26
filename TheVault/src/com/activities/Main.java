package com.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.lang3.StringUtils;
import static com.utilities.Util.*;

import com.dao.AccountDao;
import com.entities.Account;
import com.utilities.DbCon;

public class Main {
	private static Scanner input = new Scanner(System.in);
	private static AccountDao dao = new AccountDao();
	private static Boolean On = true;

	public static void main(String[] args) throws InvalidKeyException,
					NoSuchAlgorithmException, NoSuchPaddingException,
					IllegalBlockSizeException, BadPaddingException,
					FileNotFoundException, InvalidAlgorithmParameterException,
					IOException, InterruptedException, SQLException {
		init();
	}

	public static void menu() throws InterruptedException {

		/***************************************************/
		clearConsole();
		welcomeScreenBackground();
		displayOption();

	}

	public static void splitter() throws InvalidKeyException,
					NoSuchAlgorithmException, NoSuchPaddingException,
					IllegalBlockSizeException, BadPaddingException,
					FileNotFoundException, InvalidAlgorithmParameterException,
					IOException, InterruptedException {
		int i = input.nextInt();
		input.nextLine();
		switch (i) {
		case 1:
			System.out.println("Enter account domain: ");
			String domain = input.nextLine();
			dao.findByDomain(domain);
			Thread.sleep(5000);
			clearConsole();
			break;
		case 2:
			System.out.println("Enter account id number: ");
			int number = input.nextInt();
			input.nextLine();
			Account temp = dao.findById(number);
			AccountDao.copyToClipBoard(temp);
			Thread.sleep(5000);
			clearConsole();
			break;
		case 3:
			System.out.println("(* is required fields) \n"
							+ "*To create an account please input a domain (i.e nextflix.com) : ");
			String newDomain = input.nextLine();
			System.out.println("*Next what is your username: ");
			String newUsername = input.nextLine();
			System.out.println("*Next what is your password: ");
			String newPassword = input.nextLine();
			System.out.println("Next what is your email for this domain: ");
			String newEmail = input.nextLine();
			System.out.println("Anything else you want to note : ");
			String newMisc = input.nextLine();
			Account account = new Account();
			account.setDomain(newDomain);
			account.setUsername(newUsername);
			account.setPassword(newPassword, 1);
			account.setEmail(newEmail);
			account.setMisc(newMisc);
			dao.save(account);

			Thread.sleep(4000);
			clearConsole();
			break;
		case 4:
			System.out.println("Enter account id number: ");
			int idNumber = input.nextInt();
			input.nextLine();
			Account temp1 = dao.findById(idNumber);
			System.out.println(temp1);
			System.out.println("Enter the new password:");
			String newInputPassword = input.nextLine();
			temp1.setPassword(newInputPassword, 1);
			dao.update(temp1);
			Thread.sleep(5000);
			clearConsole();
			break;
		case 5:
			System.out.println("Enter account id number: ");
			int deleteNumber = input.nextInt();
			input.nextLine();
			Account temp2 = dao.findById(deleteNumber);
			System.out.println(temp2);
			System.out.println(
							"Are you sure you want to delete this account? 1 - yes, 0 - no:");
			int tempChoice = input.nextInt();
			input.nextLine();
			if (tempChoice == 1) {
				dao.delete(temp2);
			}
			Thread.sleep(5000);
			clearConsole();
			break;
		case 9:
			printCollection(dao.findAll());
			System.out.println("any number to get back to main menu : ");
			i = input.nextInt();
			input.nextLine();
			clearConsole();
			break;
		case 0:
			input.close();
			On = false;
			clearConsole();
			System.out.println("Please take out your key!!!");
			break;
		default:
			// The user input an unexpected choice.
		}
	}

	public static void displayOption() {
		int w = 80;
		String welcome = "Welcome to THE VAULT";
		String[] options = { "Please keep your key inserted!",
						"1 - Find accounts by domain",
						"2 - Find account by id number",
						"3 - Create an account",
						"4 - Update an existing account password (using acount id number)",
						"5 - Delete an account entry", "9 - Show all accounts",
						"0 - Quit" };
		System.out.println(StringUtils.rightPad("+", w - 1, "-") + "+");
		System.out.println(StringUtils
						.center(StringUtils.center(welcome, w - 2), w, "|"));
		System.out.println(StringUtils.rightPad("+", w - 1, "-") + "+");

		System.out.println(StringUtils.rightPad("+", w - 1, "-") + "+");
		for (String option : options) {
			System.out.println(StringUtils
							.center(StringUtils.center(option, w - 2), w, "|"));
		}
		System.out.println(StringUtils.rightPad("+", w - 1, "-") + "+");

	}

	public static void clearConsole() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start()
								.waitFor();
			} else {
				System.out.print("\033\143");
			}
		} catch (IOException | InterruptedException ex) {
		}
	}

	public static void welcomeScreenBackground() {
		System.out.println();
		System.out.println();
		System.out.println("                                  |>>>");
		System.out.println("                                  |");
		System.out.println(
						"                    |>>>      _  _|_  _        |>>>");
		System.out.println("                    |        |;| |;| |;|       |");
		System.out.println(
						"                _  _|_  _    \\.    .  /    _  _|_  _");
		System.out.println(
						"               |;|_|;|_|;|    \\:. ,  /    |;|_|;|_|;|");
		System.out.println(
						"               \\\\..      /    ||;   . |    \\\\.    . /");
		System.out.println(
						"                \\\\.  ,  /     ||:  .  |     \\\\:  . /");
		System.out.println(
						"                 ||:   |_   _ ||_ . _ | _   _||:   |");
		System.out.println(
						"                 ||:  .|||_|;|_|;|_|;|_|;|_|;||:.  |");
		System.out.println(
						"                 ||:   ||.    .     .      . ||:  .|");
		System.out.println(
						"                 ||: . || .     . .   .  ,   ||:   |       \\,/");
		System.out.println(
						"                 ||:   ||:  ,  _______   .   ||: , |            /`\\");
		System.out.println(
						"                 ||:   || .   /+++++++\\    . ||:   |");
		System.out.println(
						"                 ||:   ||.    |+++++++| .    ||: . |");
		System.out.println(
						"              __ ||: . ||: ,  |+++++++|.  . _||_   |");
		System.out.println(
						"     ____--`~    '--~~__|.    |+++++__|----~    ~`---,              ___");
		System.out.println(
						"-~--~                   ~---__|,--~'                  ~~----_____-~'   `~----~~");
		System.out.println();
		System.out.println();
	}

	public static boolean findKey()
					throws NoSuchAlgorithmException, NoSuchPaddingException,
					InvalidKeyException, IllegalBlockSizeException,
					BadPaddingException, FileNotFoundException, IOException {

		String address = ":/vault/key.txt";

		boolean success = false;
		for (int i = 65; i < 91; i++) {
			if (!success) {
				char drive = (char) i;
				StringBuilder fullAddress = new StringBuilder();
				fullAddress.append(drive);
				fullAddress.append(address);
				try (InputStream inputStream = new FileInputStream(
								fullAddress.toString())) {
					return true;
				} catch (FileNotFoundException e) {

				}
			}
		}
		return false;
	}

	public static void init() throws InvalidKeyException,
					NoSuchAlgorithmException, NoSuchPaddingException,
					IllegalBlockSizeException, BadPaddingException,
					FileNotFoundException, IOException, InterruptedException,
					InvalidAlgorithmParameterException, SQLException {
		Boolean keyInserted = false;
		int count = 0;
		while (keyInserted = findKey() == false && count < 2) {
			System.out.print("Looking for key");
			for (int i = 1; i <= 3; i++) {
				System.out.print(".");
				Thread.sleep(1000);
			}
			Thread.sleep(2000);
			keyInserted = findKey();
			clearConsole();
			count++;
		}

		if (keyInserted = findKey()) {
			while (On) {
				DbCon.load(0);
				menu();
				splitter();
			}
		} else {
			System.out.println(
							"No key found,type 0 to retry, type 1 to create a new vault, any other number to exit:");
			int choice = input.nextInt();
			input.nextLine();
			if (choice == 1) {
				System.out.println("New account setup");
				Thread.sleep(1000);
				DbCon.setup();
				init();
			} else if (choice == 0) {
				init();
				System.exit(1);
			} else {
				System.exit(1);
			}
		}
	}
}
