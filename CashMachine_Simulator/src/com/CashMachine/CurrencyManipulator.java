package CashMachine;

import CashMachine.exception.*;

import java.util.*;


public class CurrencyManipulator
{
    private Currency currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if(denominations.containsKey(denomination))
            denominations.put(denomination, denominations.get(denomination) + count);
        else
            denominations.put(denomination,count);
    }

    public int getTotalAmount() {
        int result = 0;
        for(Map.Entry<Integer,Integer> pair : denominations.entrySet())
            result = result + (pair.getKey() * pair.getValue());
        return result;
    }

    public boolean hasMoney(){
        return denominations.size() != 0;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        if (getTotalAmount() >= expectedAmount)
            return true;
        else
            return false;
    }

    private static TreeMap<Integer, Integer> temp = new TreeMap<>(Collections.reverseOrder());  //  Временная копия кассы
    private static int sum;
    private static LinkedHashMap<Integer, Integer> result = new LinkedHashMap<>();
    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        sum = expectedAmount;
        temp.putAll(denominations);
        result.clear();
        sum = calculating();
        if (sum > 0)
            throw new NotEnoughMoneyException();
        else
        {
            for (Map.Entry<Integer, Integer> entry : result.entrySet())
                denominations.put(entry.getKey(), denominations.get(entry.getKey()) - entry.getValue());
            temp.clear();
        }
        return result;
    }

    private static int calculating()
    {
        //  Получаем простой список номиналов (без количества купюр)
        ArrayList<Integer> list = new ArrayList<>(); //  заполняется доступными номиналами
        for (Map.Entry<Integer, Integer> pair : temp.entrySet())
            list.add(pair.getKey());
        try
        {
            for (Integer note : list)   // для каждого номинала начиная с самого большого из доступных
            {
                if (note < sum)  // если номинал меньше нужной суммы
                {
                    int count = sum / note;  //  Максимум купюр, что можем взять
                    for (; count > 0; count--)
                    {
                        if (temp.get(note) >= count)  //  Проверяем, доступно ли столько купюр
                            break;                    //  Получаем максимально доступное число
                    }
                    addAmount(note, count, result);   //  Записываем в результат
                    removeAmount(note, count, temp);  //  Убираем из временной кассы
                    sum -= note * count;  //  Уменьшаем сумму
                    if (sum == 0)  //  Если получилось сложить - все ОК
                        break;
                    else  //  Иначе пробуем дальше подбирать
                    {
                        sum = calculating();  // Рекурсия №1
                        if (sum == 0)  //  Если получилось сложить - все ОК
                            break;
                        else  //  Иначе удаляем последнюю добавленную купюру
                        {
                            int removeCount = 0;  // считаем сколько таких купюр удаляется
                            do
                            {
                                if (result.containsKey(note))  //  Если еще есть эта купюра в результате
                                {
                                    removeAmount(note, 1, result);  // Удаляем ее
                                    removeCount++;  //  Считаем количество удаленных купюр
                                    sum += note;  //  Добавляем к сумме
                                    sum = calculating();  // Проверяем заново - Рекурсия №2
                                }
                                else  //  Если уже удалили все купюры этого номинала, а сумму так и не сложили
                                {
                                    addAmount(note, removeCount, temp);  //  Возвращаем все эти купюры обратно в кассу
                                    break;
                                }
                            }
                            while (sum!=0);  //  Рекурсия №1 завершилась и получила свой результат
                        }
                    }
                }
                else if (note == sum)  //  Если номинал равен сумме
                {
                    addAmount(note, 1, result);
                    removeAmount(note, 1, temp);
                    sum -= note;
                }
            }
            return sum;
        }
        catch (NullPointerException e) { return 1; }  //  Сценарий выбора всех купюр из кассы в процессе проверок
    }

    private static void addAmount(int denomination, int count, Map<Integer, Integer> map)
    {
        if(map.containsKey(denomination))
            map.put(denomination, map.get(denomination) + count);
        else
            map.put(denomination,count);
    }

    private static void removeAmount(int denomination, int count, Map<Integer, Integer> map)
    {
        map.put(denomination, map.get(denomination) - count);
        if (map.get(denomination)==0)
            map.remove(denomination);
    }

}
