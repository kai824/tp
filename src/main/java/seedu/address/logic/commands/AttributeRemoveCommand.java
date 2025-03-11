package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AttributeRemoveCommand extends Command {

    public static final String COMMAND_WORD = "remove-attribute";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the attribute of the person identified by name.\n"
            + "Parameters: n/ [NAME] w/ [ATTRIBUTE_NAME]\n"
            + "Example: " + COMMAND_WORD + "n/ Alex Yeoh w/graduation year";

    private final String candidateName;
    private final String attributeName;

    /**
     * @param
     */
    public AttributeRemoveCommand(String candidateName, String attributeName) {
        requireAllNonNull(candidateName, attributeName);

        this.candidateName = candidateName;
        this.attributeName = attributeName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // TODO find candidate and remove attribute
        return new CommandResult("Success!");
    }
}
