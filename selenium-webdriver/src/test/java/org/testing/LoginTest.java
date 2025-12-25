package org.testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

// ==================== MODEL CLASS: LoginCredentials ====================
class LoginCredentials {
    private String email;
    private String password;

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginCredentials{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

// ==================== TEST DATA CLASS ====================
class LoginTestData {
    // Mang du lieu test
    public static LoginCredentials[] getLoginTestData() {
        return new LoginCredentials[] {
                // TC1: Khong co email va mat khau
                new LoginCredentials("", ""),
                // TC2: Khong nhap email nhung nhap password '111111'
                new LoginCredentials("", "111111"),
                // TC3: nhap email (kkk123kkk@gmail.com) va de trong password
                new LoginCredentials("kkk123kkk@gmail.com", "")
        };
    }
}

// ==================== MAIN TEST CLASS ====================
public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String loginURL = "https://naveenautomationlabs.com/opencart/index.php?route=account/login";

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().to(loginURL);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void TestCase1() {
        LoginCredentials[] testDataArray = LoginTestData.getLoginTestData();

        for (int i = 0; i < testDataArray.length; i++) {
            LoginCredentials credentials = testDataArray[i];
            System.out.println("\n--- TC1: Khong co email va mat khau ---");
            System.out.println("Du lieu test: " + credentials);
            performLogin(credentials.getEmail(), credentials.getPassword());

            // Kiem tra thong bao validation: Warning: Please enter all information
            // completely.
            try {
                WebElement alert = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
                String alertText = alert.getText();
                System.out.println("Noi dung alert: " + alertText);
                String expectedMessage = "Warning: Please enter all information completely.";
                Assert.assertTrue("Alert phai chua thong bao", alertText.contains(expectedMessage));
                System.out.println("Ket qua: PASS (alert chua: Warning: Please enter all information completely.)");
            } catch (Exception e) {
                System.out.println("Ket qua: FAIL - alert khong xuat hien hoac khac noi dung: " + e.getMessage());
                Assert.fail("Alert khong tim thay hoac noi dung khong trung khop cho TC1: " + e.getMessage());
            }
        }
    }

    @Test
    public void TestCase2() {
        LoginCredentials[] testDataArray = new LoginCredentials[] { new LoginCredentials("", "111111") };

        for (int i = 0; i < testDataArray.length; i++) {
            LoginCredentials credentials = testDataArray[i];
            System.out.println("\n--- TC2: Khong nhap email, nhap password '111111' ---");
            System.out.println("Du lieu test: " + credentials);
            performLogin(credentials.getEmail(), credentials.getPassword());

            // Kiem tra thong bao validation: Warning: Please enter all information
            // completely.
            try {
                WebElement alert = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
                String alertText = alert.getText();
                System.out.println("Noi dung alert: " + alertText);
                String expectedMessage = "Warning: Please enter all information completely.";
                Assert.assertTrue("Alert phai chua thong bao", alertText.contains(expectedMessage));
                System.out.println("Ket qua: PASS (alert chua: Warning: Please enter all information completely.)");
            } catch (Exception e) {
                System.out.println("Ket qua: FAIL - alert khong xuat hien hoac khac noi dung: " + e.getMessage());
                Assert.fail("Alert khong tim thay hoac noi dung khong trung khop cho TC2: " + e.getMessage());
            }
        }
    }

    @Test
    public void TestCase3() {
        LoginCredentials[] testDataArray = new LoginCredentials[] { new LoginCredentials("kkk123kkk@gmail.com", "") };

        for (int i = 0; i < testDataArray.length; i++) {
            LoginCredentials credentials = testDataArray[i];
            System.out.println("\n--- TC3: Nhap email, de trong password ---");
            System.out.println("Du lieu test: " + credentials);
            performLogin(credentials.getEmail(), credentials.getPassword());

            try {
                WebElement alert = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
                String alertText = alert.getText();
                System.out.println("Noi dung alert: " + alertText);
                String expectedMessage = "Warning: Please enter all information completely.";
                Assert.assertTrue("Alert phai chua thong bao", alertText.contains(expectedMessage));
                System.out.println("Ket qua: PASS (alert chua: Warning: Please enter all information completely.)");
            } catch (Exception e) {
                System.out.println("Ket qua: FAIL - alert khong xuat hien hoac khac noi dung: " + e.getMessage());
                Assert.fail("Alert khong tim thay hoac noi dung khong trung khop cho TC3: " + e.getMessage());
            }
        }
    }

    @Test
    public void TestCase4() {
        System.out.println("\n--- TC4: 2@gmail.com / 121212 ---");
        performLogin("2@gmail.com", "121212");
        try {
            WebElement alert = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
            String alertText = alert.getText();
            System.out.println("Noi dung alert: " + alertText);
            String expected = "Warning: No match for E-Mail Address and/or Password.";
            Assert.assertTrue("TC4 phai hien thi canh bao khong khop", alertText.contains(expected));
            System.out.println("Ket qua: PASS (TC4 hien thi canh bao khong khop)");
        } catch (Exception e) {
            System.out.println("Ket qua: FAIL (TC4) - " + e.getMessage());
            Assert.fail("Alert khong tim thay hoac noi dung khong trung khop cho TC4: " + e.getMessage());
        }
    }

    @Test
    public void TestCase5() {
        System.out.println("\n--- TC5: 2@gmail.com / 111111 ---");
        performLogin("2@gmail.com", "111111");
        try {
            WebElement alert = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
            String alertText = alert.getText();
            System.out.println("Noi dung alert: " + alertText);
            String expected = "Warning: No match for E-Mail Address and/or Password.";
            Assert.assertTrue("TC5 phai hien thi canh bao khong khop", alertText.contains(expected));
            System.out.println("Ket qua: PASS (TC5 hien thi canh bao khong khop)");
        } catch (Exception e) {
            System.out.println("Ket qua: FAIL (TC5) - " + e.getMessage());
            Assert.fail("Alert khong tim thay hoac noi dung khong trung khop cho TC5: " + e.getMessage());
        }
    }

    @Test
    public void TestCase6() {
        System.out.println("\n--- TC6: kkk123kkk@gmail.com / 121212 ---");
        performLogin("kkk123kkk@gmail.com", "121212");
        try {
            WebElement alert = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
            String alertText = alert.getText();
            System.out.println("Noi dung alert: " + alertText);
            String expected = "Warning: No match for E-Mail Address and/or Password.";
            Assert.assertTrue("TC6 phai hien thi canh bao khong khop", alertText.contains(expected));
            System.out.println("Ket qua: PASS (TC6 hien thi canh bao khong khop)");
        } catch (Exception e) {
            System.out.println("Ket qua: FAIL (TC6) - " + e.getMessage());
            Assert.fail("Alert khong tim thay hoac noi dung khong trung khop cho TC6: " + e.getMessage());
        }
    }

    @Test
    public void TestCase7() {
        System.out.println("\n--- TC7: kkk123kkk@gmail.com / 111111 ---");
        performLogin("kkk123kkk@gmail.com", "111111");
        try {
            // doi chuyen trang toi account page
            wait.until(ExpectedConditions.urlContains("route=account/account"));
            String current = driver.getCurrentUrl();
            System.out.println("Current URL: " + current);
            Assert.assertTrue("TC7 phai chuyen huong toi account page", current.contains("route=account/account"));
            System.out.println("Ket qua: PASS (TC7 chuyen huong toi account page)");
        } catch (Exception e) {
            System.out.println("Ket qua: FAIL (TC7) - " + e.getMessage());
            Assert.fail("Khong chuyen huong toi account page cho TC7: " + e.getMessage());
        }
    }

    // Method helper de thuc hien login
    private void performLogin(String email, String password) {
        try {
            // Nhap email
            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-email")));
            emailInput.clear();
            emailInput.sendKeys(email);

            // Nhap password
            WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-password")));
            passwordInput.clear();
            passwordInput.sendKeys(password);

            // Gui phim ENTER thay vi click nut Login (nut khong co id)
            passwordInput.sendKeys(Keys.ENTER);

            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}
