package seedu.address.model.person;

import java.util.Comparator;
import java.util.Optional;

import seedu.address.model.attribute.Attribute;

/**
 * Compare two persons based on the lexicographical order of the value of a specified attribute name.
 * Guarantees: immutable.
 */

public class AttributeBasedPersonComparator implements Comparator<Person> {
    private final String attributeName;
    private final Comparator<Attribute> attributeComparator;
    private enum SortType {
        LEXICOGRAPHICAL,
        NUMERICAL
    }

    /**
     * Initializes an instance with an attribute.
     * The instance will be used to compare two Persons based on the given attribute name.
     *
     * @param attributeName The attribute name with which the candidates will be sorted.
     */
    public AttributeBasedPersonComparator(String attributeName, Comparator<Attribute> attributeComparator) {
        this.attributeName = attributeName;
        this.attributeComparator = attributeComparator;
    }

    @Override
    public int compare(Person o1, Person o2) {
        Optional<Attribute> o1Attribute = o1.getAttribute(attributeName);
        Optional<Attribute> o2Attribute = o2.getAttribute(attributeName);
        if (o2Attribute.isEmpty()) {
            return -1;
        }
        if (o1Attribute.isEmpty()) {
            return 1;
        }
        return attributeComparator.compare(o1Attribute.get(), o2Attribute.get());
    }
}
