import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import page.XYZBankPage;
import util.Config;
import util.GeneratePostCodeName;
import util.Table;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс тестирования функциональности XYZBank.
 *
 * <p>Этот класс содержит тесты, которые проверяют различные функции банка XYZ,
 * включая добавление клиентов. Каждый тест выполняется в собственном экземпляре
 * веб-драйвера, который открывает веб-приложение банка и взаимодействует с его
 * элементами.</p>
 *
 * <p>Тесты используют аннотации JUnit 5 для настройки и выполнения тестов.</p>
 */
public class XYZBankTest {
    //Поле с генерацией Post Code
    String postCode = GeneratePostCodeName.generatePostCode();
    //Поле с генерацией First Name
    String firstName = GeneratePostCodeName.convertToName(postCode);
    //Конфигурация
    Config config = new Config();
    //Перемнная для веб-драйвера
    private WebDriver driver;
    // Объект страницы банка XYZ
    private XYZBankPage xyzBank;

    /**
     * Метод, выполняющийся перед каждым тестом.
     *
     * <p>Этот метод инициализирует веб-драйвер, открывает приложение банка,
     * устанавливает неявное ожидание и разворачивает окно браузера на полный
     * экран.</p>
     */
    @BeforeEach
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        driver.get(config.getAppUrl());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        xyzBank = new XYZBankPage(driver);
    }

    /**
     * Тест добавления клиента в банк.
     *
     * <p>Этот тест заполняет необходимые поля для добавления нового клиента,
     * открывает таблицу клиентов и проверяет, был ли успешно добавлен клиент
     * с указанным именем.</p>
     */
    @Test
    public void testAddCustomer() {
        xyzBank.fillFieldsAndAddCustomer(firstName, "Snape", postCode);
        xyzBank.openCustomers();
        assertTrue(xyzBank.findCustomerByName(firstName));
    }

    /**
     * Тестирует сортировку имен клиентов в таблице.
     *
     * <p>
     * Этот тест открывает список клиентов, получает начальный порядок имен,
     * сортирует их по имени, и затем проверяет, изменился ли порядок имен
     * после сортировки.
     * </p>
     */
    @Test
    public void testSort() {
        xyzBank.openCustomers();
        WebElement tableElement = driver.findElement(By.xpath("//table[@class = 'table table-bordered table-striped']/tbody"));
        Table table = new Table(tableElement, driver);
        List<String> initialSortNames = table.getAllNames();
        xyzBank.sortFirstName();
        List<String> finalSortNames = table.getAllNames();
        assertFalse(initialSortNames.equals(finalSortNames));
    }

    /**
     * Тестирует удаление клиента из списка.
     *
     * <p>
     * Этот тест открывает список клиентов, получает имя клиента для удаления,
     * удаляет клиента и проверяет, что клиент больше не существует в таблиц.
     * </p>
     */
    @Test
    public void testDelete() {
        xyzBank.openCustomers();
        WebElement tableElement = driver.findElement(By.xpath("//table[@class = 'table table-bordered table-striped']/tbody"));
        Table table = new Table(tableElement, driver);
        String nameFoRemove = table.getNamesForRemove();
        xyzBank.deleteCust(nameFoRemove);
        assertFalse(xyzBank.findCustomerByName(nameFoRemove));
    }

    /**
     * Очищает ресурсы после выполнения каждого теста.
     *
     * <p>
     * Этот метод вызывается после каждого теста для завершения работы
     * драйвера, используемого для взаимодействия с веб-приложением.
     * </p>
     */
    @AfterEach
    public void tearDown() throws Exception {
        driver.quit();
    }
}
