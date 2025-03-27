package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NumericalValueBasedAttributeComparatorTest {
    @Test
    public void testCompare() {
        NumericalValueBasedAttributeComparator comparator = new NumericalValueBasedAttributeComparator();
        Attribute attribute1 = new Attribute("Degree", "Computer Science");
        Attribute attribute2 = new Attribute("Degree", "42");
        Attribute attribute3 = new Attribute("Degree", "0.25");
        Attribute attribute4 = new Attribute("Degree", "Mathematics");
        assert(comparator.compare(attribute2, attribute3) > 0);
        assert(comparator.compare(attribute1, attribute2) > 0);
        assert(comparator.compare(attribute2, attribute1) < 0);
        assertEquals(-1, comparator.compare(attribute1, attribute4));
        assertEquals(-1, comparator.compare(attribute4, attribute1));

    }
}
