package de.insights.endec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static Logger log = LoggerFactory.getLogger(AES.class);

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

    public static byte[] encrypt(byte[] bytToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(bytToEncrypt);
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static byte[] decrypt(byte[] bytToDecrypt, String secret)
    {
        try
        {
            log.info("inside AES secretkey="+secret);
            log.info(bytToDecrypt.toString());
            log.info(Integer.toString(bytToDecrypt.length));
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(bytToDecrypt);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    public static void main(String args[]) {

       byte[] encrypted= encrypt("saurabh".getBytes(),"hello123");
        for(int i=0;i<encrypted.length;i++)
            System.out.print(encrypted[i]+",");
        System.out.println();
       byte[] decrypted = decrypt(encrypted,"hello123");
       for(int i=0;i<decrypted.length;i++)
           System.out.print(decrypted[i]+",");
       System.out.println();
       System.out.print(decrypted.length);
    }
}
