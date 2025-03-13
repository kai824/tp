package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttributeMatchesPredicate;

/**
 * Filter the candidates with the provided attribute.
 * Guarantees: immutable.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filter the candidates with the provided attribute.\n"
            + "Parameters: ATTRIBUTE_NAME=ATTRIBUTE_VALUE (case-sensitive, can be more than one)\n"
            + "Example: " + COMMAND_WORD + "a/major=Computer Science";
    private final AttributeMatchesPredicate filter;

    /**
     * Initializes an instance with the predicate comprised of attributes.
     *
     * @param filter The filtering predicate created with the user input attributes.
     */
    public FilterCommand(AttributeMatchesPredicate filter) {
        requireNonNull(filter);
        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(filter);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_FILTERED_OVERVIEW,
                    model.getFilteredPersonList().size()));
    }
}
