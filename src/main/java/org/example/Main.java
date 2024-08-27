package org.example;

import org.example.filter.Handler;

/**
 * Главный класс приложения.
 * Создает экземпляр класса {@link Handler}.
 * Запускает процесс фильтрации данных.
 */
public class Main {
    /**
     *
     * @param args принимает на вход аргументы командной строки, переданные пользователем.
     */
    public static void main(String[] args) {
        Handler handler = new Handler(args);
        handler.filter();
    }
}