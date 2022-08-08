package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.*;

public class TransferMoneyTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        val dashboardPage = new DashboardPage();
        int value = 5000;
        String cardNumber = String.valueOf(DataHelper.getSecondCardNumber());
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        dashboardPage.transferFromFirstToSecond();
        val transferPage = new TransferPage();
        transferPage.importTransferData(value, cardNumber);
        var firstCardBalance2 = dashboardPage.getFirstCardBalance();
        var secondCardBalance2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalance1 + value, firstCardBalance2);
        Assertions.assertEquals(secondCardBalance1 - value, secondCardBalance2);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        val dashboardPage = new DashboardPage();
        int value = 5000;
        String cardNumber = String.valueOf(DataHelper.getFirstCardNumber());
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        dashboardPage.transferFromSecondToFirst();
        val transferPage = new TransferPage();
        transferPage.importTransferData(value, cardNumber);
        var firstCardBalance2 = dashboardPage.getFirstCardBalance();
        var secondCardBalance2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalance1 - value, firstCardBalance2);
        Assertions.assertEquals(secondCardBalance1 + value, secondCardBalance2);
    }

    @Test
    void shouldNotTransferMoneyFromFirstToSecond() {
        val dashboardPage = new DashboardPage();
        int value = 20000;
        String cardNumber = String.valueOf(DataHelper.getSecondCardNumber());
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        dashboardPage.transferFromFirstToSecond();
        val transferPage = new TransferPage();
        transferPage.importTransferData(value, cardNumber);
        var firstCardBalance2 = dashboardPage.getFirstCardBalance();
        var secondCardBalance2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalance1, firstCardBalance2);
        Assertions.assertEquals(secondCardBalance1, secondCardBalance2);
    }

    @Test
    void shouldNotTransferMoneyFromSecondToFirst() {
        val dashboardPage = new DashboardPage();
        int value = 20000;
        String cardNumber = String.valueOf(DataHelper.getFirstCardNumber());
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        dashboardPage.transferFromSecondToFirst();
        val transferPage = new TransferPage();
        transferPage.importTransferData(value, cardNumber);
        var firstCardBalance2 = dashboardPage.getFirstCardBalance();
        var secondCardBalance2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalance1, firstCardBalance2);
        Assertions.assertEquals(secondCardBalance1, secondCardBalance2);
    }

    @Test
    void shouldReloadDashboardPage() {
        val dashboardPage = new DashboardPage();
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        dashboardPage.reload();
        var firstCardBalance2 = dashboardPage.getFirstCardBalance();
        var secondCardBalance2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalance1, firstCardBalance2);
        Assertions.assertEquals(secondCardBalance1, secondCardBalance2);
    }
}
