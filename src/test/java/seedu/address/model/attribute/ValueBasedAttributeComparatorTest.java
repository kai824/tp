package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ValueBasedAttributeComparatorTest {
    @Test
    public void testCompare() {
        ValueBasedAttributeComparator comparator = new ValueBasedAttributeComparator();
        Attribute attribute1 = new Attribute("Major", "Computer Science");
        Attribute attribute2 = new Attribute("Major", "Mathematics");
        Attribute attribute3 = new Attribute("Major", "Computer Science");
        assert(comparator.compare(attribute1, attribute2) < 0);
        assertEquals(0, comparator.compare(attribute1, attribute3));
    }

}
