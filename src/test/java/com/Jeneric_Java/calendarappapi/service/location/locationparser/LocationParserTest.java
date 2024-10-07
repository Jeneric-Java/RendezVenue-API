package com.Jeneric_Java.calendarappapi.service.location.locationparser;

import com.Jeneric_Java.calendarappapi.service.location.locationparser.AESUtil;
import com.Jeneric_Java.calendarappapi.service.location.locationparser.LocationParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

class LocationParserTest {

    private static final String algorithm = "AES/CBC/PKCS5Padding";

    @Test
    @DisplayName("Generates key and initialisation vector to encrypt plaintext string, passes cipher text output to location parser method returns matching decrypted plaintext string.")
    void testDecryptEncipheredLocation()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException, IOException {

        // Arrange
        String input = "gcw2hzwzj";
        LocationParser locationParser = new LocationParser();

        SecretKey key = AESUtil.getKeyFromPassword();
        IvParameterSpec ivParameterSpec = AESUtil.getIv();

//        System.out.println(Arrays.toString(ivParameterSpec.getIV()));

        String cipherText = AESUtil.encrypt(algorithm, input, key, ivParameterSpec);

        System.out.println(cipherText);
        String plainText = locationParser.parseEncrypted(cipherText);

        Assertions.assertEquals(input, plainText);
    }

//    @Test
//    @DisplayName("Generates key and initialisation vector to encrypt plaintext string, passes cipher text output to location parser method returns matching decrypted plaintext string.")
//    void testDecryptAndEncryptFromPWDerivedKey()
//            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
//            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException {
//
//        // Arrange
//        LocationParser locationParser = new LocationParser();
//
//        List<String> creds = locationParser.ioPassword();
//
//        Assertions.assertEquals("5#@7+r-S.99jk!(u*?//BcV^", creds.get(0));
//        Assertions.assertEquals("47109625", creds.get(1));
//    }


}