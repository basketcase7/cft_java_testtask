package org.example.filter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.example.filter.Handler.isNew;

/**
 * Класс, в котором описана логика записи файлов с отфильтрованными типами данных
 */
public class FilterWriter {

    List<String> strings;
    List<Double> floats;
    List<Long> integers;

    /**
     * Конструктор класса.
     * Создает экземпляр {@code FileWriter}
     * @param strings принимает список, состоящий из строк
     * @param floats принимает список, состоящий из дробных чисел
     * @param integers принимает список, состоящий из целых чисел
     */
    public FilterWriter(List<String> strings, List<Double> floats, List<Long> integers){
        this.strings = strings;
        this.floats = floats;
        this.integers = integers;
    }

    /**
     * Метод, который сохраняет все, что находится в листе, в текстовый файл
     * @param filename принимает название файла, в которые нужно сохранить результат
     * @param data принимает лист, элементы которого нужно взять для сохранения в файл
     * @param <T> тип данных, из которого состоит лист
     *           {@code isNew} определяет, нужно ли оставлять прошлые результаты
     */
    public static <T> void saveToFile(String filename, List<T> data){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, !isNew))){
            for (T item : data){
                bw.write(item.toString());
                bw.newLine();
            }
        } catch (IOException e){
            System.out.println("Ошибка при записи файла: " + filename);
        }
    }

    /**
     * Метод, который создает новый директории для сохранения файлов с результатми, если эти директории отсутствуют
     * @param path принимает путь, по которому необходимо сохранить файл
     */
    public void createOutputDirectory(String path){
        File dir = new File(path);
        if (!dir.exists()){
            if (dir.mkdirs()){
                System.out.println("Каталог создан: " + path);
            }
        }else {
            System.out.println("Не удалось создать каталог или каталог уже существует: " + path);
        }
    }
}
