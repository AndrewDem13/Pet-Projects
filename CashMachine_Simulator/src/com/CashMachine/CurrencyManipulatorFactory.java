package CashMachine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class CurrencyManipulatorFactory
{
    static Map<Currency, CurrencyManipulator> manipulators = new HashMap<>();

    public static CurrencyManipulator getManipulatorByCurrencyCode(Currency currencyCode)
    {
        if (manipulators.containsKey(currencyCode))
            return manipulators.get(currencyCode);
        else
        {
            CurrencyManipulator current = new CurrencyManipulator(currencyCode);
            manipulators.put(currencyCode, current);
            return current;
        }
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return manipulators.values();
    }

    private CurrencyManipulatorFactory() { }

}
