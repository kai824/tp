package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Removes an attribute from a person in the address book.
 */
public class AttributeRemoveCommand extends Command {

    public static final String COMMAND_WORD = "remove-attribute";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the attribute of the person identified by name.\n"
            + "Parameters: n/ [NAME] w/ [ATTRIBUTE_NAME]\n"
            + "Example: " + COMMAND_WORD + "n/ Alex Yeoh w/graduation year";

    private final Name candidateName;
    private final String attributeName;

    /**
     * @param candidateName name of the person to be edited
     * @param attributeName name of the attribute to be deleted
     */
    public AttributeRemoveCommand(Name candidateName, String attributeName) {
        requireAllNonNull(candidateName, attributeName);

        this.candidateName = candidateName;
        this.attributeName = attributeName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person matchedPerson = null;

        for (Person person: lastShownList) {
            if (person.getName().equals(this.candidateName)) {
                matchedPerson = person;
                break;
            }
        }

        if (matchedPerson == null) {
            throw new CommandException("No person with name " + this.candidateName + " found!");
        }

        Attribute matchingAttribute = matchedPerson.getAttribute(this.attributeName).orElseThrow(()
                -> new CommandException("Person has no matching attribute with name " + this.attributeName)
        );
        matchedPerson.removeAttribute(matchingAttribute);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(matchingAttribute.toString() + "was deleted successfully.");
    }
}
