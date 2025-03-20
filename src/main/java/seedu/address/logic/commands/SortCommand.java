package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttributeSortComparator;

/**
 * Sort the candidates with the provided attribute.
 * Guarantees: immutable.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the candidates with the provided attribute provided attribute name. "
            + "Candidates lacking the specified attribute name will be placed last, preserving their original order.\n"
            + "Parameters: ATTRIBUTE_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " a/Graduation Year";
    private final String attributeName;

    /**
     * Initializes an instance with the comparator based on the attribute name.
     *
     * @param attributeName The name of the attribute to sort the user input.
     */
    public SortCommand(String attributeName) {
        requireNonNull(attributeName);
        this.attributeName = attributeName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortFilteredPersonList(new AttributeSortComparator(this.attributeName));

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_SORTED_OVERVIEW
                ));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SortCommand) {
            return this.attributeName.equals(((SortCommand) other).attributeName);
        }
        return false;
    }
}
