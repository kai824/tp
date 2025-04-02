package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.AutoCorrectionUtil;
import seedu.address.model.person.AttributeBasedPersonComparator;

/**
 * Represents a sort command that sorts the list of persons based on a specified attribute comparator.
 * Subclasses should provide the appropriate comparator and any relevant warning message.
 */
public abstract class SortCommand extends Command {
    private static final String MESSAGE_WARNING_MISSING_ATTRIBUTE =
            "WARNING! Only entries up to index %1$d have the specified attribute name.\n";

    protected final String attributeName;
    protected Optional<String> adjustedAttributeName;

    /**
     * Initializes an instance with the comparator based on the attribute name.
     *
     * @param attributeName The name of the attribute to sort the user input.
     */
    public SortCommand(String attributeName) {
        requireNonNull(attributeName);
        this.attributeName = attributeName;
    }

    /**
     * Returns the Comparator to use for this sort command
     */
    public abstract AttributeBasedPersonComparator getComparator();

    /**
     * Returns the warning message produced by this sort command.
     * If there is no warning, an empty String is returned.
     */
    public String getWarningMessage(Model model) {
        Optional<String> missingAttributeWarning =
            AutoCorrectionUtil.warningForName(attributeName, adjustedAttributeName);
        if (missingAttributeWarning.isPresent()) { //No entry has the specified attribute name
            return missingAttributeWarning.get() + "\n";
        }
        Optional<Long> count = model.numOfPersonsWithAttribute(this.adjustedAttributeName.orElse(attributeName));
        return count.map(val -> String.format(MESSAGE_WARNING_MISSING_ATTRIBUTE, val)).orElse("");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        this.adjustedAttributeName = model.autocorrectAttributeName(this.attributeName);
        model.sortFilteredPersonList(this.getComparator());
        String message = this.getWarningMessage(model) + Messages.MESSAGE_PERSONS_SORTED_OVERVIEW;
        return new CommandResult(message);

    }
}
