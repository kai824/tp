package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.attribute.ValueBasedAttributeComparator;
import seedu.address.testutil.PersonBuilder;

public class AttributeBasedPersonComparatorTest {
    private AttributeBasedPersonComparator ascendingComparator;
    private AttributeBasedPersonComparator descendingComparator;
    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;

    @BeforeEach
    public void setUp() {
        String attributeName = "Graduation Year";
        ascendingComparator = new AttributeBasedPersonComparator(attributeName,
                new ValueBasedAttributeComparator(true));
        descendingComparator = new AttributeBasedPersonComparator(attributeName,
                new ValueBasedAttributeComparator(false));

        person1 = new PersonBuilder().withAttributes(attributeName, "2027").build();
        person2 = new PersonBuilder().withAttributes(attributeName, "2028").build();
        person3 = new PersonBuilder().withAttributes("GPA", "4.5").build();
        person4 = new PersonBuilder().withAttributes("Location", "Singapore").build();
    }

    @Test
    public void secondPersonHasAttribute_firstPersonDoesNot_returnsPositive() {
        assertEquals(1, ascendingComparator.compare(person3, person2));
        assertEquals(1, descendingComparator.compare(person3, person2));
    }

    @Test
    public void firstPersonHasAttribute_secondPersonDoesNot_returnsNegative() {
        assertEquals(-1, ascendingComparator.compare(person1, person4));
        assertEquals(-1, descendingComparator.compare(person1, person4));
    }

    @Test
    public void compare_bothDoNotHaveAttribute_returnsNegative() {
        assertEquals(-1, ascendingComparator.compare(person3, person4));
        assertEquals(-1, descendingComparator.compare(person3, person4));
    }

    @Test
    public void compare_attributesDifferent_returnsCorrectComparison() {
        assertEquals(-1, ascendingComparator.compare(person1, person2));
        assertEquals(1, ascendingComparator.compare(person2, person1));
        assertEquals(1, descendingComparator.compare(person1, person2));
        assertEquals(-1, descendingComparator.compare(person2, person1));
    }
}
