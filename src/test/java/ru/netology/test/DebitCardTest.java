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
import static ru.netology.data.DataHelper.cardNumberApproved;
import static ru.netology.data.DataHelper.cardNumberDeclined;
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

    @DisplayName("1.1 Payment for the tour by debit card, status APPROVED")
    @Test
    void shouldBeAllowedByApprovedDebitCard() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveSuccessNotification();
        assertEquals("APPROVED", new SQLHelper().getPaymentStatus());
    }

    @DisplayName("1.2 Payment for the tour by debit card, status DECLINED")
    @Test
    void shouldBeRefusedByDeclinedDebitCard() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(cardNumberDeclined);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertEquals("DECLINED", new SQLHelper().getPaymentStatus());
    }

    @DisplayName("1.3 Payment for the tour by debit valid generated card number")
    @Test
    void shouldBeRefusedByValidCardNumber() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("1.4 Payment for the tour by debit card with short number")
    @Test
    void shouldBeErrorNotificationForShortCardNumber() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits15());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("1.5 Payment for the tour by debit card with long number")
    @Test
    void shouldBeErrorNotificationForLongCardNumber() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits17());
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("1.6 Payment for the tour by debit card with an empty input field")
    @Test
    void shouldBeErrorNotificationWhenCardFieldIsEmpty() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField("");
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("1.7 Payment for the tour by debit card with all zero in input field")
    @Test
    void shouldBeErrorNotificationWhenAllZero() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.cardNumberAllZero);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveNoticeOfRefusal();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("1.8 Payment for the tour by debit card with invalid number")
    @Test
    void shouldBeErrorNotificationWhenInvalidCardNumber() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(DataHelper.cardNumberInvalid);
        filloutOtherFieldsByValidDataForCardCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("1.9 Payment for the tour by debit card with short month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsShort() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.getOneNumber());
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("2.0 Payment for the tour by debit card with empty month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsEmpty() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField("");
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("2.1 Payment for the tour by debit card with empty zero period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsZero() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.monthAndYearAllZero);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("2.2 Payment for the tour by debit card with non-existen period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsNonExisten() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.extraMonth);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("2.3 Payment for the tour by debit card with invalid period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsInvalid() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.monthInvalid);
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationWrongFormat();
        assertNull(new SQLHelper().getPaymentStatus());
    }

    @DisplayName("2.4 Payment for the tour by debit card with long month validity period")
    @Test
    void shouldBeErrorNotificationWhenMonthPeriodIsLongrt() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardMonthField(DataHelper.getTwoNumbers());
        filloutOtherFieldsByValidDataForMonthCheck();
        mainPaymentPage.shouldHaveErrorNotificationInvalidCard();
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


}
