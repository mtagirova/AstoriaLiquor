package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static org.junit.Assert.assertTrue;

public class CreateAccountSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @Given("I am on the Astoria Liquor registration page")
    public void i_am_on_the_astoria_liquor_registration_page() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver); // Initialize Actions

        try {
            driver.manage().window().maximize();
            driver.get("https://astorialiquor.com/");

            handlePopup();

            // Find and hover over the 'Login/Sign Up' link and click
            WebElement loginSignUpLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='login-status-conatiner']//a[text()='Login/Sign Up']")));
            actions.moveToElement(loginSignUpLink).click().perform();
        } catch (Exception e) {
            System.err.println("Error during setup: " + e.getMessage());
            driver.quit();
        }
    }

    @When("I choose to Continue with Email")
    public void i_choose_to_continue_with_email() {
        try {
            // Wait until the "Continue with Email" button is visible
            WebElement continueWithEmailButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-hook='continue-with-email-button']")));

            // Wait until the "Continue with Email" button is clickable
            WebElement clickableButton = wait.until(ExpectedConditions.elementToBeClickable(continueWithEmailButton));

            // Click the button
            clickableButton.click();
        } catch (Exception e) {
            System.err.println("Error during email continuation: " + e.getMessage());
            driver.quit();
        }
    }

    @When("I enter valid account details")
    public void i_enter_valid_account_details() {
        try {
            // Wait for the registration form to be visible and find input fields
            WebElement fullNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("fullName")));
            WebElement emailField = driver.findElement(By.name("email"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement confirmPasswordField = driver.findElement(By.name("confirmPassword"));

            // Enter account details
            fullNameField.sendKeys("New User");
            emailField.sendKeys("newuser@example.com");
            passwordField.sendKeys("password123");
            confirmPasswordField.sendKeys("password123");
        } catch (Exception e) {
            System.err.println("Error entering account details: " + e.getMessage());
            driver.quit();
        }
    }

    @When("I submit the registration form")
    public void i_submit_the_registration_form() {
        try {
            // Click the submit button for the registration form
            WebElement submitButton = driver.findElement(By.xpath("//button[text()='Create Account']"));
            submitButton.click();

            // Handle any popups that might appear after form submission
            handlePopup();
        } catch (Exception e) {
            System.err.println("Error submitting the registration form: " + e.getMessage());
            driver.quit();
        }
    }

    private void handlePopup() {
        try {
            // Wait for the modal pop-up to be visible and handle it
            WebElement closePopupButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'close-popup')]")));
            closePopupButton.click();
        } catch (Exception e) {
            System.out.println("Popup did not appear or another issue occurred: " + e.getMessage());
        }
    }

    @Then("I should see a confirmation message")
    public void i_should_see_a_confirmation_message() {
        try {
            // Check if the confirmation message is displayed
            WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationMessage")));
            assertTrue(confirmationMessage.isDisplayed());
        } catch (Exception e) {
            System.err.println("Error verifying confirmation message: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
