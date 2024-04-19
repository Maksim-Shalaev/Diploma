package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.clearTables;

public class DebitCardTest {

    private MainPaymentPage mainPaymentPage = new MainPaymentPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    public void cleanTables() {
        clearTables();
    }

    @DisplayName("1 Payment for the tour by debit card, status APPROVED")
    @Test
    void shouldBeAllowedByApprovedDebitCard() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveSuccessNotification();
        assertEquals("APPROVED", new SQLHelper().getPaymentStatus());
    }

    @DisplayName("2 Payment for the tour by debit card, status DECLINED")
    @Test
    void shouldBeRefusedByDeclinedDebitCard() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(cardNumberDeclined);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertEquals("DECLINED", new SQLHelper().getPaymentStatus());
    }

    @DisplayName("3 Payment for the tour by debit valid generated card number")
    @Test
    void shouldBeRefusedByValidCardNumber() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("4 Payment for the tour by debit card with short number")
    @Test
    void shouldBeErrorNotificationForShortCardNumber() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits15());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("5 Payment for the tour by debit card with long number")
    @Test
    void shouldBeErrorNotificationForLongCardNumber() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits17());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("6 Payment for the tour by debit card with an empty input field")
    @Test
    void shouldBeErrorNotificationWhenCardFieldIsEmpty() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField("");
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("7 Payment for the tour by debit card with all zero in input field")
    @Test
    void shouldBeErrorNotificationWhenAllZero() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.cardNumberAllZero);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("8 Payment for the tour by debit card with invalid number")
    @Test
    void shouldBeErrorNotificationWhenInvalidCardNumber() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.cardNumberInvalid);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("9 Payment for the tour by debit card with short month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsShort() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.getOneNumber());
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("10 Payment for the tour by debit card with empty month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsEmpty() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField("");
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("11 Payment for the tour by debit card with zero month period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsZero() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.monthAndYearAllZero);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("12 Payment for the tour by debit card with non-existen month period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsNonExisten() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.extraMonth);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("13 Payment for the tour by debit card with invalid month period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsInvalid() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.monthInvalid);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("14 Payment for the tour by debit card with long month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsLong() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(longMonth);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("15 Payment for the tour by debit card with short year validity period")
    @Test
    void shouldBeErrorNotificationWhenYearPeriodIsShort() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardYearField(getOneNumber());
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("16 Payment for the tour by debit card with empty year validity period")
    @Test
    void shouldBeErrorNotificationWhenYearPeriodIsEmpty() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardYearField("");
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("17 Payment for the tour by debit card with zero year period")
    @Test
    void shouldBeErrorNotificationWhenYearPeriodIsZero() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardYearField(DataHelper.monthAndYearAllZero);
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationCardExpired();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("18 Payment for the tour by debit card with last year")
    @Test
    void shouldBeErrorNotificationWhenLastYear() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardYearField(DataHelper.getLastYear());
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationCardExpired();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("19 Payment for the tour by debit card with invalid year period")
    @Test
    void shouldBeErrorNotificationWhenYearPeriodIsInvalid() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardYearField(yearInvalid);
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("20 Payment for the tour by debit card with full year validity period")
    @Test
    void shouldBeErrorNotificationWhenFullPeriodIsLong() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardYearField(DataHelper.getFullYear());
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationCardExpired();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("21 Payment for the tour by debit card with english letters in name of card holder")
    @Test
    void shouldBeErrorNotificationWrongFormatName() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderNameEn());
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("22 Payment for the tour by debit card with english letters in surname of card holder")
    @Test
    void shouldBeErrorNotificationWrongFormatSurname() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderSurnameEn());
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("23 Payment for the tour by debit card with russian letters in name of card holder")
    @Test
    void shouldBeErrorNotificationWrongFormatNameRu() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderNameRu());
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("24 Payment for the tour by debit card with russian letters in surname of card holder")
    @Test
    void shouldBeErrorNotificationWrongFormatSurnameRu() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderSurnameRu());
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("25 Payment for the tour by debit card empty field in name of card holder")
    @Test
    void shouldBeErrorNotificationRequiredField() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardHolderField(" ");
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationRequiredField();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("26 Payment for the tour by debit card with invalid card holder")
    @Test
    void shouldBeErrorNotificationInvalidFormat() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardHolderField(cardHolderInvalid);
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("27 Payment for the tour by debit card with one number of CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfCvcCvv1() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getOneNumber());
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("28 Payment for the tour by debit card with two numbers of CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfCvcCvv2() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getTwoNumbers());
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("29 Payment for the tour by debit card with empty CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfEmptyCvcCvv() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(" ");
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("30 Payment for the tour by debit card with all zero CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfAllZero() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(cvcCvvAllZero);
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("31 Payment for the tour by debit card with invalid CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidCvcCvv() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(cvcCvvInvalid);
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("32 Payment for the tour by debit card with long CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfLongCvcCvv() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(longCvcCvv);
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    private void filloutOtherFieldsByValidDataForCardCheck() {
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }

    private void filloutOtherFieldsByValidDataForMonthCheck() {
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }

    private void filloutOtherFieldsByValidDataForYearCheck() {
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }

    private void filloutOtherFieldsByValidDataForNameAndSurnameCheck() {
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }

    private void filloutOtherFieldsByValidDataForCvcCvvCheck() {
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.clickContinueButton();
    }
}
