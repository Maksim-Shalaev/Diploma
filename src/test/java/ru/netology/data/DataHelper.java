package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    public static final String cardNumberApproved = "4444 4444 4444 4441";
    public static final String cardNumberDeclined = "4444 4444 4444 4442";
    public static final String cardNumberAllZero = "0000 0000 0000 0000";
    public static final String cardNumberInvalid = "1234!b@a1234%:?*";
    public static final String monthAndYearAllZero = "00";
    public static final String extraMonth = "13";
    public static final String monthInvalid = "!q";
    public static final String longMonth = "321";
    public static final String yearInvalid = "!q";
    public static final String cardHolderInvalid = "T@12n3M";
    public static final String cvcCvvAllZero = "000";
    public static final String cvcCvvInvalid = "!Qq";
    public static final String longCvcCvv = "1234";


    public static String getCardNumberDigits15() {
        Faker faker = new Faker();
        return faker.number().digits(15);
    }

    public static String getCardNumberDigits16() {
        Faker faker = new Faker();
        return faker.number().digits(16);
    }

    public static String getCardNumberDigits17() {
        Faker faker = new Faker();
        return faker.number().digits(17);
    }

    public static String getCurrentMonth() {
        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public static String getOneNumber() {
        Faker faker = new Faker();
        return faker.number().digits(1);
    }

    public static String getTwoNumbers() {
        Faker faker = new Faker();
        return faker.number().digits(2);
    }

    public static String getYear() {
        String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public static String getLastYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getFullYear() {
        String fullYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        return fullYear;
    }

    public static String cardHolderNameEn() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public static String cardHolderSurnameEn() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName();
    }

    public static String cardHolderFullNameEn() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    public static String cardHolderNameRu() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName();
    }

    public static String cardHolderSurnameRu() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName();
    }

    public static String cardHolderFullNameRu() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getCvcCvv() {
        Faker faker = new Faker();
        return faker.number().digits(3);
    }

}
