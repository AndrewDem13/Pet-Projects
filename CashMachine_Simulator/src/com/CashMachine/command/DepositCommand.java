package CashMachine.command;

import CashMachine.*;
import CashMachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit", CashMachine.currentLocale);

    @Override
    public void execute() throws InterruptOperationException {
        Currency code = ConsoleHelper.askCurrencyCode();
        int[] nominalCount = ConsoleHelper.getValidTwoDigits(code);
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
        int banknote = nominalCount[0];
        int quantity = nominalCount[1];

        ConsoleHelper.writeAnimatedMessage(res.getString("before"));
        ConsoleHelper.printAnimation();
        manipulator.addAmount(banknote, quantity);
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), banknote*quantity, code) + "\n\n\n");
    }
}
