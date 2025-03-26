package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttributeBasedPersonComparator;

/**
 * Represents a sort command that sorts the list of persons based on a specified attribute comparator.
 * Subclasses should provide the appropriate comparator and any relevant warning message.
 */
public abstract class SortCommand extends Command {
    protected final String attributeName;
    protected String adjustedAttributeName;

    /**
     * Initializes an instance with the comparator based on the attribute name.
     *
     * @param attributeName The name of the attribute to sort the user input.
     */
    public SortCommand(String attributeName) {
        requireNonNull(attributeName);
        this.attributeName = attributeName;
        this.adjustedAttributeName = attributeName;
    }

    /**
     * Returns the Comparator to use for this sort command
     */
    public abstract AttributeBasedPersonComparator getComparator();

    /**
     * Returns the warning message produced by this sort command.
     * If there is no warning, an empty String is returned.
     */
    public abstract String getWarningMessage(Model model);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        this.adjustedAttributeName = model.findClosestAttributeName(this.attributeName).orElse(this.attributeName);
        model.sortFilteredPersonList(this.getComparator());
        String message = this.getWarningMessage(model) + Messages.MESSAGE_PERSONS_SORTED_OVERVIEW;
        return new CommandResult(message);

    }
}
