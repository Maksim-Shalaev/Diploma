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


//    @DisplayName("1.1 Payment for the tour by credit card, status APPROVED")
//    @Test
//    void shouldBeAllowedByApprovedCreditCard() {
//        mainPaymentPage.openCreditPaymentPage();
//        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
//        filloutOtherFieldsByValidData();
//        mainPaymentPage.shouldHaveSuccessNotification();
//        assertEquals("APPROVED", new SQLHelper().getCreditRequestStatus());
//    }

//    @DisplayName("1.2 Payment for the tour by credit card, status DECLINED")
//    @Test
//    void shouldBeDRefusedByDeclinedCreditCard() {
//        mainPaymentPage.openCreditPaymentPage();
//        mainPaymentPage.filloutCardNumberField(cardNumberDeclined);
//        filloutOtherFieldsByValidData();
//        mainPaymentPage.shouldHaveNoticeOfRefusal();
//        assertEquals("DECLINED", new SQLHelper().getCreditRequestStatus());
//    }
//
//    @DisplayName("1.3 Payment for the tour by credit valid generated card number")
//    @Test
//    void shouldBeRefusedByValidCreditCardNumber() {
//        mainPaymentPage.openCreditPaymentPage();
//        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
//        filloutOtherFieldsByValidData();
//        mainPaymentPage.shouldHaveNoticeOfRefusal();
//        assertNull(new SQLHelper().getCreditRequestStatus());
//    }

//    private void filloutOtherFieldsByValidData() {
//        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
//        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
//        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
//        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
//        mainPaymentPage.clickContinueButton();
//    }
}