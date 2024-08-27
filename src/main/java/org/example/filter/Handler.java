package org.example.filter;

import org.example.stats.ResultStats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.filter.FilterWriter.saveToFile;

/**
 * Класс, в котором описана вся логика фильтрации документов и обработки дополнительных опций.
 */
public class Handler {

    private final String[] args;

    List<String> strings = new ArrayList<>();
    List<Double> floats = new ArrayList<>();
    List<Long> integers = new ArrayList<>();

    private final FilterReader filterReader;
    private final FilterWriter filterWriter;
    private final ResultStats resultStats;

    private String outputPath = "";
    private String prefix = "";
    /**
     * <p>{@code inNew} Флаг, отвечающий за то, нужно ли перезаписывать прошлые результаты</p>
     * {@code true} - не оставляет прошлые результаты
     * {@code false} - оставляет прошлые результаты
     */
    public static boolean isNew = true;
    /**
     * {@code statsStatus} отвечает за то, в каком виде должна отобразиться статистика
     * <p>{@code 0} - Не отображать статистику</p>
     * <p>{@code 1} - Краткая статистика</p>
     * <p>{@code 2} - Полная статистика</p>
     */
    public static int statsStatus = 0;
    /**
     * Конструктор для класса.
     * Создает экземпляр {@code Handler}
     * @param args принимает аргументы, введенные пользователем в виде дополнительных опций и текстовых файлов.
     *
     */
    public Handler(String[] args) {
        this.args = args;
        this.filterReader = new FilterReader();
        this.filterWriter = new FilterWriter(strings, floats, integers);
        this.resultStats = new ResultStats(strings, floats, integers);
    }
    /**
     * Метод, который поочередно применяет методы, которые производят фильтрацию по типам данных,
     * обработку дополнительных опций, сохранение данных в файл, а также вызов отображения статистики
     */
    public void filter() {
        filterReader.getFiles(args);
        divideTypes(filterReader.getFilenames());
        divideOptions(filterReader.getOptions());

        if (outputPath != null) {
            filterWriter.createOutputDirectory(outputPath);
        }
        if (!integers.isEmpty()) saveToFile(outputPath + prefix + "integers.txt", integers);
        if (!strings.isEmpty()) saveToFile(outputPath + prefix + "strings.txt", strings);
        if (!floats.isEmpty()) saveToFile(outputPath + prefix + "floats.txt", floats);
        if (!integers.isEmpty())resultStats.giveStats(integers, statsStatus);
        if (!strings.isEmpty())resultStats.giveStats(strings, statsStatus);
        if (!floats.isEmpty())resultStats.giveStats(floats, statsStatus);
    }
    /**
     * Сеттер для префикса имени
     * @param prefix Устанавливает префикс имени для создаваемых файлов
     */
    private void setPrefix(String prefix){
        this.prefix = prefix;
    }
    /**
     * Сеттер для пути новых файлов
     * @param path устанавливает путь, куда необходимо сохранить новые файлы
     */
    private void setPath(String path){
        this.outputPath = path + File.separator;
    }
    /**
     * Метод пробегается по листу, состоящему из названий текстовых файлов, и применяет к каждому из них метод {@code processLine}
     * @param filenames принимает на вход лист, состоящий из названий текстовых файлов, которые нужно отфильтровать
     * @throws IOException если произошла ошибка при чтении файла
     */
    private void divideTypes(List<String> filenames){
        for (String filename : filenames){
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))){
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    processLine(line, integers, floats, strings);
                }
            }
            catch (IOException e){
                System.out.println("Ошибка при чтении файла: " + filename);
            }
        }
    }

    /**
     * Метод обрабатывает дополнительные опции, которые необходимо применить
     * @param options принимает на вход лист, состоящий из дополнительных опций, которые нужно применить
     */
    private void divideOptions(List<String> options){
        for (int i = 0; i < options.size(); i++){
            switch (options.get(i)){
                case "-o":
                    if (options.size() > i + 1){
                        setPath(options.get(i + 1));
                        i+=1;
                    }
                    else {System.out.println("Ошибка при вводе пути");}
                    break;
                case "-p":
                    if (options.size() > i + 1){
                        setPrefix(options.get(i + 1));
                        i+=1;
                    }
                    else {System.out.println("Ошибка при вводе префикса имени");}
                    break;
                case "-a":
                    isNew = false;
                    break;
                case "-s":
                    statsStatus = 1;
                    break;
                case "-f":
                    statsStatus = 2;
                    break;
                default:
                    System.out.println("Ошибка в параметре: " + options.get(i));
            }
        }
    }

    /**
     * Метод, который определяет к какому типу данных относится элемент в текстовом файле
     * @param line принимает на вход элемент который нужно отфильтровать
     * @param integers принимает на вход лист, в котором хранятся целые числа
     * @param floats принимает на вход лист, в которым хранятся дробные числа
     * @param strings принимает на вход лист, в котором хранятся строки
     */
    private static void processLine(String line, List<Long> integers, List<Double> floats, List<String> strings){
        try {
            if (line.contains(".")){
                floats.add(Double.parseDouble(line));

            }
            else {
                integers.add(Long.parseLong(line));
            }
        } catch (NumberFormatException e){
            strings.add(line);
        }
    }
}
