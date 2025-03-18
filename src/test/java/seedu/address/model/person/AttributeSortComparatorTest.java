package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

public class AttributeSortComparatorTest {
    private AttributeSortComparator comparator;
    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;

    @BeforeEach
    public void setUp() {
        String attributeName = "Graduation Year";
        comparator = new AttributeSortComparator(attributeName);

        person1 = new PersonBuilder().withAttributes(attributeName, "2027").build();
        person2 = new PersonBuilder().withAttributes(attributeName, "2028").build();
        person3 = new PersonBuilder().withAttributes("GPA", "4.5").build();
        person4 = new PersonBuilder().withAttributes("Location", "Singapore").build();
    }

    @Test
    public void compare_secondPersonHasAttribute_firstPersonDoesNot_returnsPositive() {
        assertEquals(1, comparator.compare(person3, person2));
    }

    @Test
    public void compare_firstPersonHasAttribute_SecondPersonDoesNot_returnsNegative() {
        assertEquals(-1, comparator.compare(person1, person4));
    }

    @Test
    public void compare_bothDoNotHaveAttribute_returnsZero() {
        assertEquals(-1, comparator.compare(person3, person4)); // Matches your logic
    }

    @Test
    public void compare_attributesDifferent_returnsCorrectComparison() {
        assertEquals(-1, comparator.compare(person1, person2));
        assertEquals(1, comparator.compare(person2, person1));
    }
}
