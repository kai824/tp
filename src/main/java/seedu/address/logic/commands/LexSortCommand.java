package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import seedu.address.logic.commands.exceptions.CommandException;
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
            + ": Sort the candidates in lexicographically ascending order based on the value of the specified attribute.\n"
            + "Candidates lacking the specified attribute name will be placed last, preserving their original order.\n"
            + "Parameters: ATTRIBUTE_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ATTRIBUTE + "Graduation Year";
    private final String attributeName;
    private String adjustedAttributeName;

    /**
     * Initializes an instance with the comparator based on the attribute name.
     *
     * @param attributeName The name of the attribute to sort the user input.
     */
    public LexSortCommand(String attributeName) {
        requireNonNull(attributeName);
        this.attributeName = attributeName;
        this.adjustedAttributeName = attributeName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        this.adjustedAttributeName = model.findClosestAttributeName(this.attributeName).orElse(this.attributeName);
        return super.execute(model);
    }

    @Override
    public AttributeBasedPersonComparator getComparator() {
        return new AttributeBasedPersonComparator(this.adjustedAttributeName, new ValueBasedAttributeComparator());
    }

    @Override
    public String getWarningMessage() {
        return "";
    }

    @Override
    public boolean equals(Object other) {
        requireNonNull(other);
        if (other instanceof LexSortCommand) {
            return this.attributeName.equals(((LexSortCommand) other).attributeName);
        }
        return false;
    }
}
