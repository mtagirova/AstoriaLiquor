package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class SearchSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I am on the Astoria Liquor homepage")
    public void i_am_on_the_astoria_liquor_homepage() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        try {
            driver.manage().window().maximize();
            driver.get("https://astorialiquor.com/");
        } catch (Exception e) {
            System.err.println("Error during setup: " + e.getMessage());
            driver.quit();
        }
    }

    @When("I search for vodka")
    public void i_search_for_vodka() {
        try {
            Thread.sleep(5000); // Reduced sleep time to 5 seconds

            handleEmailPopup();

            WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-hook='search-box-input']")));
            searchBar.sendKeys("vodka");

            searchBar.submit();
        } catch (Exception e) {
            System.err.println("Error during search: " + e.getMessage());
            driver.quit();
        }
    }

    private void handleEmailPopup() {
        try {
            WebElement popupCloseButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='skeleton close-popup']")));

            popupCloseButton.click();

            System.out.println("Pop-up closed successfully.");
        } catch (Exception e) {
            System.err.println("Error handling pop-up: " + e.getMessage());
        }
    }

    @Then("I should see search results for vodka")
    public void i_should_see_search_results_for_vodka() {
        try {

            WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-results"))); // Update locator as needed
            System.out.println("Search results: " + resultsContainer.getText());
            String resultsText = resultsContainer.getText();
            assertTrue("Search results do not contain 'vodka'", resultsText.toLowerCase().contains("vodka"));
        } catch (Exception e) {
            System.err.println("Error verifying search results: " + e.getMessage());
        } finally {

            if (driver != null) {
                driver.quit();
            }
        }
    }
}
