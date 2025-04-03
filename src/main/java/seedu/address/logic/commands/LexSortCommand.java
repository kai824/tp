package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import seedu.address.model.Model;
import seedu.address.model.attribute.ValueBasedAttributeComparator;
import seedu.address.model.person.AttributeBasedPersonComparator;

/**
 * Sort the candidates with the provided attribute.
 * Guarantees: immutable.
 */
public class LexSortCommand extends SortCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the candidates in lexicographically ascending order "
            + "based on the value of the specified attribute.\n"
            + "Candidates lacking the specified attribute name "
            + "will be placed last, preserving their original order.\n"
            + "Parameters: ATTRIBUTE_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ATTRIBUTE + "Location";

    /**
     * Initializes an instance with the comparator based on the attribute name.
     *
     * @param attributeName The name of the attribute to sort the user input.
     */
    public LexSortCommand(String attributeName, boolean isAscending) {
        super(attributeName, isAscending);
    }

    @Override
    public AttributeBasedPersonComparator getComparator(boolean isAscending) {
        return new AttributeBasedPersonComparator(
            this.adjustedAttributeName.orElse(attributeName), new ValueBasedAttributeComparator(isAscending));
    }

    @Override
    public String getWarningMessage(Model model) {
        return super.getWarningMessage(model);
    }
}
