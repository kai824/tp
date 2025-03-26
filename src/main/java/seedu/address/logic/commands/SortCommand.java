package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttributeBasedPersonComparator;

public abstract class SortCommand extends Command {
    /**
     * Returns the Comparator to use for this sort command
     */
    public abstract AttributeBasedPersonComparator getComparator();

    /**
     * Returns the warning message produced by this sort command.
     * If there is no warning, an empty String is returned.
     */
    public abstract String getWarningMessage();

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortFilteredPersonList(this.getComparator());

        String message = this.getWarningMessage() + String.format(Messages.MESSAGE_PERSONS_SORTED_OVERVIEW);

        return new CommandResult(message);

    }
}
