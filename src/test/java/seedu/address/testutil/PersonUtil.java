package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        person.getTags().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        person.getAttributes().forEach(
            a -> sb.append(PREFIX_ATTRIBUTE + a.getCaseAwareAttributeName() + "=" + a.getAttributeValue() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        if (descriptor.getUpdateAttributes().isPresent()) {
            Set<Attribute> updateAttributes = descriptor.getUpdateAttributes().get();
            if (updateAttributes.isEmpty()) {
                sb.append(PREFIX_ATTRIBUTE);
            } else {
                updateAttributes.forEach(a ->
                    sb.append(PREFIX_ATTRIBUTE).append(a.getCaseAwareAttributeName()).append("=")
                        .append(a.getAttributeValue()).append(" "));
            }
        }
        if (descriptor.getRemoveAttributes().isPresent()) {
            Set<String> removeAttributes = descriptor.getRemoveAttributes().get();
            if (removeAttributes.isEmpty()) {
                sb.append(PREFIX_REMOVE_ATTRIBUTE);
            } else {
                removeAttributes.forEach(s -> sb.append(PREFIX_REMOVE_ATTRIBUTE).append(s).append(" "));
            }
        }
        return sb.toString();
    }
}
