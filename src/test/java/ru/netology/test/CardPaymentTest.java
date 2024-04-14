package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.clearTables;

public class CardPaymentTest {

    private MainPaymentPage mainPaymentPage;

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
        mainPaymentPage = new MainPaymentPage();
    }

    @AfterEach
    public void cleanTables() {
        clearTables();
    }

    @DisplayName("Payment for the tour by debit card, status APPROVED")
    @Test
    void shouldBeAllowedByApprovedDebitCard() {
        mainPaymentPage.openCardPaymentPage();
        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
        filloutOtherFieldsByValidData();
        mainPaymentPage.shouldHaveSuccessNotification();
        assertEquals("APPROVED", new SQLHelper().getPaymentStatus());

    }

//    @DisplayName("Payment for the tour by credit card, status APPROVED")
//    @Test
//    void shouldBeAllowedByApprovedCreditCard() {
//        mainPaymentPage.openCreditPaymentPage();
//        mainPaymentPage.filloutCardNumberField(cardNumberApproved);
//        filloutOtherFieldsByValidData();
//        mainPaymentPage.shouldHaveSuccessNotification();
//        assertEquals("APPROVED", new SQLHelper().getCreditRequestStatus());
//
//    }
//
//    @DisplayName("Payment for the tour by debit card, status DECLINED")
//    @Test
//    void shouldBeRefusedByDeclinedDebitCard() {
//        mainPaymentPage.openCardPaymentPage();
//        mainPaymentPage.filloutCardNumberField(cardNumberDeclaned);
//        filloutOtherFieldsByValidData();
//        mainPaymentPage.shouldHaveNoticeOfRefusal();
//        assertEquals("DECLINED", new SQLHelper().getPaymentStatus());
//    }
//
//    @DisplayName("Payment for the tour by credit card, status DECLINED")
//    @Test
//    void shouldBeDRefusedByDeclinedCreditCard() {
//        mainPaymentPage.openCreditPaymentPage();
//        mainPaymentPage.filloutCardNumberField(cardNumberDeclaned);
//        filloutOtherFieldsByValidData();
//        mainPaymentPage.shouldHaveNoticeOfRefusal();
//        assertEquals("DECLINED", new SQLHelper().getCreditRequestStatus());
//    }
//
//    @DisplayName("Payment for the tour by debit valid generated card number")
//    @Test
//    void shouldBeRefusedByValidCardNumber() {
//        mainPaymentPage.openCardPaymentPage();
//        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
//        filloutOtherFieldsByValidData();
//        mainPaymentPage.shouldHaveNoticeOfRefusal();
//        assertNull(new SQLHelper().getPaymentStatus());
//    }
//
//    @DisplayName("Payment for the tour by credit valid generated card number")
//    @Test
//    void shouldBeRefusedByValidCreditCardNumber() {
//        mainPaymentPage.openCreditPaymentPage();
//        mainPaymentPage.filloutCardNumberField(DataHelper.getCardNumberDigits16());
//        filloutOtherFieldsByValidData();
//        mainPaymentPage.shouldHaveNoticeOfRefusal();
//        assertNull(new SQLHelper().getCreditRequestStatus());
//    }


    private void filloutOtherFieldsByValidData() {
        mainPaymentPage.filloutCardMonthField(DataHelper.getCurrentMonth());
        mainPaymentPage.filloutCardYearField(DataHelper.getYear());
        mainPaymentPage.filloutCardHolderField(DataHelper.cardHolderFullNameEn());
        mainPaymentPage.filloutCardCvvCvcField(DataHelper.getCvcCvv());
        mainPaymentPage.clickContinueButton();
    }


}
