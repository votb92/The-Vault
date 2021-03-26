package com.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class GenerateEncryptionPassword {

    public static final String AES = "AES";

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
    
	public static String setDecryptedPassword(String Password) throws NoSuchAlgorithmException, NoSuchPaddingException, 
    InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, IOException, InvalidAlgorithmParameterException{
    	String key = "";
        Properties prop = new Properties();
        InputStream input = null;
        
		String address = ":/vault/key.txt";

		boolean success = false;
		for(int i = 65; i < 91; i++) {
			if(!success) {
			char drive = (char)i;
			StringBuilder fullAddress = new StringBuilder();
			fullAddress.append(drive);
			fullAddress.append(address);
            try {
            	input = new FileInputStream(fullAddress.toString());
                break;
            } catch (FileNotFoundException e){}
			}
		}
        // load a properties file
        prop.load(input);
        key = prop.getProperty("Key");
        input.close();
        String encryptedPassword = encryptString(key, Password);
        return encryptedPassword;
    }
    
    private static String encryptString(String key, String password) throws NoSuchAlgorithmException, 
    NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, 
    BadPaddingException, IOException {
        byte[] bytekey = hexStringToByteArray(key);
        SecretKeySpec sks = new SecretKeySpec(bytekey, GenerateEncryptionPassword.AES);
        Cipher cipher = Cipher.getInstance(GenerateEncryptionPassword.AES);
        cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
        byte[] encrypted = cipher.doFinal(password.getBytes());
        String encryptedpwd = byteArrayToHexString(encrypted);
        return encryptedpwd;
    }
    
    public static void main(String args[]) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {

        System.out.println(setDecryptedPassword("Tuijdhg"));
//        byte[] bytekey = hexStringToByteArray(key);
//        SecretKeySpec sks = new SecretKeySpec(bytekey, GenerateEncryptionPassword.AES);
//        Cipher cipher = Cipher.getInstance(GenerateEncryptionPassword.AES);
//        cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
//        byte[] encrypted = cipher.doFinal(password.getBytes());
//        String encryptedpwd = byteArrayToHexString(encrypted);
//        System.out.println("****************  Encrypted Password  ****************");
//        System.out.println(encryptedpwd);
//        System.out.println("****************  Encrypted Password  ****************");

    }
}
