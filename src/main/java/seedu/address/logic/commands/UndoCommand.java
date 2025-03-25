package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Undoes the last Command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Undo success!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_UNDO_SUCCESS, false, false);
    }
}
