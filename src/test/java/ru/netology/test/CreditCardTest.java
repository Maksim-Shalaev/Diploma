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

public class CreditCardTest {

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

    @DisplayName("1 Payment for the tour by credit card, status APPROVED")
    @Test
    void shouldBeAllowedByApprovedCreditCard() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveSuccessNotification();
        assertEquals("APPROVED", new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("2 Payment for the tour by credit card, status DECLINED")
    @Test
    void shouldBeRefusedByDeclinedCreditCard() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardNumberField(cardNumberDeclined);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertEquals("DECLINED", new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("3 Payment for the tour by credit valid generated card number")
    @Test
    void shouldBeRefusedByValidCreditCardNumber() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("4 Payment for the tour by credit card with short number")
    @Test
    void shouldBeErrorNotificationForShortCreditCardNumber() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits15());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("5 Payment for the tour by credit card with long number")
    @Test
    void shouldBeErrorNotificationForLongCreditCardNumber() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits17());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("6 Payment for the tour by credit card with an empty input field")
    @Test
    void shouldBeErrorNotificationWhenCreditCardFieldIsEmpty() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardNumberField("");
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("7 Payment for the tour by credit card with all zero in input field")
    @Test
    void shouldBeErrorNotificationWhenAllZero() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.cardNumberAllZero);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("8 Payment for the tour by credit card with invalid number")
    @Test
    void shouldBeErrorNotificationWhenInvalidCreditCardNumber() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.cardNumberInvalid);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("9 Payment for the tour by credit card with short month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsShort() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.getOneNumber());
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("10 Payment for the tour by credit card with empty month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsEmpty() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardMonthField("");
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("11 Payment for the tour by credit card with zero month period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsZero() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.monthAndYearAllZero);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("12 Payment for the tour by credit card with non-existen month period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsNonExisten() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.extraMonth);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("13 Payment for the tour by credit card with invalid month period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsInvalid() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.monthInvalid);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("14 Payment for the tour by credit card with long month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsLong() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardMonthField(longMonth);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("15 Payment for the tour by credit card with short year validity period")
    @Test
    void shouldBeErrorNotificationWhenYearPeriodIsShort() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardYearField(getOneNumber());
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("16 Payment for the tour by credit card with empty year validity period")
    @Test
    void shouldBeErrorNotificationWhenYearPeriodIsEmpty() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardYearField("");
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("17 Payment for the tour by credit card with zero year period")
    @Test
    void shouldBeErrorNotificationWhenYearPeriodIsZero() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardYearField(DataHelper.monthAndYearAllZero);
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationCardExpired();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("18 Payment for the tour by credit card with last year")
    @Test
    void shouldBeErrorNotificationWhenLastYear() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardYearField(DataHelper.getLastYear());
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationCardExpired();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("19 Payment for the tour by credit card with invalid year period")
    @Test
    void shouldBeErrorNotificationWhenYearPeriodIsInvalid() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardYearField(yearInvalid);
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("20 Payment for the tour by credit card with full year validity period")
    @Test
    void shouldBeErrorNotificationWhenFullPeriodIsLong() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardYearField(DataHelper.getFullYear());
        filloutOtherFieldsByValidDataForYearCheck();
        mainPaymentPage.shouldHaveErrorNotificationCardExpired();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("21 Payment for the tour by credit card with english letters in name of card holder")
    @Test
    void shouldBeErrorNotificationWrongFormatName() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderNameEn());
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("22 Payment for the tour by credit card with english letters in surname of card holder")
    @Test
    void shouldBeErrorNotificationWrongFormatSurname() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderSurnameEn());
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("23 Payment for the tour by credit card with russian letters in name of card holder")
    @Test
    void shouldBeErrorNotificationWrongFormatNameRu() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderNameRu());
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("24 Payment for the tour by credit card with russian letters in surname of card holder")
    @Test
    void shouldBeErrorNotificationWrongFormatSurnameRu() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderSurnameRu());
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("25 Payment for the tour by credit card empty field in name of card holder")
    @Test
    void shouldBeErrorNotificationRequiredField() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardHolderField(" ");
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationRequiredField();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("26 Payment for the tour by credit card with invalid card holder")
    @Test
    void shouldBeErrorNotificationInvalidFormat() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardHolderField(cardHolderInvalid);
        filloutOtherFieldsByValidDataForNameAndSurnameCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("27 Payment for the tour by credit card with one number of CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfCvcCvv1() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getOneNumber());
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("28 Payment for the tour by credit card with two numbers of CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfCvcCvv2() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getTwoNumbers());
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("29 Payment for the tour by credit card with empty CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfEmptyCvcCvv() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(" ");
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("30 Payment for the tour by credit card with all zero CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfAllZero() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(cvcCvvAllZero);
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("31 Payment for the tour by credit card with invalid CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidCvcCvv() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(cvcCvvInvalid);
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    @DisplayName("32 Payment for the tour by credit card with long CVC/CVV")
    @Test
    void shouldBeErrorNotificationInvalidFormatOfLongCvcCvv() {
        mainPaymentPage.openCreditPaymentPage();
        mainPaymentPage.filloutCardCvvCvcField(longCvcCvv);
        filloutOtherFieldsByValidDataForCvcCvvCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getCreditRequestStatus());
    }

    private void filloutOtherFieldsByValidDataForCardCheck() {
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }

    private void filloutOtherFieldsByValidDataForMonthCheck() {
        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }

    private void filloutOtherFieldsByValidDataForYearCheck() {
        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }

    private void filloutOtherFieldsByValidDataForNameAndSurnameCheck() {
        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }

    private void filloutOtherFieldsByValidDataForCvcCvvCheck() {
        mainPaymentPage.filloutCardNumberField(cardNumberDeclined);
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.clickContinueButton();
    }
}