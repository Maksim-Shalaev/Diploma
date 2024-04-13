package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPaymentPage {

    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement buyCreditButton = $(byText("Купить в кредит"));
    private final SelenideElement cardNumberField = $("[placeholder=`0000 0000 0000 0000`]");
    private final SelenideElement cardMonthField = $("[placeholder=`08`]");
    private final SelenideElement cardYearField = $("[placeholder=`22`]");
    private final SelenideElement cardHolderField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cardCvvCvcField = $(".input [placeholder=`999`]");
    private final SelenideElement continueButton = $(byText("Продолжить"));
    private final SelenideElement notification = $("div.notification_visible  div.notification__content");
    private final SelenideElement wrongFormat = $(byText("Неверный формат"));
    private final SelenideElement invalidCard = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement cardExpired = $(byText("Истёк срок действия карты"));
    private final SelenideElement requiredField = $(byText("Поле обязательно для заполнения"));

    public void openCardPaymentPage() {
        buyButton.click();
    }

    public void openCreditPaymentPage() {
        buyCreditButton.click();
    }

    public void filloutCardNumberField(String cardNumber) {
        cardNumberField.setValue(cardNumber);
    }

    public void filloutCardMonthField(String cardMonth) {
        cardMonthField.setValue(cardMonth);
    }

    public void filloutCardYearField(String cardYear) {
        cardYearField.setValue(cardYear);
    }

    public void filloutCardHolderField(String cardHolder) {
        cardHolderField.setValue(cardHolder);
    }

    public void filloutCardCvvCvcField(String cvvCvc) {
        cardCvvCvcField.setValue(cvvCvc);
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    public void shouldHaveSuccessNotification() {
        notification.shouldHave(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(15));
    }

    public void shouldHaveNoticeOfRefusal() {
        notification.shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(15));
    }

    public void shouldHaveErrorNotificationWrongFormat() {
        wrongFormat.shouldHave(Condition.visible);
    }

    public void shouldHaveErrorNotificationInvalidCard() {
        invalidCard.shouldHave(Condition.visible);
    }

    public void shouldHaveErrorNotificationCardExpired() {
        cardExpired.shouldHave(Condition.visible);
    }

    public void shouldHaveErrorNotificationRequiredField() {
        requiredField.shouldHave(Condition.visible);
    }
}









