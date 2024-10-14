package com.Jeneric_Java.calendarappapi.service.location.locationparser;

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

class LocationParserTest {

    private static final String algorithm = "AES/CBC/PKCS5Padding";

    @Test
    @DisplayName("Generates key and initialisation vector to encrypt plaintext string, passes cipher text output to location parser method returns matching decrypted plaintext string.")
    void testDecryptEncipheredLocation()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException, IOException {

        // Arrange
        String input = "gcqdsf542";
        LocationParser locationParser = new LocationParser();

        SecretKey key = AESUtil.getKeyFromPassword();
        IvParameterSpec ivParameterSpec = AESUtil.getIv();

        String cipherText = AESUtil.encrypt(algorithm, input, key, ivParameterSpec);

        System.out.println(cipherText);
        String plainText = locationParser.parseEncrypted(cipherText);

        Assertions.assertEquals(input, plainText);
    }
}