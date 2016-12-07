package CashMachine.command;

import CashMachine.*;
import CashMachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class ExitCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit", CashMachine.currentLocale);

    @Override
    public void execute() throws InterruptOperationException {
        while (true)
        {
            ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
            String answer = ConsoleHelper.readString().trim().toLowerCase();
            if (res.getString("yes").equals(answer))
            {
                ConsoleHelper.writeMessage(res.getString("thank.message"));
                CashMachine.on = false;
                break;
            }
            else if (res.getString("no").equals(answer))
                break;
        }
    }
}
