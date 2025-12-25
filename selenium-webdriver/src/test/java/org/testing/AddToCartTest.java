package org.testing;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddToCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "https://naveenautomationlabs.com/opencart/index.php?route=common/home";

    // Login
    private static final String EMAIL = "qwerty@gmail.com";
    private static final String PASSWORD = "Qwerty@123";

    // Locators
    private static final By MY_ACCOUNT = By.xpath("//span[normalize-space()='My Account']");
    private static final By LOGIN_LINK = By.linkText("Login");
    private static final By EMAIL_INPUT = By.id("input-email");
    private static final By PASSWORD_INPUT = By.id("input-password");
    private static final By LOGIN_BUTTON = By.xpath("//input[@value='Login']");
    private static final By LOGOUT_LINK = By.linkText("Logout");

    private static final By SEARCH_INPUT = By.name("search");
    private static final By SEARCH_BUTTON = By.cssSelector("button.btn-default.btn-lg");

    private static final By PRODUCT_MACBOOK = By.linkText("MacBook");
    private static final By QUANTITY_INPUT = By.id("input-quantity");
    private static final By ADD_TO_CART_BUTTON = By.id("button-cart");

    private static final By HTML_ALERT = By.cssSelector("div.alert");

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    // ===== COMMON =====

    private void login() {
        wait.until(ExpectedConditions.elementToBeClickable(MY_ACCOUNT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_LINK)).click();
        System.out.println("Dang nhap voi tai khoan " + EMAIL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT)).sendKeys(EMAIL);
        driver.findElement(PASSWORD_INPUT).sendKeys(PASSWORD);
        driver.findElement(LOGIN_BUTTON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_LINK));
        System.out.println("Thong bao dang nhap thanh cong");
        driver.get(BASE_URL);
    }

    private void openMacBook() {
        System.out.println("--- Mo trang san pham MacBook ---");
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        search.clear();
        System.out.println("Nhap 'MacBook' vao o tim kiem");
        search.sendKeys("MacBook");
        driver.findElement(SEARCH_BUTTON).click();
        System.out.println("Click vao san pham MacBook tu ket qua");
        wait.until(ExpectedConditions.elementToBeClickable(PRODUCT_MACBOOK)).click();
    }

    private String addToCartAndGetAlert(int quantity) {
        WebElement qty = wait.until(ExpectedConditions.visibilityOfElementLocated(QUANTITY_INPUT));
        qty.clear();
        System.out.println("Nhap so luong: " + quantity);
        qty.sendKeys(String.valueOf(quantity));
        System.out.println("Click nut them vao gio hang");
        driver.findElement(ADD_TO_CART_BUTTON).click();

        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(HTML_ALERT));
        String alertText = alert.getText().toLowerCase();
        System.out.println("Thong bao hien thi: " + alertText);
        return alertText;
    }

    // =================== TEST CASES ===================

    @Test
    public void TC01_NotLogin_InvalidQuantity() {
        System.out.println("--- Chay TC01: Khong dang nhap - So luong khong hop le ---");
        openMacBook();
        String alert = addToCartAndGetAlert(0);
        Assert.assertTrue("Expected login alert",
                alert.contains("login") || alert.contains("account"));
    }

    @Test
    public void TC02_NotLogin_ValidQuantity() {
        System.out.println("--- Chay TC02: Khong dang nhap - So luong hop le ---");
        openMacBook();
        String alert = addToCartAndGetAlert(1);
        Assert.assertTrue("Expected login alert",
                alert.contains("login") || alert.contains("account"));
    }

    @Test
    public void TC05_Login_InvalidQuantity() {
        System.out.println("--- Chay TC05: Da dang nhap - So luong khong hop le ---");
        login();
        openMacBook();
        String alert = addToCartAndGetAlert(0);
        Assert.assertTrue("Expected quantity error",
                alert.contains("minimum") || alert.contains("quantity"));
    }

    @Test
    public void TC07_Login_QuantityExceedStock() {
        System.out.println("--- Chay TC07: Da dang nhap - So luong vuot qua ton kho ---");
        login();
        openMacBook();
        String alert = addToCartAndGetAlert(999);
        Assert.assertTrue("Expected stock error",
                alert.contains("stock") || alert.contains("not available"));
    }

    @Test
    public void TC08_Login_ValidQuantity() {
        System.out.println("--- Chay TC08: Da dang nhap - So luong hop le ---");
        login();
        openMacBook();
        String alert = addToCartAndGetAlert(1);
        Assert.assertTrue("Expected success message",
                alert.contains("success"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}