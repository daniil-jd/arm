package ru.arm.gm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Запускающий класс.
 */
@SpringBootApplication
public class Application {

    /**
     * Запускает приложение.
     * @param args аргументы
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
