package org.example.stats;

import java.util.Collections;
import java.util.List;

/**
 * Класс, который отвечает за статистику по обработанным файлам
 */
public class ResultStats {
    List<String> strings;
    List<Double> floats;
    List<Long> integers;

    /**
     * Конструктор класса. Создает экземпляр {@code ResultStats}.
     * @param strings принимает список со строками
     * @param floats принимает список с дробными числами
     * @param integers принимает список с целыми числами
     */
    public ResultStats(List<String> strings, List<Double> floats, List<Long> integers){
        this.strings = strings;
        this.floats = floats;
        this.integers = integers;
    }

    /**
     * Метод, который отвечает за отображение статистики в зависимости от состояния переменной {@code statsStatus}
     * @param list принимает лист, статистику по которому нужно собрать
     * @param statsStatus принимает тип статистики, которую нужно собрать
     * @param <T> принимает тип данных, из которого состоит лист, по которому необходимо собрать статистику
     */
    public <T> void giveStats(List<T> list, int statsStatus){
        StringBuffer stringBuffer = new StringBuffer();
        switch (statsStatus){
            case 0:
                break;
            case 1:
                stringBuffer.append("Количество элементов в списке ").append(getName(list)).append(list.size());
                break;
            case 2:
                if (list.isEmpty()) {
                    stringBuffer.append("Список ").append(getName(list)).append(" пуст");
                } else {
                    stringBuffer.append("Количество элементов в списке ").append(getName(list)).append(list.size()).append("\n");
                    if (list.get(0) instanceof Long){
                        List<Long> longList = (List<Long>) list;
                        stringBuffer.append(longStat(longList));
                    }
                    else if (list.get(0) instanceof Double){
                        List<Double> doubleList = (List<Double>) list;
                        stringBuffer.append(doubleStat(doubleList));
                    }
                    else if (list.get(0) instanceof String){
                        List<String> stringList = (List<String>) list;
                        stringBuffer.append(stringStat(stringList));
                    }
                }
        }
        System.out.println(stringBuffer);
    }

    /**
     * Метод, который возвращает название листа, в зависимости от данных, которые в нем храняется
     * @param list принимает лист, название которого необходимо вернуть
     * @return возвращает название листа
     * @param <T> принимает тип данных, из которого состоит лист, название которого необходимо вернуть
     */
    private <T> String getName(List<T> list){
        if (list.get(0).getClass() == Long.class){
            return "Integers: ";
        }
        if (list.get(0).getClass() == Double.class){
            return "Floats: ";
        }
        if (list.get(0).getClass() == String.class){
            return "Strings: ";
        }
        return null;
    }

    /**
     * Метод, который собирает полную статистику по листу с целыми числами
     * @param longList принимает лист из полных чисел, по которому необходимо собрать статистику
     * @return возвращает {@link StringBuffer} в котором содержится статистика по целым числам
     */
    private StringBuffer longStat(List<Long> longList){
        StringBuffer stringBuffer = new StringBuffer();
        long min = Collections.min(longList);
        long max = Collections.max(longList);
        double avg = longList.stream().mapToDouble(Long::doubleValue).average().orElse(0.0);
        stringBuffer
                .append("Минимальное значение: ").append(min).append("\n")
                .append("Максимальное значение: ").append(max).append("\n")
                .append("Среднее значение: ").append(avg).append("\n");
        return stringBuffer;
    }
    /**
     * Метод, который собирает полную статистику по листу с дробными числами
     * @param doubleList принимает лист из дробных чисел, по которому необходимо собрать статистику
     * @return возвращает {@link StringBuffer} в котором содержится статистика по дробным числам
     */
    private StringBuffer doubleStat(List<Double> doubleList){
        StringBuffer stringBuffer = new StringBuffer();
        double min = Collections.min(doubleList);
        double max = Collections.max(doubleList);
        double avg = doubleList.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        stringBuffer
                .append("Минимальное значение: ").append(min).append("\n")
                .append("Максимальное значение: ").append(max).append("\n")
                .append("Среднее значение: ").append(avg).append("\n");
        return stringBuffer;
    }
    /**
     * Метод, который собирает полную статистику по листу со строками
     * @param stringList принимает лист из строк, по которому необходимо собрать статистику
     * @return возвращает {@link StringBuffer} в котором содержится статистика по строкам
     */
    private StringBuffer stringStat(List<String> stringList){
        StringBuffer stringBuffer = new StringBuffer();
        int minLength = stringList.stream().mapToInt(String::length).min().orElse(0);
        int maxLength = stringList.stream().mapToInt(String::length).max().orElse(0);
        stringBuffer
                .append("Длина минимальной строки: ").append(minLength).append("\n")
                .append("Длина максимальной строки: ").append(maxLength).append("\n");
        return stringBuffer;
    }
}
