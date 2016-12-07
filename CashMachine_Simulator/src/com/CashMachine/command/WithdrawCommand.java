package CashMachine.command;

import CashMachine.*;
import CashMachine.exception.InterruptOperationException;
import CashMachine.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw", CashMachine.currentLocale);

    @Override
    public void execute() throws InterruptOperationException {
        Currency code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);

        int ammount;

        ConsoleHelper.writeMessage(res.getString("specify.amount"));
        while (true)
        {
            String s = ConsoleHelper.readString().trim();
            try
            {
                ammount = Integer.parseInt(s);
                if (ammount <= 0)
                    throw new Exception();
            }
            catch (Exception e)
            {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }

            if (!manipulator.isAmountAvailable(ammount))
            {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }

            try
            {
                Map<Integer, Integer> result = manipulator.withdrawAmount(ammount);
                ConsoleHelper.writeAnimatedMessage(res.getString("before"));
                ConsoleHelper.printAnimation();
                ConsoleHelper.writeAnimatedMessage(String.format(res.getString("success.format"), ammount, code));
                ConsoleHelper.writeAnimatedMessage(res.getString("cash"));
                for (Map.Entry<Integer, Integer> entry : result.entrySet())
                    ConsoleHelper.writeAnimatedMessage(entry.getKey() + " " + code + " - x" + entry.getValue());
                ConsoleHelper.writeMessage("\n");
                break;
            }
            catch (NotEnoughMoneyException e)
            {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }
    }
}
