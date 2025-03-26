package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import seedu.address.model.attribute.NumericalValueBasedAttributeComparator;
import seedu.address.model.person.AttributeBasedPersonComparator;

/**
 * Sort the candidates with the provided attribute.
 * Guarantees: immutable.
 */
public class NumSortCommand extends SortCommand {
    public static final String COMMAND_WORD = "sort-num";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the candidates in ascending order based on numerical value of the specified attribute.\n"
            + "Candidates lacking the specified attribute name will be placed last, preserving their original order.\n"
            + "Among Candidates with the specified attribute name, "
            + "./gthose without a numerical value will be placed last.\n"
            + "Parameters: ATTRIBUTE_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ATTRIBUTE + "Graduation Year";

    /**
     * Initializes an instance with the comparator based on the attribute name.
     *
     * @param attributeName The name of the attribute to sort the user input.
     */
    public NumSortCommand(String attributeName) {
        super(attributeName);
    }

    @Override
    public AttributeBasedPersonComparator getComparator() {
        return new AttributeBasedPersonComparator(this.attributeName, new NumericalValueBasedAttributeComparator());
    }

    @Override
    public String getWarningMessage() {
        //TODO
        return "";
    }

    @Override
    public boolean equals(Object other) {
        requireNonNull(other);
        if (other instanceof NumSortCommand) {
            return this.attributeName.equals(((NumSortCommand) other).attributeName);
        }
        return false;
    }
}
