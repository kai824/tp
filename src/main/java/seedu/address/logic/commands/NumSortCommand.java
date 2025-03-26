package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.Optional;

import seedu.address.model.Model;
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
    public static final String MESSAGE_WARNING_MISSING_NUMERICALS =
            "WARNING! Only entries up to index %1$d have valid numerical values for the specified attribute.\n";


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
    public String getWarningMessage(Model model) {
        Optional<Long> count = model.numOfPersonsWithNumericalValue(this.adjustedAttributeName);
        return count.map(val -> String.format(MESSAGE_WARNING_MISSING_NUMERICALS, val)).orElse("");
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
