/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption;

import java.io.*;
import java.net.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64; 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
/**
 *
 * @author satal
 */
public class server {
    public static void main(String[] args) throws IOException {
          String clientGelen;
          ServerSocket serverSocket = null;
          Socket clientSocket = null;
         
          int sayi;
 
          try {
               //*Server 7755 portundan Client'ı dinliyor *//
               serverSocket = new ServerSocket(7755);
          } catch (Exception e) {
               System.out.println("Port Hatası!");
          }
          System.out.println("SERVER BAŞLANTI İÇİN HAZIR...");
          clientSocket = serverSocket.accept();
 
          //* Client'a veri gönderimi için kullandığımız PrintWriter nesnesi oluşturulur *//
          PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
 
          //* Client'dan gelen verileri tutan BufferedReader nesnesi oluşturulur *//
          BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 
          while((clientGelen = in.readLine()) != null) {
               final String secretKey = "ssshhhhhhhhhhh!!!!";
        String encryptedString = server.encrypt(clientGelen, secretKey) ;
        String decryptedString = server.decrypt(encryptedString, secretKey) ;
     
        System.out.println(clientGelen);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
          }
         
          out.close();
          in.close();
          clientSocket.close();
          serverSocket.close();
    }
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
