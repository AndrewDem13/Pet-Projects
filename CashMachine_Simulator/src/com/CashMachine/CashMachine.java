package CashMachine;

import CashMachine.command.CommandExecutor;
import CashMachine.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

public class CashMachine
{
    public static final String RESOURCE_PATH = "CashMachine.resources.";
    public static boolean on = true;
    public static Locale currentLocale = Locale.ENGLISH;


    public static void main(String[] args) throws InterruptOperationException {
        language();

        Operation operation;

        try
        {
           CommandExecutor.execute(Operation.LOGIN);
            do
            {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            while (on);
        }
        catch (InterruptOperationException e)
        {
            ConsoleHelper.printExitMessage();
        }
    }

    private static void language() {
        ConsoleHelper.writeMessage("1 - English language \n2 - Русский язык");
        int i;
        while (true)
        {
            try
            {
                i = Integer.parseInt(ConsoleHelper.readString());
                if (i == 1 || i == 2)
                    break;
            }
            catch (Exception e) { }
        }
        if (i == 2)
        {
            currentLocale = new Locale("ru", "RU");
            ConsoleHelper.res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common", currentLocale);
        }
    }
}
