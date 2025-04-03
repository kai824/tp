package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_NAME_GRAD_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_NAME_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_ALT_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_GRAD_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_MAJOR;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.attribute.Attribute;
import seedu.address.testutil.PersonBuilder;

public class AttributeMatchesPredicateTest {
    private Attribute major1 = new Attribute(VALID_ATTRIBUTE_NAME_MAJOR.toLowerCase(), VALID_ATTRIBUTE_VALUE_MAJOR);
    private Attribute major2 = new Attribute(VALID_ATTRIBUTE_NAME_MAJOR.toUpperCase(), VALID_ATTRIBUTE_VALUE_ALT_MAJOR);
    private Attribute year = new Attribute(VALID_ATTRIBUTE_NAME_GRAD_YEAR, VALID_ATTRIBUTE_VALUE_GRAD_YEAR);
    private Attribute university = new Attribute("University", "NUS");
    private Attribute hobby = new Attribute("Hobby", "Baseball");
    private Attribute cca = new Attribute("CCA", "Dance");

    private Person buildPersonWithAttributes(Attribute... attributes) {
        String[] parameters =
            Arrays.stream(attributes)
            .flatMap(attribute
                -> List.of(attribute.getCaseAwareAttributeName(), attribute.getAttributeValue()).stream())
            .toArray(String[]::new);
        return new PersonBuilder().withAttributes(parameters).build();
    }

    @Test
    public void test_attributeMatches_returnsTrue() {
        // One attribute
        AttributeMatchesPredicate predicate =
            new AttributeMatchesPredicate(Set.of(major1));
        assertTrue(predicate.test(buildPersonWithAttributes(major1)));

        // Multiple attributes
        predicate = new AttributeMatchesPredicate(
            Set.of(university, hobby));
        assertTrue(predicate.test(buildPersonWithAttributes(university, hobby)));

        // Person has extra attributes
        predicate = new AttributeMatchesPredicate(
            Set.of(cca, year));
        assertTrue(predicate.test(buildPersonWithAttributes(cca, hobby, year)));

        // Mixed-case attributes: name
        predicate =
                new AttributeMatchesPredicate(Set.of(university));
        assertTrue(predicate.test(new PersonBuilder().withAttributes("university", "NUS").build()));

        // Two attribute values in the same attribute name: one of the values matches the person
        predicate =
            new AttributeMatchesPredicate(Set.of(major1, major2));
        assertTrue(predicate.test(new PersonBuilder()
            .withAttributes(VALID_ATTRIBUTE_NAME_MAJOR, VALID_ATTRIBUTE_VALUE_MAJOR).build()));
        assertTrue(predicate.test(new PersonBuilder()
            .withAttributes(VALID_ATTRIBUTE_NAME_MAJOR.toUpperCase(), VALID_ATTRIBUTE_VALUE_MAJOR).build()));
        assertTrue(predicate.test(new PersonBuilder()
            .withAttributes(VALID_ATTRIBUTE_NAME_MAJOR.toLowerCase(), VALID_ATTRIBUTE_VALUE_MAJOR).build()));
    }

    @Test
    public void test_attributeMatches_returnsFalse() {
        // Mixed-case attributes: value
        AttributeMatchesPredicate predicate =
            new AttributeMatchesPredicate(Set.of(university));
        assertFalse(predicate.test(new PersonBuilder().withAttributes("University", "nus").build()));

        // Non-matching attributes: value
        predicate = new AttributeMatchesPredicate(
            Set.of(university, hobby));
        assertFalse(predicate.test(new PersonBuilder()
            .withAttributes("University", "NUS", "Hobby", "Softball").build()));

        // Non-matching attributes: name
        predicate = new AttributeMatchesPredicate(
            Set.of(university, hobby));
        assertFalse(predicate.test(new PersonBuilder()
            .withAttributes("University", "NUS", "CCA", "Baseball").build()));

        // No attribute
        predicate = new AttributeMatchesPredicate(
            Set.of(year));
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Same name but different value
        predicate = new AttributeMatchesPredicate(
            Set.of(university));
        assertFalse(predicate.test(new PersonBuilder()
            .withAttributes("University", "NTU").build()));

        // Name and value are swapped
        predicate =
            new AttributeMatchesPredicate(Set.of(new Attribute("fuga", "hoge")));
        assertFalse(predicate.test(new PersonBuilder().withAttributes("hoge", "fuga").build()));

        // Attributes match name, phone, email and address, but does not match the attributes
        predicate = new AttributeMatchesPredicate(
            Set.of(new Attribute("Alice", "12345"),
                new Attribute("alice@email.com", "Hobby")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAttributes("Hobby", "Baseball").build()));

        // Two attribute values in the same attribute name: neither of the two matches the person
        predicate =
            new AttributeMatchesPredicate(Set.of(major1, major2));
        assertFalse(predicate.test(new PersonBuilder()
            .withAttributes(VALID_ATTRIBUTE_NAME_MAJOR, VALID_ATTRIBUTE_VALUE_GRAD_YEAR).build()));

        // Two attribute values in the same attribute name: the person does not have any attribute
        predicate =
            new AttributeMatchesPredicate(Set.of(major1, major2));
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_attributeMatches_assertions() {
        AttributeMatchesPredicate predicate = new AttributeMatchesPredicate(Set.of(major1));
        Person person = new PersonBuilder().withAttributes("A", "B", "a", "D").build();
        // A person has a duplicate -> assertion
        assertThrows(AssertionError.class,
            "Person should not have duplicates in attribute names", () -> predicate.test(person));
        // A person does not have a duplicate -> no assertion
        Person person2 = new PersonBuilder().withAttributes("A", "B", "a", "B").build();
        assertFalse(predicate.test(person2));
    }

    @Test
    public void equals() {
        AttributeMatchesPredicate majorYear =
            new AttributeMatchesPredicate(Set.of(major1, year));
        AttributeMatchesPredicate yearMajor =
            new AttributeMatchesPredicate(Set.of(year, major1));
        AttributeMatchesPredicate majorA = new AttributeMatchesPredicate(Set.of(major1));
        AttributeMatchesPredicate majorB = new AttributeMatchesPredicate(Set.of(major2));

        // same objects -> returns true
        assertTrue(majorYear.equals(majorYear));
        // same values -> returns true
        assertTrue(majorYear.equals(yearMajor));
        // different types -> returns false
        assertFalse(majorA.equals(100));
        // different values -> returns false
        assertFalse(majorA.equals(majorB));
        assertFalse(majorA.equals(majorYear));
        // null -> returns false
        assertFalse(majorA.equals(null));
    }
}
