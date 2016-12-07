package CashMachine.command;

import CashMachine.*;
import CashMachine.exception.InterruptOperationException;

import java.util.Collection;
import java.util.ResourceBundle;


class InfoCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info", CashMachine.currentLocale);

    @Override
    public void execute() throws InterruptOperationException {
        Collection<CurrencyManipulator> manipulatorMap = CurrencyManipulatorFactory.getAllCurrencyManipulators();

        if (manipulatorMap.isEmpty())
            ConsoleHelper.writeMessage(res.getString("no.money") + "\n\n\n");
        else
        {
            ConsoleHelper.writeMessage(res.getString("before"));
            for (CurrencyManipulator manipulator : manipulatorMap)
            {
                if (manipulator.hasMoney() && manipulator.getTotalAmount() > 0)
                {
                    ConsoleHelper.writeAnimatedMessage(manipulator.getCurrencyCode() + " - " + manipulator.getTotalAmount());
                }
                else
                    ConsoleHelper.writeMessage(res.getString("no.money") + "\n\n\n");
            }
            ConsoleHelper.writeMessage("\n\n");
        }
    }
}
