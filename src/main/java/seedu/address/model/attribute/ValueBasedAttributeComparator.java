package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

/**
 * Compares two attributes based on lexicographical order of the values.
 */
public class ValueBasedAttributeComparator implements Comparator<Attribute> {
    @Override
    public int compare(Attribute o1, Attribute o2) {
        requireNonNull(o1);
        requireNonNull(o2);
        return o1.compareToAttributeOfSameAttributeNameByDefaultValue(o2);
    }
}
