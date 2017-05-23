package com.mobitill.barandrestaurant.utils.interceptors;

import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by james on 5/23/2017.
 */

public class AESEncryptor {

    public static final String TAG = AESEncryptor.class.getSimpleName();

    private static final String password = "tfdsfsafedfsdfsfsd";
    private static String salt;
    private static int pswdIterations = 65536  ;
    private static int keySize = 256;
    private byte[] ivBytes;


    public static String encrypt(String plainText) throws Exception{
        salt =  "38939jndnid00wjjdjdooiso00e3jedmnmnso00";
        byte[] saltBytes = salt.getBytes();

        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
        );


        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Log.i(TAG, "encrypt: " + bytesToHex(secret.getEncoded()) + " size "
                + bytesToHex(secret.getEncoded()).length());
        //Encrypt the message
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return bytesToHex(encryptedTextBytes);

    }


    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String decrypt(String encryptedText) throws Exception {

        salt =  "38939jndnid00wjjdjdooiso00e3jedmnmnso00";
        byte[] saltBytes = salt.getBytes("UTF-8");


        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
        );

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // Decrypt the message
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);

        byte[] decryptedTextBytes = null;

        byte[] encryptedTextBytes = hexStringToByteArray(encryptedText);
        for(int i = 1; i <= 2; i++){
            try {

                decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
                break;
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                cipher = Cipher.getInstance("AES/ECB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secret);
                decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
                //encryptedText.concat("10101010101010101010101010101010");
                Log.i(TAG, "decrypt: " + "continue on BadPaddingException");
                continue;
                //e.printStackTrace();
            }
        }


        return new String(decryptedTextBytes);
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String s = new String(bytes);
        return s;
    }
}
