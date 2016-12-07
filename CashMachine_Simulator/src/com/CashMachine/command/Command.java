package CashMachine.command;

import CashMachine.exception.InterruptOperationException;

interface Command
{
    void execute() throws InterruptOperationException;
}
