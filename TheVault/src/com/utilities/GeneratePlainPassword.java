package com.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class GeneratePlainPassword {

    public static final String AES = "AES";

    @SuppressWarnings("unused")
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
    
    @SuppressWarnings("unused")
	public static String getDecryptedPassword(String encryptedPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, 
    InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, IOException{
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
		try {
	        prop.load(input); 
        } catch(NullPointerException ex) {
        	System.out.println("Please Insert Key!!!");
        	System.exit(1);
        } finally {
        	if(input != null) {
        		input.close();
        	}
        }
        key = prop.getProperty("Key");
        
        String originalPassword = decryptString(key, encryptedPassword);
        input.close();
        return originalPassword;
    }
    
    private static String decryptString(String key, String encryptedPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, 
    InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, IOException {
        byte[] bytekey = hexStringToByteArray(key);
        SecretKeySpec sks = new SecretKeySpec(bytekey, GeneratePlainPassword.AES);
        Cipher cipher = Cipher.getInstance(GeneratePlainPassword.AES);
        cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] decrypted = cipher.doFinal(hexStringToByteArray(encryptedPassword));
        String OriginalPassword = new String(decrypted);
    	return OriginalPassword;
    }
}