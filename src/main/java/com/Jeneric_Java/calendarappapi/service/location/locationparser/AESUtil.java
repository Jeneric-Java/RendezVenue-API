package com.Jeneric_Java.calendarappapi.service.location.locationparser;

import org.springframework.web.util.HtmlUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AESUtil {

    protected static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    protected static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        cipherText = HtmlUtils.htmlUnescape(cipherText);

//        byte[] byte24 = Base64.getMimeDecoder()
//                .decode(cipherText);
//
//        byte[] byte16 = new byte[16];
//
//        for (int i = 0; i < 16; i++) {
//            byte16[i] = byte24[i];
//        }

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        byte[] plainText = cipher.doFinal(Base64.getMimeDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    protected static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    protected static SecretKey getKeyFromPassword()
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        List<String> tokens = getTokens();
        String password = tokens.get(0);
        String salt = tokens.get(1);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        return secret;
    }

    protected static IvParameterSpec generateDynamicIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static void writeIvToFile() throws IOException {

        File file = new File("./src/main/resources/iv.jpg");

        IvParameterSpec ivParameterSpec = generateDynamicIv();

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(ivParameterSpec.getIV());
        }
    }

    protected static IvParameterSpec getIv() throws IOException {

        File file = new File("./src/main/resources/iv.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new IvParameterSpec(bytes);
    }

    private static List<String> getTokens() throws IOException {

        List<String> credentials = new ArrayList<>();

        FileReader in = new FileReader("./src/main/resources/password.txt");
        BufferedReader br = new BufferedReader(in);

        String line;
        while ((line = br.readLine()) != null) {
            credentials.add(line);
        }
        in.close();

        return credentials;
    }



}
