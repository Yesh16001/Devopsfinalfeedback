package com.selenium.test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.nio.file.Path;

public class FormTest {

    private WebDriver driver;
    private int passCount = 0;
    private int failCount = 0;

    private static final String URL = "file:///" + resolveHtmlPath().replace("\\", "/");

    public static void main(String[] args) {
        FormTest testSuite = new FormTest();
        testSuite.runAllTests();
    }

    public void runAllTests() {
        try {
            setUp();
            testPageTitle();
            testEmptyName();
            testEmptyEmail();
            testShortPassword();
            testPasswordMismatch();
            testGenderRadio();
            testCourseDropdown();
            testValidSubmission();
            testResetButton();
            testInvalidMobile();
            testShortFeedback();
            testValidFeedbackSubmission();
        } finally {
            tearDown();
            printResults();
        }
    }

    private void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    private void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void loadPage() {
        driver.get(URL);
    }

    private void printResults() {
        System.out.printf("%nFinal Report:%nPassed: %d | Failed: %d%n", passCount, failCount);
    }

    private static String resolveHtmlPath() {
        Path current = Path.of(System.getProperty("user.dir")).toAbsolutePath();
        for (int i = 0; i < 8 && current != null; i++) {
            Path candidate = current.resolve("web").resolve("index.html");
            if (candidate.toFile().exists()) {
                return candidate.toString();
            }
            current = current.getParent();
        }
        throw new IllegalStateException("Cannot find web/index.html");
    }

    private String getAlertTextAndAccept() {
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        return text;
    }

    private void fillInput(String id, String value) {
        WebElement input = driver.findElement(By.id(id));
        input.clear();
        input.sendKeys(value);
    }

    private void submitForm() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    private void assertTest(String name, boolean condition, String actual) {
        if (condition) {
            System.out.println(name + ": PASS ✔");
            passCount++;
        } else {
            System.out.println(name + ": FAIL ✘ [Actual: " + actual + "]");
            failCount++;
        }
    }

    private void testPageTitle() {
        loadPage();
        assertTest("TC01 Title", "Admission Form".equals(driver.getTitle()), driver.getTitle());
    }

    private void testEmptyName() {
        loadPage();
        fillInput("email", "test@test.com");
        submitForm();
        String msg = getAlertTextAndAccept();
        assertTest("TC02 Empty Name", "Name cannot be empty".equals(msg), msg);
    }

    private void testEmptyEmail() {
        loadPage();
        fillInput("name", "Tanmay");
        submitForm();
        String msg = getAlertTextAndAccept();
        assertTest("TC03 Empty Email", "Enter email".equals(msg), msg);
    }

    private void testShortPassword() {
        loadPage();
        fillInput("name", "Tanmay");
        fillInput("email", "t@t.com");
        fillInput("password", "123");
        fillInput("confirm", "123");
        submitForm();
        String msg = getAlertTextAndAccept();
        assertTest("TC04 Short Password", "Password must be at least 6 characters".equals(msg), msg);
    }

    private void testPasswordMismatch() {
        loadPage();
        fillInput("name", "Tanmay");
        fillInput("email", "t@t.com");
        fillInput("password", "pass123");
        fillInput("confirm", "diff123");
        submitForm();
        String msg = getAlertTextAndAccept();
        assertTest("TC05 Pwd Mismatch", "Passwords do not match".equals(msg), msg);
    }

    private void testGenderRadio() {
        loadPage();
        WebElement male = driver.findElement(By.id("male"));
        WebElement female = driver.findElement(By.id("female"));
        male.click();
        boolean ok = male.isSelected() && !female.isSelected();
        female.click();
        ok = ok && female.isSelected() && !male.isSelected();
        assertTest("TC06 Gender Radio", ok, "Radio selection error");
    }

    private void testCourseDropdown() {
        loadPage();
        Select course = new Select(driver.findElement(By.id("course")));
        course.selectByVisibleText("BCA");
        boolean ok = "BCA".equals(course.getFirstSelectedOption().getText());
        assertTest("TC07 Dropdown", ok, "Dropdown selection error");
    }

    private void testValidSubmission() {
        loadPage();
        fillInput("name", "Tanmay Kalinkar");
        fillInput("email", "tanmay@gmail.com");
        fillInput("mobile", "9876543210");
        fillInput("password", "password123");
        fillInput("confirm", "password123");
        fillInput("feedback", "This admission process is very smooth and the portal works really well.");
        driver.findElement(By.id("male")).click();
        new Select(driver.findElement(By.id("course"))).selectByVisibleText("B.Tech");
        submitForm();
        String msg = getAlertTextAndAccept();
        assertTest("TC08 Success", "Registration Successful".equals(msg), msg);
    }

    private void testResetButton() {
        loadPage();
        fillInput("name", "Temp Data");
        driver.findElement(By.id("resetBtn")).click();
        String nameValue = driver.findElement(By.id("name")).getAttribute("value");
        assertTest("TC09 Reset Button", "".equals(nameValue), "Field not cleared");
    }

    private void testInvalidMobile() {
        loadPage();
        fillInput("name", "Tanmay");
        fillInput("email", "t@t.com");
        fillInput("mobile", "ABC123");
        fillInput("password", "pass123");
        fillInput("confirm", "pass123");
        fillInput("feedback", "This feedback has enough words to pass the minimum length check.");
        submitForm();
        String msg = getAlertTextAndAccept();
        assertTest("TC10 Invalid Mobile", "Invalid mobile number".equals(msg), msg);
    }

    private void testShortFeedback() {
        loadPage();
        fillInput("name", "Tanmay");
        fillInput("email", "t@t.com");
        fillInput("mobile", "9876543210");
        fillInput("password", "pass123");
        fillInput("confirm", "pass123");
        fillInput("feedback", "Too short feedback text only now");
        submitForm();
        String msg = getAlertTextAndAccept();
        assertTest("TC11 Short Feedback", "Feedback must be at least 10 words".equals(msg), msg);
    }

    private void testValidFeedbackSubmission() {
        loadPage();
        fillInput("name", "Tanmay");
        fillInput("email", "t@t.com");
        fillInput("mobile", "9876543210");
        fillInput("password", "pass123");
        fillInput("confirm", "pass123");
        fillInput("feedback", "The complete form validation works well and gives very clear user messages.");
        driver.findElement(By.id("female")).click();
        new Select(driver.findElement(By.id("course"))).selectByVisibleText("BCA");
        submitForm();
        String msg = getAlertTextAndAccept();
        assertTest("TC12 Valid Feedback", "Registration Successful".equals(msg), msg);
    }
}
