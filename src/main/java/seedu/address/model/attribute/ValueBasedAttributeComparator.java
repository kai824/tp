package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

public class ValueBasedAttributeComparator implements Comparator<Attribute> {
    @Override
    public int compare(Attribute o1, Attribute o2) {
        requireNonNull(o1);
        requireNonNull(o2);
        return o1.compareToAttributeOfSameAttributeNameByDefaultValue(o2);
    }
}
