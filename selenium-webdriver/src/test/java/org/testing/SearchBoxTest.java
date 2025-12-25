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
import java.util.List;

public class SearchBoxTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseURL = "https://naveenautomationlabs.com/opencart/";

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().to(baseURL);
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
        System.out.println("\n--- TestCase1: Khong nhap gi, nhan Enter ---");
        performSearch("");
        
        try {
            Thread.sleep(2000);
            
            List<WebElement> products = driver.findElements(By.className("product-layout"));
            System.out.println("So san pham tim thay: " + products.size());
            
            Assert.assertTrue("Ket qua phai liet ke tat ca san pham (> 0)", products.size() > 0);
            System.out.println("Ket qua: PASS (Liet ke tat ca san pham)");
        } catch (Exception e) {
            System.out.println("Ket qua: FAIL - " + e.getMessage());
            Assert.fail("TestCase1 failed: " + e.getMessage());
        }
    }

    @Test
    public void TestCase2() {
        System.out.println("\n--- TestCase2: Nhap 'May bay', nhan Enter ---");
        performSearch("May bay");
        
        try {
            Thread.sleep(2000);
            
            // Kiem tra the <p> chua thong bao "There is no product that matches the search criteria."
            WebElement noProductMessage = driver.findElement(By.xpath("//p[contains(text(), 'There is no product that matches the search criteria.')]"));
            String messageText = noProductMessage.getText();
            System.out.println("Thong bao: " + messageText);
            
            Assert.assertEquals("Ket qua phai la khong tim thay san pham phu hop", 
                    "There is no product that matches the search criteria.", messageText);
            System.out.println("Ket qua: PASS (Hien thi thong bao khong tim thay san pham)");
        } catch (Exception e) {
            System.out.println("Ket qua: FAIL - " + e.getMessage());
            Assert.fail("TestCase2 failed: " + e.getMessage());
        }
    }

    @Test
    public void TestCase3() {
        System.out.println("\n--- TestCase3: Nhap 'Iphone', nhan Enter ---");
        performSearch("Iphone");
        
        try {
            Thread.sleep(2000);
            
            List<WebElement> products = driver.findElements(By.className("product-layout"));
            System.out.println("So san pham tim thay: " + products.size());
            
            Assert.assertTrue("Ket qua phai tim thay san pham", products.size() > 0);
            
            WebElement firstProductName = products.get(0).findElement(By.tagName("h4"));
            String productName = firstProductName.getText();
            System.out.println("Ten san pham tim thay: " + productName);
            
            Assert.assertTrue("Ten san pham phai chua 'iPhone'", productName.toLowerCase().contains("iphone"));
            System.out.println("Ket qua: PASS (Tim thay san pham 'iPhone')");
        } catch (Exception e) {
            System.out.println("Ket qua: FAIL - " + e.getMessage());
            Assert.fail("TestCase3 failed: " + e.getMessage());
        }
    }

    private void performSearch(String searchTerm) {
        try {
            WebElement searchInput = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='search']")));
            searchInput.clear();
            searchInput.sendKeys(searchTerm);
            System.out.println("Nhap tu khoa: '" + searchTerm + "'");
            
            searchInput.sendKeys(Keys.ENTER);
            System.out.println("Nhan Enter");
        } catch (Exception e) {
            System.out.println("Loi khi tim kiem: " + e.getMessage());
        }
    }
}
