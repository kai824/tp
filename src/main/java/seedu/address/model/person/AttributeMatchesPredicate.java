package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.attribute.Attribute;

/**
 * Tests if a Person has all of the designated attributes.
 * Guarantees: immutable.
 */
public class AttributeMatchesPredicate implements Predicate<Person> {
    private final Set<Attribute> attributes;

    /**
     * Initializes an instance with the list of attributes.
     * The instance will be used to check if a Person meets ALL the given attributes.
     *
     * @param attributes The pairs of attribute name and value with which the candidates will be filtered.
     */
    public AttributeMatchesPredicate(Set<Attribute> attributes) {
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
