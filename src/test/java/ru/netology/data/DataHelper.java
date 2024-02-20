package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    public static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

}
