package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Updates an alias from an attribute name to a site link.
 */
public class AliasCommand extends Command {
    public static final String COMMAND_WORD = "link";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Associates a website domain with an attribute name.\n"
            + "Parameters: ATTRIBUTE_NAME=WEB_SITE_DOMAIN_LINK\n"
            + "Example: " + COMMAND_WORD + " a/GitHub=https://github.com/\n"
            + "To remove the correspondency, use '" + COMMAND_WORD + " ra/ATTRIBUTE_NAME' instead.";
    public static final String MESSAGE_ADD_ALIAS_SUCCESS =
        "Added the site link: the site domain %2$s is now associated with %1$s";
    public static final String MESSAGE_REMOVE_ALIAS_SUCCESS =
        "Removed the site link: currently no site link is associated with %1$s";
    public static final String MESSAGE_ACCEPT_ONE_ARGUMENT =
        "This command accepts exactly one argument, "
            + "which can be either a/ATTRIBUTE_NAME=WEB_SITE_DOMAIN_LINK or ra/ATTRIBUTE_NAME.";

    private final String attributeName;
    private final Optional<String> siteLink;

    /**
     * Initializes a command with a pair of an attribute name and a site link.
     *
     * @param attributeName The attribute name for which the alias mapping will be updated.
     * @param siteLink The site link that will be associated with the attribute name.
     *     If siteLink is empty, the current alias mapping for the attribute name will be removed instead.
     */
    private AliasCommand(String attributeName, Optional<String> siteLink) {
        requireNonNull(attributeName);
        requireNonNull(siteLink);
        this.attributeName = attributeName;
        this.siteLink = siteLink;
    }

    /**
     * Constructs a command that adds an alias from a attribute name to a site link.
     *
     * @param attributeName The attribute name for which the alias mapping will be added.
     * @param siteLink The site link that will be associated with the attribute name, which can be empty.
     */
    public static AliasCommand addAliasCommand(String attributeName, String siteLink) {
        return new AliasCommand(attributeName, Optional.of(siteLink));
    }

    /**
     * Constructs a command that removes an alias associated with a attribute name.
     *
     * @param attributeName The attribute name for which the alias mapping will be removed.
     */
    public static AliasCommand removeAliasCommand(String attributeName) {
        return new AliasCommand(attributeName, Optional.empty());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String message;
        model.updateAlias(attributeName, siteLink);
        if (siteLink.isPresent()) {
            message = String.format(MESSAGE_ADD_ALIAS_SUCCESS, attributeName, siteLink.get());
        } else {
            message = String.format(MESSAGE_REMOVE_ALIAS_SUCCESS, attributeName);
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
