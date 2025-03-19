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
    private final long numOfDistinctAttributeNames;
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
        this.numOfDistinctAttributeNames =
            attributes.stream().map(attribute -> attribute.attributeName).distinct().count();
    }

    @Override
    public boolean test(Person person) {
        long numOfMatchedAttributes =
            person.getAttributes().stream()
                .filter(attribute
                    -> attributes.stream().anyMatch(specifiedAttribute
                        -> specifiedAttribute.equals(attribute))).count();
        return numOfMatchedAttributes == numOfDistinctAttributeNames;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof AttributeMatchesPredicate otherPredicate) {
            return otherPredicate.attributes.equals(this.attributes);
        }
        return false;
    }
}
