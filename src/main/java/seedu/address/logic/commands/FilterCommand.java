package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.attribute.Attribute.CAPITALISATION_NOTE;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Attribute;
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
            + "Example: " + COMMAND_WORD + " a/major=Computer Science";
    public static final String MESSAGE_WARNING_DUPLICATE =
        "WARNING! There are duplicate attributes (i.e. name-value pairs). " + CAPITALISATION_NOTE;

    private final Set<Attribute> attribtues;
    private final boolean wasDuplicate;

    /**
     * Initializes an instance with the predicate comprised of attributes.
     *
     * @param filter The filtering predicate created with the user input attributes.
     * @param wasDuplicate Indicates whether there was a duplicate attribute in the user input.
     */
    public FilterCommand(Set<Attribute> attributes, boolean wasDuplicate) {
        requireNonNull(attributes);
        this.attribtues = attributes;
        this.wasDuplicate = wasDuplicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Set<Attribute> adjustedAttribtues =
            attribtues.stream()
                .map(attribute -> new Attribute(
                    model.findClosestAttributeName(attribute.getAttributeName())
                        .orElse(attribute.getAttributeName()),
                        model.findClosestAttributeValue(attribute.getAttributeValue())
                            .orElse(attribute.getAttributeValue())))
                            .collect(Collectors.toSet());

        AttributeMatchesPredicate filter = new AttributeMatchesPredicate(adjustedAttribtues);

        model.updateFilteredPersonList(filter);

        String message = String.format(Messages.MESSAGE_PERSONS_FILTERED_OVERVIEW,
            model.getFilteredPersonList().size());
        if (wasDuplicate) {
            message = MESSAGE_WARNING_DUPLICATE + "\n" + message;
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof FilterCommand otherFilterCommand) {
            return otherFilterCommand.attribtues.equals(this.attribtues)
                && otherFilterCommand.wasDuplicate == this.wasDuplicate;
        }
        return false;
    }
}
