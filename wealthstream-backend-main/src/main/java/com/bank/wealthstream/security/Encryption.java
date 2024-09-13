package com.bank.wealthstream.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryption {
    static String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    static String ALGORITHM = "AES";
    static String KEY = "qwertyuiop0123654789?!|*-{][]./&";
    static String VECTOR = "asdfghjkln012365";
    static Base64.Decoder B64Decoder = java.util.Base64.getDecoder();

    public static String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec myKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(VECTOR.getBytes(StandardCharsets.UTF_8));

            cipher.init(Cipher.DECRYPT_MODE, myKey, ivSpec);
            byte[] plainText = cipher.doFinal(B64Decoder.decode(data));
            return new String(plainText);
        } catch (Exception e) {
            System.out.println(e);
            return data;
        }
    }

    public static String aesEncrypt(String data, boolean isHex) {
        try {
            byte[] bytesOutput = null;
            Cipher cases = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec myKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(VECTOR.getBytes(StandardCharsets.UTF_8));
            String encrypted;
            if (isHex) {
                bytesOutput = byteAESEncrypt(data, cases, myKey, ivSpec);
                encrypted = DatatypeConverter.printHexBinary(bytesOutput);
            } else {
                bytesOutput = byteAESEncrypt(data, cases, myKey, ivSpec);
                encrypted = org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(bytesOutput);
            }

            return encrypted;
        } catch (Exception e) {
            System.out.println(e);
            return data;
        }
    }

    private static byte[] byteAESEncrypt(String info, Cipher cases, SecretKeySpec myKey, IvParameterSpec ivspec)
            throws Exception {
        cases.init(1, myKey, ivspec);
        return cases.doFinal(info.getBytes());
    }
}
