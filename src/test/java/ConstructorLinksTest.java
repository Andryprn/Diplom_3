import site.nomoreparties.stellarburgers.constants.Endpoints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pageObjects.MainPage;
import site.nomoreparties.stellarburgers.setupBrowser.SetUpWebDriver;

import static org.junit.Assert.assertTrue;

public class ConstructorLinksTest {
    public WebDriver driver;
    public MainPage mainPage;

    @Before
    public void setUp() {
        driver = SetUpWebDriver.setUpWDM();
        mainPage = new MainPage(driver);
        driver.get(Endpoints.BASE_URL);
    }

    @Test
    public void checkSaucesPass() {
        mainPage.clickOnSaucesMenu();
        assertTrue("Выбрана вкладка соусов", mainPage.saucesMenuIsSelected());
    }

    @Test
    public void checkFillingsPass() {
        mainPage.clickOnFillingsMenu();
        assertTrue("Выбрана вкладка начинок", mainPage.fillingsMenuIsSelected());
    }

    @Test
    public void checkBunsPass() {
        mainPage.clickOnFillingsMenu();
        mainPage.clickOnBunsMenu();
        assertTrue("Выбрана вкладка соусов", mainPage.bunsMenuIsSelected());
    }

    @After
    public void tearDown() {
        if(driver != null){
            driver.quit();
        }
    }
}