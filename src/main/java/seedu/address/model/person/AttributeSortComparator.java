package seedu.address.model.person;

import java.util.Comparator;
import java.util.Optional;

import seedu.address.model.attribute.Attribute;

public class AttributeSortComparator implements Comparator<Person> {
    private final String attributeName;

    /**
     * Initializes an instance with an attribute.
     * The instance will be used to compare two Persons based on the given attribute name.
     *
     * @param attributeName The attribute name with which the candidates will be sorted.
     */
    public AttributeSortComparator(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public int compare(Person o1, Person o2) {
        if (o1.isSamePerson(o2)) {
            return 0;
        }
        Optional<Attribute> o1Attribute = o1.getAttribute(attributeName);
        Optional<Attribute> o2Attribute = o2.getAttribute(attributeName);
        return o2Attribute.map(value
                -> o1Attribute.map(attribute
                        -> attribute.compareToAttributeOfSameAttributeName(value))
                .orElse(1)).orElse(-1);

    }
}
