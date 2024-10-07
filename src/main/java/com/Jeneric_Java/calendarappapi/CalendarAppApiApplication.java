package com.Jeneric_Java.calendarappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@SpringBootApplication
public class CalendarAppApiApplication {

	public static void main(String[] args) {
        SpringApplication.run(CalendarAppApiApplication.class, args);
	}

	// Harcoded Initialisation Vector *DO NOT DELETE*
//	public static void main(String[] args) throws IOException {
//		File file = new File("./src/main/resources/iv.jpg");
//
//		byte[] hardcodedIV = { INSERT BYTES HERE };
//		IvParameterSpec ivParameterSpec = new IvParameterSpec(hardcodedIV);
//
//		try (FileOutputStream outputStream = new FileOutputStream(file)) {
//			outputStream.write(ivParameterSpec.getIV());
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//
//		byte[] bytes = Files.readAllBytes(file.toPath());
//		System.out.println(Arrays.toString(new IvParameterSpec(bytes).getIV()));
//
//		SpringApplication.run(CalendarAppApiApplication.class, args);
//	}

}
