package CashMachine;


public enum Currency
{
    UAH, USD, EUR;


    public static Currency getAllowableCurrencyByOrdinal(Integer i)
    {
        switch(i)
        {
            case 0: throw new IllegalArgumentException();
            case 1: return UAH;
            case 2: return USD;
            case 3: return EUR;
            default: throw new IllegalArgumentException();
        }
    }
    }
