package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Undoes the last Command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Undo is executed successfully!";

    public static final String PREV_ADDR_BOOK_UNAVAILABLE = "Previous address book unavailable!";

    @Override
    public CommandResult execute(Model model) {
        return CommandResult.createUndoResult(MESSAGE_UNDO_SUCCESS);
    }
}
