import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Avito {
    WebDriver driver;

    @BeforeClass
    public void prepare() {
        System.setProperty("webdriver.chrome.driver", "C:\\Driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.avito.ru");

    }

    @Test(priority = 1)
    public void categorySelect() {
        Select categorySelect = new Select(driver.findElement(By.id("category")));
        categorySelect.selectByVisibleText("Оргтехника и расходники");
    }

    @Test(priority = 2)
    public void searchSelect() {
        driver.findElement(By.xpath("//*[@id=\"search\"]")).sendKeys("Принтер");
    }

    @Test(priority = 3)
    public void citySearch() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(@data-marker, \"search-form/region\")]/*")).click();
        driver.findElement(By.xpath("//*[contains(@data-marker, \"popup-location/region/input\")]")).sendKeys("Владивосток");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[contains(@data-marker, \"suggest(0)\")]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[6]/div/div/span/div/div[4]/div[2]/div/button")).click();
    }

    @Test(priority = 4)
    public void checkBox() {
        WebElement genreCheckbox = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div/div[2]/div[1]/form/div[5]/div/div/div/div/div/div/label/span"));
        if (!genreCheckbox.isSelected()) {
            genreCheckbox.click();
        }
    }

    @Test(priority = 5)
    public void showAd() {
        driver.findElement(By.xpath("//div/button[contains(@data-marker, 'search-filters/submit-button')]")).click();
    }

    @Test(priority = 6)
    public void sortGoods() {
        Select sortGoods = new Select(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[3]/div[1]/div[2]/select")));
        sortGoods.selectByVisibleText("Дороже");
    }

    @Test(priority = 7)
    public void goods() {
        List<WebElement> goods = driver.findElements(By.xpath(".//*[contains(@class, 'snippet-list')]/*"));
        int n = 0;
        for (WebElement printer : goods) {
            if (!printer.findElements(By.className("snippet-title-row")).isEmpty() ||
                    !printer.findElements(By.className("snippet-price")).isEmpty()) {
                WebElement name = printer.findElement(By.className("snippet-title-row"));
                WebElement price = printer.findElement(By.className("snippet-price"));
                System.out.printf("Марка: " + name.getText() + " " + "Цена:" + price.getText() + "\n");
                n++;
            }
            if (n == 3) break;
        }
    }
    @AfterClass
    public void end(){
        driver.quit();
    }

}

