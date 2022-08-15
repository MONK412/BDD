package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement ammount = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement replenishButton = $("[data-test-id='action-transfer']");

    public void importTransferData(int value, String cardNumber) {
        ammount.setValue(Integer.toString(value));
        from.setValue(String.valueOf(cardNumber));
        replenishButton.click();
    }

    public void clearTransferData() {
        ammount.sendKeys(Keys.CONTROL + "A");
        ammount.sendKeys(Keys.BACK_SPACE);
        from.sendKeys(Keys.CONTROL + "A");
        from.sendKeys(Keys.BACK_SPACE);
    }
}
