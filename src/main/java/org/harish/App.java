package org.harish;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

/**
 * Hello world!
 *
 */
public class App 
{
    private static AppiumDriver driver;

    public static void main( String[] args )
    {
        try {
            //openCalculator();
            openAldi();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void openAldi() throws MalformedURLException {
        final DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10");
        cap.setCapability("udid","22b3c81410047ece");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Galaxy S9");
        cap.setCapability("appPackage", "de.apptiv.business.android.aldi_uk");
        cap.setCapability("appActivity", "de.apptiv.business.android.aldi_uk.ui.activity.MainActivity");
        cap.setCapability(MobileCapabilityType.NO_RESET, "true");
        cap.setCapability(MobileCapabilityType.FULL_RESET, "false");

        final URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver<MobileElement>(url, cap);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 60);


        final List<WebElement> dontAllowBtns =  driver.findElements(By.id("de.apptiv.business.android.aldi_uk:id/custom_dialog_button_negative"));
        if(!dontAllowBtns.isEmpty()) {
            MobileElement dontAllowBtn = (MobileElement) dontAllowBtns.get(0);
            wait.until(ExpectedConditions.elementToBeClickable(dontAllowBtn));
            dontAllowBtn.click();
        }
        final MobileElement searchBtn = (MobileElement) driver.findElement(By.id("de.apptiv.business.android.aldi_uk:id/menu_search"));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
        searchBtn.click();

        //search text box id
        final MobileElement searchTxtBox = (MobileElement) driver.findElement(By.id("de.apptiv.business.android.aldi_uk:id/search_src_text"));
        wait.until(ExpectedConditions.elementToBeClickable(searchTxtBox));
        searchTxtBox.click();
        searchTxtBox.sendKeys("chairs");
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("android.widget.TextView")));
        List<WebElement> filterSortBtns = driver.findElements(By.className("android.widget.TextView"));
        String[] verifyBtns = {"FILTER BY", "SORT BY"};
        List<String> actualBtnLabels = filterSortBtns.stream()
                .map( btn -> btn.getText())
                .collect(toList());


        for(String verifyBtn : verifyBtns){
            System.out.println(String.format("Btn %s exists: %b",verifyBtn, actualBtnLabels.contains(verifyBtn)));

        }

        driver.findElement(By.name("FILTER BY")).click();


        System.out.println("Aldi app started");
    }

    private static void openCalculator() throws MalformedURLException {
        final DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName","Galaxy S9");
        //cap.setCapability("udid","22b3c81410047ece");
        cap.setCapability("udid","192.168.1.65:5555");
        cap.setCapability("platformName","Android");
        cap.setCapability("platformVersion","10");
        cap.setCapability("appPackage","com.sec.android.app.popupcalculator");
        cap.setCapability("appActivity","com.sec.android.app.popupcalculator.Calculator");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(url, cap);

        //MobileElement five = (MobileElement)driver.findElement(By.id("0bcec879-6936-496d-bd4f-63f2ae5f236a"));
        MobileElement first = (MobileElement)driver.findElement(By.xpath("/hierarchy//android.widget.Button[13]"));
        MobileElement plus = (MobileElement)driver.findElement(By.xpath("/hierarchy//android.widget.Button[16]"));
        MobileElement six = (MobileElement)driver.findElement(By.xpath("/hierarchy//android.widget.Button[11]"));
        MobileElement equalsTo = (MobileElement)driver.findElement(By.xpath("/hierarchy//android.widget.Button[20]"));
       MobileElement result = (MobileElement)driver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_edt_formula"));

        first.click();
        plus.click();
        six.click();
        equalsTo.click();


       final String actualResult = result.getText();
       System.out.println("Result :" + actualResult);

        System.out.println("Calculator application started...");
    }

}
