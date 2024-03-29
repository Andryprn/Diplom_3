package site.nomoreparties.stellarburgers.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalProfilePage {
    private final By headerConstructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By stellarBurgerLogo = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']");

    private final By menuItemProfile = By.xpath(".//a[text()='Профиль']");
    private final By menuItemExitButton = By.xpath(".//button[text()='Выход']");
    private final WebDriver driver;

    public PersonalProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitingForProfilePageLoading() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(menuItemProfile));
    }

    public void menuItemExitButtonClick() {
        driver.findElement(menuItemExitButton).click();
    }

    public void stellarBurgerLogoClick() {
        driver.findElement(stellarBurgerLogo).click();
    }

    public void headerConstructorButtonClick() {
        driver.findElement(headerConstructorButton).click();
    }
}
