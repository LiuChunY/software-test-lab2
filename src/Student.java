

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Student {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    private ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    private InputStream stream = null;
    Workbook wb;

    @Before
    public void setUp() throws Exception {
        String driverPath = System.getProperty("user.dir") + "/src/driver/geckodriver";
        System.setProperty("webdriver.gecko.driver", driverPath);
        driver = new FirefoxDriver();
        baseUrl = "http://121.193.130.195:8800";
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        stream = new FileInputStream("/Users/liuchunyang/IdeaProjects/lab2/src/user.xlsx");
        try {
            wb = new XSSFWorkbook(stream);
        } catch (Exception e) {

        }

        org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
        for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            int maxCells = row.getLastCellNum();
            ArrayList<String> List = new ArrayList<String>();
            Cell cell = null;
            for (int j = 1; j < maxCells; j++) {
                cell = row.getCell(j);
                if (cell == null)
                    continue;
                cell.setCellType(CellType.STRING);
                List.add(cell.getStringCellValue());

            }
            list.add(List);
        }
    }

    @Test
    public void testStudent() throws Exception {
        try {

            driver.get(baseUrl + '/');
            for (int i = 0; i < list.size(); i++) {

                driver.get("http://121.193.130.195:8800/login");
                driver.findElement(By.name("id")).click();
                driver.findElement(By.name("id")).clear();
                driver.findElement(By.name("id")).sendKeys(list.get(i).get(0));
                driver.findElement(By.name("password")).click();
                driver.findElement(By.name("password")).clear();
                driver.findElement(By.name("password")).sendKeys(list.get(i).get(0).substring(4, 10));
                driver.findElement(By.id("btn_login")).click();
                String idString = driver.findElement(By.id("student-id")).getText();
                String passwordString = driver.findElement(By.id("student-name")).getText();
                String gitUrlString = driver.findElement(By.id("student-git")).getText();
                ArrayList<String> webStrings = new ArrayList<String>();
                webStrings.add(idString);
                webStrings.add(passwordString);
                webStrings.add(gitUrlString);
                assertEquals(list.get(i), webStrings);
                driver.findElement(By.id("btn_logout")).click();
                driver.findElement(By.id("btn_return")).click();
                System.out.println(list.get(i).get(0) +" "+ list.get(i).get(1) + " Test FINISHED");
            }
        }
        catch (Exception e) {
            System.out.println("What the?");
            e.printStackTrace();
        }
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

}

