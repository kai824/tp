package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.attribute.Attribute;
import seedu.address.testutil.PersonBuilder;

public class AttributeMatchesPredicateTest {

    @Test
    public void test_attributeMatches_returnsTrue() {
        // One attribute
        AttributeMatchesPredicate predicate =
            new AttributeMatchesPredicate(List.of(new Attribute("Major", "Computer Science")));
        assertTrue(predicate.test(new PersonBuilder()
            .withAttributes("Major", "Computer Science").build()));

        // Multiple attributes
        predicate = new AttributeMatchesPredicate(
            List.of(new Attribute("University", "NUS"), new Attribute("Hobby", "Baseball")));
        assertTrue(predicate.test(new PersonBuilder()
            .withAttributes("University", "NUS", "Hobby", "Baseball").build()));

        // Person has extra attributes
        predicate = new AttributeMatchesPredicate(
            List.of(new Attribute("CCA", "Dance"), new Attribute("Graduation year", "2028")));
        assertTrue(predicate.test(new PersonBuilder()
            .withAttributes(
                "CCA", "Dance", "Hobby", "k-pop", "Graduation year", "2028", "Age", "20")
                    .build()));
    }

    @Test
    public void test_attributeMatches_returnsFalse() {
        // Mixed-case attributes: value
        AttributeMatchesPredicate predicate =
            new AttributeMatchesPredicate(List.of(new Attribute("University", "NUS")));
        assertFalse(predicate.test(new PersonBuilder().withAttributes("University", "nus").build()));

        // Same name but different value
        predicate = new AttributeMatchesPredicate(
            List.of(new Attribute("University", "NUS")));
        assertFalse(predicate.test(new PersonBuilder()
            .withAttributes("University", "NTU").build()));

        // Mixed-case attributes: name
        predicate =
                new AttributeMatchesPredicate(List.of(new Attribute("University", "NUS")));
        assertFalse(predicate.test(new PersonBuilder().withAttributes("university", "NUS").build()));

        // Non-matching attributes: value
        predicate = new AttributeMatchesPredicate(
            List.of(new Attribute("University", "NUS"), new Attribute("Hobby", "Baseball")));
        assertFalse(predicate.test(new PersonBuilder()
            .withAttributes("University", "NUS", "Hobby", "Softball").build()));

        // Non-matching attributes: name
        predicate = new AttributeMatchesPredicate(
            List.of(new Attribute("University", "NUS"), new Attribute("Hobby", "Baseball")));
        assertFalse(predicate.test(new PersonBuilder()
            .withAttributes("University", "NUS", "CCA", "Softball").build()));

        // No attribute
        predicate = new AttributeMatchesPredicate(
            List.of(new Attribute("Graduation year", "2022")));
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Name and value are swapped
        predicate =
            new AttributeMatchesPredicate(List.of(new Attribute("fuga", "hoge")));
        assertFalse(predicate.test(new PersonBuilder().withAttributes("hoge", "fuga").build()));

        // Attributes match name, phone, email and address, but does not match the attributes
        predicate = new AttributeMatchesPredicate(
            List.of(new Attribute("Alice", "12345"),
                new Attribute("alice@email.com", "Hobby")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAttributes("Hobby", "Baseball").build()));
    }
}
