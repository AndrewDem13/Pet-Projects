package CashMachine;

import CashMachine.exception.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ConsoleHelper
{
    public static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common", CashMachine.currentLocale);

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println("\n"+message);
    }

    public static void writeAnimatedMessage(String message) {
        System.out.print("\n"+message);
    }

    public static void printAnimation() {
        try
        {
            Thread.sleep(200);
            System.out.print(" ");
            for (int i = 0; i < 3; i++)
            {
                Thread.sleep(100);
                System.out.print(".");
                Thread.sleep(200);
                System.out.print(" ");
            }
            Thread.sleep(100);
        }
            catch (InterruptedException e) {}
        System.out.println();
    }

    public static String readString() throws InterruptOperationException {
        String s = "";
        try
        {
            s = reader.readLine();
        }
        catch (IOException e) {}

        if (s.trim().equals("0"))
            throw new InterruptOperationException();

        return s;
    }

    public static Currency askCurrencyCode() throws InterruptOperationException {
        Currency currency;
        int ordinal;

        writeMessage(res.getString("choose.currency.code"));
        while (true)
        {
            try
            {
                ordinal = Integer.parseInt(readString().trim());
                currency = Currency.getAllowableCurrencyByOrdinal(ordinal);
                return currency;
            }
            catch (InterruptOperationException ie)
            {
                throw new InterruptOperationException();
            }
            catch (Exception e)
                {
                    writeMessage(res.getString("invalid.data"));
                }
        }
    }

    public static int[] getValidTwoDigits(Currency currencyCode) throws InterruptOperationException {
        String[] result;
        String s = "";
        int i, j;
        Integer[] denominations = new Integer[9];
        ArrayList<Integer> array;

        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
        while(true)
        {
            s = readString();
            result = s.trim().split(" ");

            try
            {
                i = Integer.parseInt(result[0]);
                j = Integer.parseInt(result[1]);
            }
            catch (Exception e)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }

            if (i <= 0 || j <= 0 || result.length > 2)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }

            // checking nominals by currency code
            if (currencyCode==Currency.UAH)
                denominations = new Integer[] {1, 2, 5, 10, 20, 50, 100, 200, 500};
            else if (currencyCode==Currency.USD)
                denominations = new Integer[] { 1, 2, 5, 10, 20, 50, 100 };
            else if (currencyCode==Currency.EUR)
                denominations = new Integer[] { 5, 10, 20, 50, 100, 200, 500 };

            array = new ArrayList<>(Arrays.asList(denominations));
            if (!array.contains(i))
            {
                writeMessage(res.getString("invalid.denomination"));
                continue;
            }

            break;
        }
        return new int[] {i, j};
    }

    public static Operation askOperation() throws InterruptOperationException {
        String s = "";
        writeMessage(res.getString("choose.operation") + "\n" +
                "1 - " + res.getString("operation.INFO") + "\n" +
                "2 - " + res.getString("operation.DEPOSIT") + "\n" +
                "3 - " + res.getString("operation.WITHDRAW") + "\n" +
                "4 - " + res.getString("operation.EXIT"));
        while (true)
        {
            s = readString().trim();
            try
            {
                return Operation.getAllowableOperationByOrdinal(Integer.parseInt(s));
            }
            catch (Exception e)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
        }
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }
}
