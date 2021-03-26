package com.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.utilities.*;

public class PasswordDemo {
	public static void main(String[] args) {
		String address = ":/vault/key.txt";

		InputStream input = null;

		boolean success = false;
		for (int i = 65; i < 91; i++) {
			if (!success) {
				char drive = (char) i;
				StringBuilder fullAddress = new StringBuilder();
				fullAddress.append(drive);
				fullAddress.append(address);
				System.out.println(fullAddress);
				try {
					input = new FileInputStream(fullAddress.toString());
					break;
				} catch (FileNotFoundException e) {

				}
			}
		}
	}
}