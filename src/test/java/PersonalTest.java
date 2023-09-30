import site.nomoreparties.stellarburgers.constants.Endpoints;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.model.UserRandomizer;
import site.nomoreparties.stellarburgers.model.UserSteps;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import site.nomoreparties.stellarburgers.pageObjects.LoginPage;
import site.nomoreparties.stellarburgers.pageObjects.MainPage;
import site.nomoreparties.stellarburgers.pageObjects.PersonalProfilePage;
import site.nomoreparties.stellarburgers.setupBrowser.SetUpWebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PersonalTest {

    public static String accessToken, refreshToken;
    public static User user;
    public WebDriver driver;
    public PersonalProfilePage personalProfilePage;
    public MainPage mainPage;
    public LoginPage loginPage;

    @Before
    public void setUp() {
        driver = SetUpWebDriver.setUpWDM();
        mainPage = new MainPage(driver);
        personalProfilePage = new PersonalProfilePage(driver);
        loginPage = new LoginPage(driver);
        driver.get(Endpoints.BASE_URL);
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
    }
    @After
    public void tearDown() {
//        System.out.println(driver.manage().getCookies());
        if(driver != null){
            driver.quit();
        }
    }
    @BeforeClass
    public static void beforeClass() {
        RestAssured.baseURI = Endpoints.BASE_URL;
        user = UserRandomizer.createNewRandomUser();
        Response response = UserSteps.createUser(user);
        accessToken = response.path("accessToken");
        refreshToken = response.path("refreshToken");
    }
    @AfterClass
    public static void afterClass() {
        UserSteps.deleteUser(accessToken);
    }


    @Test
    @DisplayName("Переход в личный кабинет пользователя с главной страницы")
    public void checkFromMainToPersonalPassage() {
        mainPage.waitingForMainPageLoading();
        mainPage.clickOnProfileEnterButton();
        personalProfilePage.waitingForProfilePageLoading();
        assertEquals("Перешли в личный кабинет после нажатия кнопки 'Личный Кабинет' на главной странице", Endpoints.BASE_URL + Endpoints.PROFILE, driver.getCurrentUrl());
    }



    @Test
    @DisplayName("Проверка перехода по клику на логотип Stellar Burgers")
    public void checkTransitionToMainPageAfterUserLoggedIn() {
        driver.get(Endpoints.BASE_URL + "/account");
        personalProfilePage.waitingForProfilePageLoading();
        personalProfilePage.stellarBurgerLogoClick();
        mainPage.waitingForMainPageLoading();
        assertEquals("Перешли на главную страницу после авторизации", Endpoints.BASE_URL + "/", driver.getCurrentUrl());
    }


    @Test
    @DisplayName("Проверка перехода по клику на кнопку «Конструктор»")
    public void checkTransitionFromProfileByClickOnLogoButton() {
        driver.get(Endpoints.BASE_URL + "/account");
        personalProfilePage.waitingForProfilePageLoading();
        personalProfilePage.headerConstructorButtonClick();
        mainPage.waitingForMainPageLoading();
        assertEquals("Перешли на главную страницу после нажатия на логотип", Endpoints.BASE_URL + "/", driver.getCurrentUrl());
    }


    @Test
    @DisplayName("Проверка выхода по кнопке «Выйти» в личном кабинете")
    public void checkProfileExitButton() {
        driver.get(Endpoints.BASE_URL + "/account");
        personalProfilePage.waitingForProfilePageLoading();
        personalProfilePage.menuItemExitButtonClick();
        loginPage.waitingForLoginFormLoading();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        assertNull("Токен пользователя пустой", localStorage.getItem("accessToken"));
        assertEquals("Зашли на страницу логина после выхода", Endpoints.BASE_URL + Endpoints.LOGIN, driver.getCurrentUrl());
    }

}