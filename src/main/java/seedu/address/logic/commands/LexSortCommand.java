package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttributeSortComparator;

/**
 * Sort the candidates with the provided attribute.
 * Guarantees: immutable.
 */
public class LexSortCommand extends SortCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the candidates in lexicographical order based on the value of the specified attribute.\n"
            + "Candidates lacking the specified attribute name will be placed last, preserving their original order.\n"
            + "Parameters: ATTRIBUTE_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ATTRIBUTE + "Graduation Year";

    @Override
    public AttributeSortComparator getComparator() {
        return new AttributeSortComparator(this.attributeName);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof LexSortCommand) {
            return this.attributeName.equals(((LexSortCommand) other).attributeName);
        }
        return false;
    }
}
