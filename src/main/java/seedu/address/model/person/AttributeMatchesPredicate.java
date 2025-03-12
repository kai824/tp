package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.attribute.Attribute;

/**
 * Tests that a person has all of the designated attributes.
 */
public class AttributeMatchesPredicate implements Predicate<Person> {
    private final List<Attribute> attributes;

    /**
     * @param attributes The pairs of attribute name and value with which the candidates will be filtered.
     */
    public AttributeMatchesPredicate(List<Attribute> attributes) {
        requireNonNull(attributes);
        assert attributes.size() > 0;
        this.attributes = attributes;
    }

    @Override
    public boolean test(Person person) {
        return attributes.stream()
                .allMatch(testAttribute
                    -> person.getAttributes().stream().anyMatch(personAttribute
                        -> personAttribute.equals(testAttribute)));
    }
}
