package Ticket_Creation;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Scanner;

public class test {
    public static WebDriver driver;
    public static WebDriverWait wait;

    public static void main(String[] args) throws InterruptedException {
        setup();
        loginPage("https://192.168.4.96:10095/oss", "nocuser2", "nms@123$");
        ttpage();
        changettcreation();
    }

    public static void setup() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--incognito");
        options.addArguments("force-device-scale-factor=0.80");
        options.addArguments("high-dpi-support=0.80");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    public static void loginPage(String url, String uname, String pwd) {
        driver.get(url);
        WebElement user = driver.findElement(By.id("username"));
        user.sendKeys(uname);
        WebElement pass = driver.findElement(By.id("password"));
        pass.sendKeys(pwd);
        WebElement login = driver.findElement(By.cssSelector(".btn-default"));
        login.click();
        waituntilpageload();
    }

    public static void waituntilpageload() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("super-ac-wrapper")));
    }

    public static void elementToBeClickable(String locatorvalue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading-wrapper")));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorvalue)));
        actions(element);
        try {
            if(!element.getText().isEmpty()) {
                System.out.println(element.getAccessibleName() + ": " + element.getText());
            }
        } catch (Exception e) {
            System.out.println("No text found");
        }
        waituntilpageload();
    }

    public static void actions(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    public static void handledropdown(String selectlocatorvalue) throws InterruptedException {
        Select ttlist = new Select(driver.findElement(By.id(selectlocatorvalue)));
        for (WebElement list : ttlist.getOptions()) {
            System.out.println(list.getText());
            waituntilpageload();
        }
        getinput(ttlist);
        waituntilpageload();
    }

    public static void getinput(Select ttlist) {
        // Prompt user for input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the ticket type you want to select: ");
        String userInput = scanner.nextLine();
        // Select the desired option based on user input
        boolean optionFound = false;
        for (WebElement option : ttlist.getOptions()) {
            if (option.getText().equalsIgnoreCase(userInput)) {
                ttlist.selectByVisibleText(option.getText());
                optionFound = true;
                System.out.println("Selected: " + option.getText());
                break;
            }
        }
    }

    public static void verifySelectedDropdownValue(String locatorvalue, String value) throws InterruptedException {
        WebElement dropdownElement = driver.findElement(By.xpath(locatorvalue));
        Select dropdown = new Select(dropdownElement);
        String selectedValue = dropdown.getFirstSelectedOption().getText();
        if (selectedValue.equals(value)) {
            System.out.println("Selected option: " + selectedValue);
        }
        Thread.sleep(500);
    }

    public static void verifyDropdownValueMatches(String locatorvalue, String ddvalue) throws InterruptedException {
        WebElement dropdownElement = driver.findElement(By.xpath(locatorvalue));
        String ddtext = dropdownElement.getText();
        if (ddtext.equals(ddvalue)) {
            System.out.println("Dropdown value matches username: "+ ddtext);
        }
        Thread.sleep(500);
    }

    public static void selectByVisibleText(String locatorvalue, String value) throws InterruptedException {
        WebElement dropdown = driver.findElement(By.xpath(locatorvalue));
        Select dd =  new Select(dropdown);
        dd.selectByVisibleText(value);
        String ddtext = dd.getFirstSelectedOption().getText();
        System.out.println("Selected option: " + ddtext);
        Thread.sleep(500);
    }

    public static void selectByIndex(String locatorvalue, int value) throws InterruptedException {
        WebElement dropdown = driver.findElement(By.xpath(locatorvalue));
        Select dd =  new Select(dropdown);
        dd.selectByIndex(value);
        String ddtext = dd.getFirstSelectedOption().getText();
        System.out.println("Selected option: "+ ddtext);
        Thread.sleep(500);
    }

    public static void enterkeys(String locatorvalue) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
        element.sendKeys(Keys.ENTER);
        System.out.println(element.getAccessibleName() + ": " + element.isDisplayed());
        Thread.sleep(500);
    }

    public static void sendkeys(String locatorvalue, String value) throws InterruptedException {
        WebElement element = driver.findElement(By.xpath(locatorvalue));
        element.sendKeys(value);
        System.out.println(element.getAccessibleName() + ": " + value + " " + element.getAccessibleName());
        Thread.sleep(500);
    }

    public static void ttpage() {
        elementToBeClickable("//div[@id='topmenubar']//li//a[@id='tt_menu_1']");
        elementToBeClickable("//div[@id='tt_menu_1_submenu']//li//a[text()='Open Tickets']");
    }

    public static void changettcreation() throws InterruptedException {
        elementToBeClickable("//div[@id='TroubleTicketView']//div//div//button[normalize-space(text())='New Ticket']");
        elementToBeClickable("//div[@id='s2id_TypeofTicket']");
        handledropdown("TypeofTicket");

//        WebElement dropdownElement = driver.findElement(By.xpath("//select[@id='ChangerequesterGroup']"));
//        Select dropdown = new Select(dropdownElement);
//        String selectedValue = dropdown.getFirstSelectedOption().getText();
//        if (selectedValue.equals("Main Network Operation Center")) {
//            System.out.println("Dropdown value matches the username: " + selectedValue);
//        }

        verifySelectedDropdownValue("//select[@id='ChangerequesterGroup']", "Main Network Operation Center");

//        WebElement dropdownElement = driver.findElement(By.xpath("//select[@id='ChangerequesterUser']/option[@value]"));
//        String ddtext = dropdownElement.getText();
//        if (ddtext.equals("nocuser1")) {
//            System.out.println("Dropdown value matches the username: " + ddtext);
//        }

        verifyDropdownValueMatches("//select[@id='ChangerequesterUser']/option[@value]", "nocuser1");

//        WebElement dropdown = driver.findElement(By.xpath("//select[@id='ChangemodeOfRequest']"));
//        Select dd =  new Select(dropdown);
//        dd.selectByVisibleText("Email");
//        String ddtext = dd.getFirstSelectedOption().getText();
//        System.out.println("Selected option: " + ddtext);

        selectByVisibleText("//select[@id='ChangemodeOfRequest']", "Email");
        selectByVisibleText("//select[@id='ChangeLocation']", "ABU TALAT");

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ReportedTime']")));
//        element.sendKeys(Keys.ENTER);

        enterkeys("//input[@id='ReportedTime']");

        selectByVisibleText("//select[@id='ChangeClassification']", "Circuit Change");
        selectByIndex("//select[@id='ChangeSubClassification']", 1);
        selectByIndex("//select[@id='AffectedParties']", 1);
        selectByIndex("//select[@id='AffectedCircuitList']", 1);

        enterkeys("//input[@id='startTime1']");
        enterkeys("//input[@id='endTime1']");
        enterkeys("//input[@id='startTime2']");
        enterkeys("//input[@id='endTime2']");

        // check-box
//        WebElement element = driver.findElement(By.xpath("//div[@class='form-group']//input[@id='ServiceAffecting']"));
//        Actions act = new Actions(driver);
//        act.click(element).build().perform();
//        if(element.isDisplayed()) {
//            System.out.println(element.getAccessibleName() + ": " + element.isDisplayed());
//        } else {
//            System.out.println(element.getAccessibleName() + ": not selected");
//        }

//        WebElement element = driver.findElement(By.xpath("//input[@id='PowerOn/offRequired']"));
//        Actions act = new Actions(driver);
//        act.click(element).build().perform();
//        act.sendKeys(Keys.ENTER).build().perform();
//        element.click();

        selectByIndex("//select[@id='TriggeredBy']", 1);
        selectByIndex("//select[@id='ImpactRisk']", 1);
        selectByIndex("//select[@id='ChangeImpact']", 1);

//        WebElement element = driver.findElement(By.xpath("//input[@id='ReasonforChange']"));
//        element.sendKeys("input" + element.getAccessibleName());
//        System.out.println(element.getText());

        sendkeys("//input[@id='ReasonforChange']", "Reason for change");

        selectByIndex("//select[@id='Priority']", 1);
        selectByIndex("//select[@id='Urgency']", 1);

        sendkeys("//input[@id='subject']", "create change tt");
        sendkeys("//div[@class='note-editable']", "create change tt");

        sendkeys("//input[@id='TestPlanAttachments']", "/home/logeswari/Music/Selenium/TT/src/test/resources/files/touch.xls");
        sendkeys("//input[@id='FallbackPlanAttachments']", "/home/logeswari/Music/Selenium/TT/src/test/resources/files/touch.xls");
        sendkeys("//input[@id='SecurityImpactAttachments']", "/home/logeswari/Music/Selenium/TT/src/test/resources/files/touch.xls");
        sendkeys("//input[@id='Attachments']", "/home/logeswari/Music/Selenium/TT/src/test/resources/files/touch.xls");
        sendkeys("//input[@class='ImpactedCircuitAttachment']", "/home/logeswari/Music/Selenium/TT/src/test/resources/files/touch.xls");

//        WebElement ele = driver.findElement(By.xpath("//div[@id='s2id_LinkedTicket']"));
//        ele.click();
//        WebElement sk =  driver.findElement(By.xpath("//input[@id='s2id_autogen8_search']"));
//        sk.sendKeys("EIG-TT");
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='select2-results']//li[1]/div")));
//        option.click();

        elementToBeClickable("//div[@id='s2id_LinkedTicket']");
        sendkeys("//input[@id='s2id_autogen8_search']", "EIG-TT");
        elementToBeClickable("//ul[@class='select2-results']//li[1]/div");

        elementToBeClickable("//button[@id='Submit']");

    }


}
