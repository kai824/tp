package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

/**
 * Compares two attributes based on lexicographical order of the values.
 */
public class ValueBasedAttributeComparator implements Comparator<Attribute> {
    private boolean isAscending;

    public ValueBasedAttributeComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Attribute o1, Attribute o2) {
        requireNonNull(o1);
        requireNonNull(o2);
        assert(o1.matchesName(o2.getAttributeName()));
        return o1.compareToSameNameAttributeDefault(o2, this.isAscending);
    }
}
