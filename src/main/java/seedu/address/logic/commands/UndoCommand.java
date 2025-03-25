package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the last Command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Last command successfully undone!";

    public static final String MESSAGE_NO_LAST_CHANGE = "There is no change to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.revertLastState()) {
            throw new CommandException(MESSAGE_NO_LAST_CHANGE);
        }
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
