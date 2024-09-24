package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
/**
 * Класс для взаимодействия с таблцей вкалдки Customers
 */
public class Table {

    private WebElement tableElement;
    private WebDriver driver;

    public Table(WebElement tableElement, WebDriver driver) {
        this.tableElement = tableElement;
        this.driver = driver;
    }
    /**
     * Класс для получения и обработки списка имён для выбора имени на удаление
     */
    public String getNamesForRemove() {
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tr/td[1]"));
        List<String> names = rows
                .stream()
                .map(WebElement::getText)
                .toList();
        double averageLength = names.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0);
        Optional<String> nameForRemove = names.stream()
                .reduce((name1, name2) ->
                        Math.abs(name1.length() - averageLength) < Math.abs(name2.length() - averageLength) ? name1 : name2
                );
        return nameForRemove.get();
    }
    /**
     * Класс для получения списка имён всех клиентов
     */
    public List<String> getAllNames() {
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tr/td[1]"));
        List<String> names = rows
                .stream()
                .map(WebElement::getText)
                .toList();

        return names;
    }
}
