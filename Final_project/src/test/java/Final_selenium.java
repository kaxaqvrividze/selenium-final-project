import com.google.gson.internal.bind.util.ISO8601Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.awt.AWTException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


import java.util.concurrent.TimeUnit;

public class Final_selenium {
    WebDriver driver;


    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) throws Exception{

        //Check if parameter passed as 'chrome'
        if(browser.equalsIgnoreCase("chrome")){
            //set path to chromedriver.exe
            //  System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            WebDriverManager.chromedriver().setup();

            //create chrome instance
            ChromeDriver driver = new ChromeDriver();
            //driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        }
        //Check if parameter passed as 'Edge'
        else if(browser.equalsIgnoreCase("Edge")){
            //set path to Edge.exe
            // System.setProperty("webdriver.edge.driver", ".\\src\\main\\resources\\MicrosoftWebDriver.exe");
            WebDriverManager.edgedriver().setup();

            //create Edge instance
            EdgeDriver driver = new EdgeDriver();
            //driver = new HtmlUnitDriver(BrowserVersion.EDGE);
        }
        else{
            //If no browser passed throw exception
            throw new Exception("Browser is not correct");
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void demo() throws InterruptedException, AWTException {

        driver.manage().window().maximize();
        driver.get("http://tutorialsninja.com/demo/");

        Actions action = new Actions(driver);
        RandomStringGenerator randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
//                            Go to 'My Account' and click on 'Register' button
//                                          Xpath
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul//a/span[@class='hidden-xs hidden-sm hidden-md' and text() = 'My Account']")).click();
//                                           Xpath
        driver.findElement(By.xpath("//*[@class = 'dropdown-menu dropdown-menu-right']/li/a[text()='Register']")).click();
        String register_first_name = "kakha";
//                                            JS
//          Fill personal information, choose 'Subscribe' Yes and click on 'Continue' button
        executor.executeScript(String.format("document.querySelector(\"#input-firstname\").value = '%s' ", register_first_name));
//                                            JS

        String register_last_name = "kvrividze";
        executor.executeScript(String.format("document.querySelector(\"#input-lastname\").value = '%s'", register_last_name));
//                                            CSS
        driver.findElement(By.cssSelector("#input-email")).sendKeys(randomStringGenerator.generate(5) + "@gmail.com");
//                                             CSS
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("555555555");
//                                              CSS
        driver.findElement(By.cssSelector("input.form-control[name=password]")).sendKeys("asdasd123");
//                                               Xpath
        driver.findElement(By.xpath("//*[@id=\"input-confirm\"]")).sendKeys("asdasd123");
//                                              JS
        executor.executeScript("document.querySelector(\"#content > form > fieldset > div > div > label > input[value ='1']\").checked = true");
//                                              CSS
        driver.findElement(By.cssSelector("#content > form > div > div > input[type=checkbox]")).click();
//                                              JS
        executor.executeScript("document.querySelector(\"#content > form > div > div > input.btn.btn-primary\").click()");



//        -                                           Move to 'Desktops' and select 'Show all Desktops'

        action.moveToElement(driver.findElement(By.xpath("//a[@href=\"http://tutorialsninja.com/demo/index.php?route=product/category&path=20\" and @class='dropdown-toggle']")))
                .perform();
        action.moveToElement(driver.findElement(By.linkText("Show All Desktops"))).click().perform();

//                                                           - Choose 'MP3 Players' item
        driver.findElement(By.xpath("//a[@class='list-group-item' and contains(text(),'MP3 Players')]")).click();

//                                  Move to 'iPod Classic' image and check that 'iPod Classic' text is visible on mouse hover
        action.moveToElement(driver.findElement(By.xpath("//img[@src='http://tutorialsninja.com/demo/image/cache/catalog/demo/ipod_shuffle_1-228x228.jpg']"))).perform();


        WebElement check = driver.findElement(By.xpath("//img[@src='http://tutorialsninja.com/demo/image/cache/catalog/demo/ipod_shuffle_1-228x228.jpg']"));

        check.getAttribute("title");

        if (check.getAttribute("title").equals("iPod Shuffle")) {
            System.out.println("visible");
        }


//                      Click on 'iPod Classic' link
        driver.findElement(By.linkText("iPod Shuffle")).click();

        String price = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[@class='col-sm-4']/ul//h2")).getText();

//        Click on first image and move on another images before text '4 of 4' is present (check Pic1)
        int list_size = driver.findElements(By.xpath("//*[@id=\"content\"]//ul[@class = 'thumbnails']/li")).size();

        driver.findElement(By.xpath("//*[@id=\"content\"]//ul[@class = 'thumbnails']/li/a[@href='http://tutorialsninja.com/demo/image/cache/catalog/demo/ipod_shuffle_1-500x500.jpg']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);

        for (int i = 1; i < list_size; i++) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//div[@class='mfp-counter' and contains(text(),'%s of ')]", i))));
            driver.findElement(By.xpath("//button[@title='Next (Right arrow key)']")).click();

        }
        executor.executeScript("document.querySelector(\"body > div.mfp-wrap.mfp-gallery.mfp-close-btn-in.mfp-auto-cursor.mfp-ready > div > div.mfp-content > div > button\").click()");



//                                                          Click on 'Write a review' , fill information and submit
        driver.findElement(By.xpath("//a[contains(text(),'Write a review')]")).click();



        driver.findElement(By.id("input-review")).sendKeys(randomStringGenerator.generate(30));
        driver.findElement(By.xpath("//input[@type='radio' and @value='5']")).click();
        driver.findElement(By.cssSelector("#button-review")).click();
//                                          button-cart
        driver.findElement(By.id("button-cart")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(("//span[@id='cart-total' and contains(text(),'1')]"))));

        System.out.println(driver.findElement(By.xpath("//*[@id=\"cart-total\"]")).getText());
        driver.findElement(By.className("cart-total")).click();
        driver.findElement(By.xpath("//p[@class='text-right']/a[@href='http://tutorialsninja.com/demo/index.php?route=checkout/checkout']")).click();

//                                                       - Fill Billing Details, choose Georgia and Tbilisi
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-payment-firstname")));
        WebElement billingFirstname = driver.findElement(By.id("input-payment-firstname"));
        billingFirstname.sendKeys("kakha");

        driver.findElement(By.id("input-payment-lastname")).sendKeys("kakha");
        driver.findElement(By.id("input-payment-company")).sendKeys("TBC");
        driver.findElement(By.id("input-payment-address-1")).sendKeys("digomi");
        driver.findElement(By.id("input-payment-city")).sendKeys("tbilisi");
        driver.findElement(By.id("input-payment-postcode")).sendKeys("4915");
        driver.findElement(By.xpath("//select[@id='input-payment-country']/option[text()='Georgia']")).click();

        action.moveToElement(driver.findElement(By.xpath("//select[@id='input-payment-zone']"))).click().perform();
//        driver.findElement(By.xpath("//*[@id=\"input-payment-zone\"]/option[contains(text(),'Tbilisi')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"input-payment-zone\"]/option[contains(text(),'Tbilisi')]"))).click();
        driver.findElement(By.xpath("//*[@id=\"button-payment-address\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-shipping-address"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"button-shipping-method\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='checkbox' and @name='agree' and @value='1']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-payment-method"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div/table/tfoot")));
        List<WebElement> allRows = driver.findElements(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div/table/tfoot"));

        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.tagName("tr"));

            for (WebElement cell : cells) {

                System.out.println(cell.getText().split(":")[1]);

            }
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-confirm"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]//a[@href=\"http://tutorialsninja.com/demo/index.php?route=account/order\"]"))).click();

//            String subtotal = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr[1]/td[2]")).getText();
//
//            if (price.equals(subtotal)){
//                System.out.println("yes");
//            }
//            else {
//                System.out.println("price:"+price+"subtotal:"+subtotal);
//            }


//        ამოწმებს პედინგი არი თუ არა
        String checkstatus = driver.findElement(By.xpath("//*[@id=\"content\"]/div/table/tbody/tr/td[text() = 'Pending']")).getText();
        if (checkstatus.equals("Pending")) {
            System.out.println("Pending");
        }
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String datetime = date.format(formatter);


        try {
            String curdate = driver.findElement(By.xpath(String.format("//*[@id=\"content\"]/div[1]/table/tbody/tr/td[text() = '%s']", datetime))).getText();


// ამოწმებს არი თუ არა დრო სწორი
            if (datetime.equals(curdate)) {
                System.out.println("date equal to current date");
            }
        }
        catch (NoSuchElementException e){
            System.out.println("date not equal to current date\"");
        }



    }   
}
