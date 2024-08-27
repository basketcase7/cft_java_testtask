package org.example.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, отвечающий за чтение аргументов, переданных пользователем в командной строке
 */
public class FilterReader {

    private final List<String> filenames;
    private final List<String> options;

    /**
    Конструктор класса
     * Создает экзмепляр {@code FilterReader}
     */
    public FilterReader(){
        filenames = new ArrayList<>();
        options = new ArrayList<>();
    }

    /**
     *
     * @return Список названий текстовых файлов, которые необходимо отфильтровать
     */
    public List<String> getFilenames() {
        return filenames;
    }

    /**
     *
     * @return Список дополнительных опций
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     *
     * @param optionLine получает на вход аргументы, введенные пользователем.
     * <p>Метод распределяет их на аргументы, представляющие из себя
     * текстовые файлы и дополнительные опции для работы программы.</p>
     * @throws java.io.IOException если произошла ошибка при вводе параметра
     */
    public void getFiles(String[] optionLine){
        for (String option : optionLine){
            try {
                if (option.contains(".txt")){
                    filenames.add(option);
                } else {
                    options.add(option);
                }
            } catch (Exception e) {
                System.out.println("Ошибка при чтении параметра: " + option);
            }
        }
    }

}
