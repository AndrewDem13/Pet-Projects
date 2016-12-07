package CashMachine.command;

import CashMachine.*;
import CashMachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class LoginCommand implements Command
{
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login", CashMachine.currentLocale);

    @Override
    public void execute() throws InterruptOperationException {
        String number, pin;

        ConsoleHelper.writeMessage(res.getString("specify.data"));
        while (true)
        {
            number = ConsoleHelper.readString().trim();

            if ("test".equals(number))
            {
                ConsoleHelper.writeMessage(res.getString("test.mode"));
                break;
            }

            pin = ConsoleHelper.readString().trim();

            if (number.length()!=12 || pin.length()!=4)
            {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                continue;
            }

            ConsoleHelper.writeAnimatedMessage(res.getString("before"));
            ConsoleHelper.printAnimation();

            if (validCreditCards.containsKey(number) && validCreditCards.getString(number).equals(pin))
            {
                ConsoleHelper.writeMessage(String.format("\n" + res.getString("success.format") + "\n\n\n", number));
                break;
            }
            else
            {
                ConsoleHelper.writeMessage(String.format("\n" + res.getString("not.verified.format"), number));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            }
        }
    }
}
