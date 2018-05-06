import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class MyTest extends BaseClass {
    @FindBy(xpath = ".//li[@class=\"dropdown adv-analytics-navigation-line1-link current\"]")
    private WebElement strakhovanie;

    @FindBy(xpath = ".//a[contains(text(),'ДМС')]")
    private WebElement dms;

    @FindBy(xpath = ".//span[@class=\"h1\"]")
    private WebElement dmsTitle;

    @FindBy(xpath = ".//a[contains(text(),'Отправить заявку')]")
    private WebElement sendReq;

    @FindBy(xpath = ".//b[contains(text(),'Заявка на добровольное медицинское страхование')]")
    private WebElement reqTitle;

    @FindBy(xpath = ".//input[@name=\"LastName\"]")
    private WebElement lastName;
    @FindBy(xpath = ".//input[@name=\"FirstName\"]")
    private WebElement firstName;
    @FindBy(xpath = ".//input[@name=\"MiddleName\"]")
    private WebElement middleName;
    @FindBy(xpath = ".//select[@name=\"Region\"]")
    private WebElement region;
    @FindBy(css = "#applicationForm > div.row > div:nth-child(5) > input")
    private WebElement phone;
    @FindBy(xpath = ".//input[@name=\"Email\"]")
    private WebElement email;
    @FindBy(xpath = ".//input[@type=\"checkbox\"]")
    private WebElement checkbox;
    @FindBy(xpath = ".//textarea[@name=\"Comment\"]")
    private WebElement comment;

    @FindBy(xpath = ".//button[@id=\"button-m\"]")
    private WebElement sendOurReq;
    @FindBy(xpath = ".//span[contains(text(),'Введите адрес электронной почты')]")
    private WebElement errMessage;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        dr = new ChromeDriver();
        dr.manage().window().maximize();    //полный экран
        dr.get("http://www.rgs.ru");      //заходим на сайт
    }

    @After
    public void tearDown(){dr.quit();
    }

    @Test
    public void test(){
        PageFactory.initElements(dr,this);

        strakhovanie.click();
        dms.click();

        Assert.assertEquals("Заголовок верен",                  //проверка наличия заголовка
                "Добровольное медицинское страхование (ДМС)", dmsTitle.getText());
        sendReq.click();
        waitVisibilityOf(reqTitle);
        Assert.assertEquals("Заголовок верен",
                "Заявка на добровольное медицинское страхование", reqTitle.getText());

        fillField("Иванов",lastName);   //заполняем поля заявки
        fillField("Иван",firstName);
        fillField("Иванович",middleName);
        Select regionS = new Select(region);
        regionS.selectByValue("77");
        fillField("9099995353",phone);
        fillField("qwertyqwerty",email);
        fillField("У дороги",comment);
        checkbox.click();

        Assert.assertEquals("совпадает","Иванов",lastName.getAttribute("value"));
        Assert.assertEquals("совпадает","Иван",firstName.getAttribute("value"));
        Assert.assertEquals("совпадает","Иванович",middleName.getAttribute("value"));
        Assert.assertEquals("совпадает","77",region.getAttribute("value"));
        Assert.assertEquals("совпадает","+7 (909) 999-53-53",phone.getAttribute("value"));
        Assert.assertEquals("совпадает","qwertyqwerty",email.getAttribute("value"));
        Assert.assertEquals("совпадает","У дороги",comment.getAttribute("value"));

        sendOurReq.click();//отправляем заявку
        waitVisibilityOf(errMessage);
        Assert.assertEquals("Есть сообщение",
                "Введите адрес электронной почты", errMessage.getText());

    }
}