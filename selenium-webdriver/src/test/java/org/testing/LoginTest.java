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
    // Mảng dữ liệu test
    public static LoginCredentials[] getLoginTestData() {
        return new LoginCredentials[] {
                // TC1: Không có email và mật khẩu
                new LoginCredentials("", ""),
                // TC2: Không nhập email nhưng nhập password '111111'
                new LoginCredentials("", "111111"),
                // TC3: nhập email (kkk123kkk@gmail.com) và để trống password
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
            System.out.println("\n--- TC1: Không có email và mật khẩu ---");
            System.out.println("Dữ liệu test: " + credentials);
            performLogin(credentials.getEmail(), credentials.getPassword());

            // Kiểm tra thông báo validation: Warning: Please enter all information
            // completely.
            try {
                WebElement alert = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
                String alertText = alert.getText();
                System.out.println("Alert text: " + alertText);
                String expectedMessage = "Warning: Please enter all information completely.";
                Assert.assertTrue("Alert should contain expected message", alertText.contains(expectedMessage));
                System.out.println("Kết quả: PASS (alert chứa: Warning: Please enter all information completely.)");
            } catch (Exception e) {
                System.out.println("Kết quả: FAIL - alert không xuất hiện hoặc khác nội dung: " + e.getMessage());
                Assert.fail("Alert not found or text mismatch for TC1: " + e.getMessage());
            }
        }
    }

    @Test
    public void TestCase2() {
        LoginCredentials[] testDataArray = new LoginCredentials[] { new LoginCredentials("", "111111") };

        for (int i = 0; i < testDataArray.length; i++) {
            LoginCredentials credentials = testDataArray[i];
            System.out.println("\n--- TC2: Không nhập email, nhập password '111111' ---");
            System.out.println("Dữ liệu test: " + credentials);
            performLogin(credentials.getEmail(), credentials.getPassword());

            // Kiểm tra thông báo validation: Warning: Please enter all information
            // completely.
            try {
                WebElement alert = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
                String alertText = alert.getText();
                System.out.println("Alert text: " + alertText);
                String expectedMessage = "Warning: Please enter all information completely.";
                Assert.assertTrue("Alert should contain expected message", alertText.contains(expectedMessage));
                System.out.println("Kết quả: PASS (alert chứa: Warning: Please enter all information completely.)");
            } catch (Exception e) {
                System.out.println("Kết quả: FAIL - alert không xuất hiện hoặc khác nội dung: " + e.getMessage());
                Assert.fail("Alert not found or text mismatch for TC2: " + e.getMessage());
            }
        }
    }

    @Test
    public void TestCase3() {
        LoginCredentials[] testDataArray = new LoginCredentials[] { new LoginCredentials("kkk123kkk@gmail.com", "") };

        for (int i = 0; i < testDataArray.length; i++) {
            LoginCredentials credentials = testDataArray[i];
            System.out.println("\n--- TC3: Nhập email, để trống password ---");
            System.out.println("Dữ liệu test: " + credentials);
            performLogin(credentials.getEmail(), credentials.getPassword());

            // Kiểm tra thông báo validation: Warning: Please enter all information
            // completely.
            try {
                WebElement alert = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
                String alertText = alert.getText();
                System.out.println("Alert text: " + alertText);
                String expectedMessage = "Warning: Please enter all information completely.";
                Assert.assertTrue("Alert should contain expected message", alertText.contains(expectedMessage));
                System.out.println("Kết quả: PASS (alert chứa: Warning: Please enter all information completely.)");
            } catch (Exception e) {
                System.out.println("Kết quả: FAIL - alert không xuất hiện hoặc khác nội dung: " + e.getMessage());
                Assert.fail("Alert not found or text mismatch for TC3: " + e.getMessage());
            }
        }
    }

    @Test
    public void TestCase4() {
        System.out.println("\n--- TC4: 1@gmail.com / 121212 ---");
        performLogin("1@gmail.com", "121212");
        try {
            WebElement alert = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText);
            String expected = "Warning: No match for E-Mail Address and/or Password.";
            Assert.assertTrue("TC4 should show No match warning", alertText.contains(expected));
            System.out.println("Kết quả: PASS (TC4 shows No match warning)");
        } catch (Exception e) {
            System.out.println("Kết quả: FAIL (TC4) - " + e.getMessage());
            Assert.fail("Alert not found or text mismatch for TC4: " + e.getMessage());
        }
    }

    @Test
    public void TestCase5() {
        System.out.println("\n--- TC5: 1@gmail.com / 111111 ---");
        performLogin("1@gmail.com", "111111");
        try {
            WebElement alert = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger")));
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText);
            String expected = "Warning: No match for E-Mail Address and/or Password.";
            Assert.assertTrue("TC5 should show No match warning", alertText.contains(expected));
            System.out.println("Kết quả: PASS (TC5 shows No match warning)");
        } catch (Exception e) {
            System.out.println("Kết quả: FAIL (TC5) - " + e.getMessage());
            Assert.fail("Alert not found or text mismatch for TC5: " + e.getMessage());
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
            System.out.println("Alert text: " + alertText);
            String expected = "Warning: No match for E-Mail Address and/or Password.";
            Assert.assertTrue("TC6 should show No match warning", alertText.contains(expected));
            System.out.println("Kết quả: PASS (TC6 shows No match warning)");
        } catch (Exception e) {
            System.out.println("Kết quả: FAIL (TC6) - " + e.getMessage());
            Assert.fail("Alert not found or text mismatch for TC6: " + e.getMessage());
        }
    }

    @Test
    public void TestCase7() {
        System.out.println("\n--- TC7: kkk123kkk@gmail.com / 111111 ---");
        performLogin("kkk123kkk@gmail.com", "111111");
        try {
            // đợi chuyển trang tới account page
            wait.until(ExpectedConditions.urlContains("route=account/account"));
            String current = driver.getCurrentUrl();
            System.out.println("Current URL: " + current);
            Assert.assertTrue("TC7 should redirect to account page", current.contains("route=account/account"));
            System.out.println("Kết quả: PASS (TC7 redirected to account page)");
        } catch (Exception e) {
            System.out.println("Kết quả: FAIL (TC7) - " + e.getMessage());
            Assert.fail("Did not redirect to account page for TC7: " + e.getMessage());
        }
    }

    // Method helper để thực hiện login
    private void performLogin(String email, String password) {
        try {
            // Nhập email
            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-email")));
            emailInput.clear();
            emailInput.sendKeys(email);

            // Nhập password
            WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-password")));
            passwordInput.clear();
            passwordInput.sendKeys(password);

            // Gửi phím ENTER thay vì click nút Login (nút không có id)
            passwordInput.sendKeys(Keys.ENTER);

            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
