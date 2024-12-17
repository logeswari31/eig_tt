package Ticket_Creation;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class Change {

    public static WebDriver driver;
    public static WebDriverWait wait;

    public static void main(String[] args) throws InterruptedException {
        setup();
        loginPage("https://192.168.4.204:10095/oss", "nocuser1", "nms@123$");
        ttpage("//*[@id='topmenubar']//a[@title='Ticket']");
        ttpage("//div[text()='TT']", "//div[@id='wd_floatingMenuCtnr']//a//span[text()='Open Tickets']");
        newtt("//button[@onclick='loadCreateTTview()']");
        creatett("//div[@id='s2id_TypeofTicket']", "TypeofTicket");
        changett();
    }

    public static void setup() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--incognito");
        //options.addArguments("force-device-scale-factor=0.75");
        //options.addArguments("high-dpi-support=0.75");
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("super-ac-wrapper")));
    }

    public static void actions(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    public static void visibilityOfElementLocated(String locatorvalue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
        System.out.println(element.getText());
        actions(element);
        waituntilpageload();
    }

    public static void elementToBeClickable(String locatorvalue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorvalue)));
        System.out.println(element.getText());
        actions(element);
        element.click();
        System.out.println(element.getText());
        waituntilpageload();
    }

    public static void handledropdown(String selectlocatorvalue) throws InterruptedException {
        Select ttlist = new Select(driver.findElement(By.id(selectlocatorvalue)));
        for (WebElement list : ttlist.getOptions()) {
            System.out.println(list.getText());
            // list.click();
            waituntilpageload();
        }
        getinput(ttlist);
        Thread.sleep(5000);
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

    public static void selectdropdownvalue(String selectlocatorvalue, String text) {
        WebElement tt = driver.findElement(By.xpath(selectlocatorvalue));
        Select ttlist = new Select(tt);
        List<WebElement> list = ttlist.getOptions();
        if(!list.isEmpty()){
            if(list.size() > 1) {
//                for (int i=1; i< list.size(); i++) {
//                    WebElement option = list.get(i);
//                    System.out.println("Option at index " + i + ": " + option.getText());
//                }
                if(!list.isEmpty()) {
                    ttlist.selectByVisibleText(text);
                    String gettext = ttlist.getFirstSelectedOption().getText();
                    System.out.println("Selected option: " + gettext);
                }
            } else {
                System.out.println("Selected option: no values in list ..!");
                System.err.println();
            }
        } else {
            System.out.println("list is empty !");
        }
    }

    public static void selectdropdownoption(String selectlocatorvalue, int index) {
//        WebElement tt = driver.findElement(By.xpath(selectlocatorvalue));
//        Select ttlist = new Select(tt);
//        ttlist.selectByIndex(index);
//        waituntilpageload();
//        String gettext = ttlist.getFirstSelectedOption().getText();
//        System.out.println("Selected option: " + gettext);

        WebElement tt = driver.findElement(By.xpath(selectlocatorvalue));
        Select ttlist = new Select(tt);
        List<WebElement> list = ttlist.getOptions();
        if(!list.isEmpty()){
            if(list.size() > 1) {
                for (int i=1; i< list.size(); i++) {
                    WebElement option = list.get(i);
                    // System.out.println("Option at index " + i + ": " + option.getText());
                }
                if(!list.isEmpty()) {
                    ttlist.selectByIndex(index);
                    waituntilpageload();
                    String gettext = ttlist.getFirstSelectedOption().getText();
                    System.out.println("Selected option: " + gettext);
                }
            } else {
                System.out.println("Selected option: no values in list ..!");
            }
        } else {
            System.out.println("list is empty !");
        }
    }

    public static void enterkeys(String selectlocatorvalue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectlocatorvalue)));
        element.sendKeys(Keys.ENTER);
        System.out.println(element.getText());
    }

    public static void ttpage(String openttlocatorvalue) {
        elementToBeClickable(openttlocatorvalue);
    }

    public static void ttpage(String hoverlocatorvalue, String openttlocatorvalue) {
        visibilityOfElementLocated(hoverlocatorvalue);
        elementToBeClickable(openttlocatorvalue);
    }

    public static void newtt(String newttlocatorvalue) {
        elementToBeClickable(newttlocatorvalue);
    }

    public static void creatett(String ttlistlocatorvalue, String dropdownlocatorvalue) throws InterruptedException {
        elementToBeClickable(ttlistlocatorvalue);
        handledropdown(dropdownlocatorvalue);
        waituntilpageload();
    }

    public static void changett() throws InterruptedException {
        selectdropdownvalue("//select[@id='ChangerequesterGroup']", "Main Network Operation Center");
        selectdropdownvalue("//select[@id='ChangerequesterUser']", "nocuser1");
        selectdropdownoption("//select[@id='ChangemodeOfRequest']", 1);
        selectdropdownvalue("//select[@id='ChangemodeOfRequest']", "Email");
        selectdropdownvalue("//select[@id='ChangeLocation']", "MUMBAI");
        enterkeys("//input[@id='ReportedTime']");
        selectdropdownvalue("//select[@id='ChangeClassification']", "Circuit Change");
        Thread.sleep(3000);
        selectdropdownvalue("//div[@id='s2id_ChangeSubClassification']", "Group of Ciruits");

    }
}




//    public static void ttpage(String hoverlocatorvalue, String ttlocatorvalue) {
//        Actions actions = new Actions(driver);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
//        WebElement elementToHover = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(hoverlocatorvalue)));
//        actions.moveToElement(elementToHover).build().perform();
//        waituntilpageload();
//        WebElement opentt = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ttlocatorvalue)));
//        actions.moveToElement(opentt).click().build().perform();
//    }


//    public static void creatett() {
//        driver.findElement(By.xpath("//div[@id='s2id_TypeofTicket']")).click();
//        waituntilpageload();
//        Select ttlist = new Select(driver.findElement(By.id("TypeofTicket")));
//        for (WebElement list : ttlist.getOptions()) {
//            System.out.println(list.getText());
//            // list.click();
//            waituntilpageload();
//        }
//        // Prompt user for input
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Please enter the ticket type you want to select: ");
//        String userInput = scanner.nextLine();
//        // Select the desired option based on user input
//        boolean optionFound = false;
//        for (WebElement option : ttlist.getOptions()) {
//            if (option.getText().equalsIgnoreCase(userInput)) {
//                ttlist.selectByVisibleText(option.getText());
//                optionFound = true;
//                System.out.println("Selected: " + option.getText());
//                break;
//            }
//        }
//    }


