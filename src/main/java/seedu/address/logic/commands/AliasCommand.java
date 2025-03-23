package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds an alias from an attribute name to a site link.
 */
public class AliasCommand extends Command {
    public static final String COMMAND_WORD = "link";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Associates a website domain with an attribute name.\n"
            + "Parameters: ATTRIBUTE_NAME=WEB_SITE_DOMAIN_LINK\n"
            + "Example: " + COMMAND_WORD + " a/GitHub=https://github.com/";
    public static final String MESSAGE_ADD_ALIAS_SUCCESS =
        "Added the site link: the site domain %2$s is now associated with %1$s";
    public static final String MESSAGE_REMOVE_ALIAS_SUCCESS =
        "Removed the site link: currently no site link is associated with %1$s";

    private final String attributeName;
    private final String siteLink;

    /**
     * Initializes a command with a pair of an attribute name and a site link.
     *
     * @param attributeName The attribute name for which the alias mapping will be updated.
     * @param siteLink The site link that will be associated with the attribute name.
     *     If siteLink is empty, the current alias mapping for the attribute name will be removed instead.
     */
    public AliasCommand(String attributeName, String siteLink) {
        requireNonNull(attributeName);
        requireNonNull(siteLink);
        this.attributeName = attributeName;
        this.siteLink = siteLink;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String message;
        if (siteLink.isEmpty()) {
            model.updateAlias(attributeName, Optional.empty());
            message = String.format(MESSAGE_REMOVE_ALIAS_SUCCESS, attributeName);
        } else {
            model.updateAlias(attributeName, Optional.of(siteLink));
            message = String.format(MESSAGE_ADD_ALIAS_SUCCESS, attributeName, siteLink);
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof AliasCommand otherFilterCommand) {
            return otherFilterCommand.attributeName.equals(this.attributeName)
                && otherFilterCommand.siteLink.equals(this.siteLink);
        }
        return false;
    }
}
