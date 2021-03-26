package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import static com.utilities.GenerateKey.*;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.mysql.cj.protocol.FullReadInputStream;

public class DbCon {
	private static Scanner input = new Scanner(System.in);
	private static Connection con = null;
//	private static String ADDRESS = ":/keys/vault/key.txt";
	private static String ADDRESS = ":/vault/key.txt";
	private static String DATABASE = "thevault";
	private static String TABLE = "accounts";
	private static String URL;
	private static String USER;
	private static String PASSWORD;

	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}

	public static void closeConnection() {

		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setup() throws SQLException, NoSuchAlgorithmException {
		System.out.println(
						"Make sure you have mySQL installed before this step!!!");
		System.out.println(
						"Which Drive do you want to save your key? (suggest flask drive, examples: D, F) :");
		String drive = input.nextLine();
		StringBuilder fullAddress = new StringBuilder();
		fullAddress.append(drive);
		fullAddress.append(ADDRESS);
		System.out.println(fullAddress);

		String newKey;
		String newPort = "localhost:3306";
		String mySQLUsername = "root";
		String mySQLPassword = "root";
		System.out.println(
						"Please enter mySQL port or hit enter for default (default:\"localhost:3306\") + \n"
										+ "If yours is 3360 enter \"localhost:3306\": ");
		String temp;
		if (!(temp = input.nextLine()).equals("")) {
			newPort = temp;
		}
		System.out.println(
						"Please enter mySQL username or hit enter for default (default:\"root\"): ");
		if (!(temp = input.nextLine()).equals("")) {
			mySQLUsername = temp;
		}
		System.out.println(
						"Please enter mySQL password or hit enter for default (default:\"root\"): ");
		if (!(temp = input.nextLine()).equals("")) {
			mySQLUsername = temp;
		}

		KeyGenerator keyGen = KeyGenerator.getInstance(GenerateKey.AES);
		keyGen.init(128);
		SecretKey sk = keyGen.generateKey();
		newKey = byteArrayToHexString(sk.getEncoded());

		try {
			File myObj = new File(fullAddress.toString());
			if (myObj.getParentFile().mkdir()) {
				myObj.createNewFile();
			} else {
				throw new IOException("Failed to create directory "
								+ myObj.getParent());
			}
			FileWriter myWriter = new FileWriter(fullAddress.toString());
			myWriter.write("Key=" + newKey + "\n");
			myWriter.write("URL=jdbc:mysql://" + newPort + "/" + DATABASE
							+ "\n");
			myWriter.write("USER=" + mySQLUsername + "\n");
			myWriter.write("PASSWORD=" + mySQLPassword + "\n");
			myWriter.close();
			System.out.println("Successfully create the key.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void load(int i) throws IOException {
		if (i == 1) {
			URL = "jdbc:mysql://localhost:3306/vault";
			USER = "root";
			PASSWORD = "root";
		} else if (i == 0) {
			Properties prop = new Properties();
			InputStream input = null;

			String address = ADDRESS;

			boolean success = false;
			for (int j = 65; j < 91; j++) {
				if (!success) {
					char drive = (char) j;
					StringBuilder fullAddress = new StringBuilder();
					fullAddress.append(drive);
					fullAddress.append(address);
					try {
						input = new FileInputStream(fullAddress.toString());
						break;
					} catch (FileNotFoundException e) {
					}
				}
			}
			// load a properties file
			try {
				prop.load(input);
			} catch (NullPointerException ex) {
				System.out.println("Please Insert Key!!!");
				System.exit(1);
			} finally {
				if (input != null) {
					input.close();
				}
			}
			URL = prop.getProperty("URL");
			USER = prop.getProperty("USER");
			PASSWORD = prop.getProperty("PASSWORD");
			input.close();
		}
	}
}
