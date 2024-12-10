package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Класс содержит локаторы и методы для взаимодействия с тестироемой страницей
 * Реализован с помощью паттерна Page Factory
 */

public class XYZBankPage {
    private WebDriver driver;
    //Локаторы для взаимодействия со страницей
    @FindBy(xpath = "//button[@ng-class='btnClass1']")
    private WebElement openAdd;
    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement firstName;
    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement lastName;
    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement postCode;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement addCustomer;
    @FindBy(xpath = "//button[@ng-class='btnClass3']")
    private WebElement customers;
    @FindBy(css = "a[ng-click=\"sortType = 'fName'; sortReverse = !sortReverse\"]")
    private WebElement sortFirstName;

    public XYZBankPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    /**
     * Метод для заполения полей вкладки ADD Customer. Принимает значения для заполнения полей.
     */
    public void fillFieldsAndAddCustomer(String firstNameText, String lastNameText, String postCodeText) {
        openAdd.click();
        firstName.sendKeys(firstNameText);
        lastName.sendKeys(lastNameText);
        postCode.sendKeys(postCodeText);
        addCustomer.click();
        driver.switchTo().alert().accept();

    }
    /**
     * Метод для поиска клиента в таблице вкладки Customers по First Name. Принимает имя для поиска.
     */
    public boolean findCustomerByName(String name) {
        String xpath = String.format("//td[text()='%s']", name);
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        return !elements.isEmpty();
    }
    /**
     * Метод для открытия вкладки Customers.
     */
    public void openCustomers() {
        customers.click();
    }
    /**
     * Метод для сортировки по имени.
     */
    public void sortFirstName() {
        sortFirstName.click();
    }
    /**
     * Метод для удаления клиента по имени из таблицы вкладки Customers.
     */
    public void deleteCust(String nameForRemove) {
        String xpath = String.format("//td[text()='%s']/ancestor::tr/td/button[@ng-click='deleteCust(cust)']", nameForRemove);
        WebElement deleteCustButton = driver.findElement(By.xpath(xpath));
        deleteCustButton.click();
    }
}
