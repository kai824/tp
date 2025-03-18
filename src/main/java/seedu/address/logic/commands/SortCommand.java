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
            + ": Sort the candidates with the provided attribute provided attribute name."
            + "Candidates lacking the specified attribute name will be placed last, preserving their original order.\n"
            + "Parameters: ATTRIBUTE_NAME (case-sensitive)\n"
            + "Example: " + COMMAND_WORD + "a/Graduation Year";
    private final AttributeSortComparator sorter;

    /**
     * Initializes an instance with the comparator based on the attribute name.
     *
     * @param sorter The comparator created with the user input attribute name.
     */
    public SortCommand(AttributeSortComparator sorter) {
        requireNonNull(sorter);
        this.sorter = sorter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateSortedPersonList(sorter);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_SORTED_OVERVIEW
                ));
    }
}
