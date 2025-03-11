package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class AttributeTest {
    @Test
    public void equals() {
        Attribute attribute = new Attribute("Graduation Year", "2027");

        // same name and value -> returns true
        Attribute attributeCopy = new Attribute("Graduation Year", "2027");
        assertEquals(attribute, attributeCopy);

        // null -> returns false
        assertNotEquals(null, attribute);

        // different attribute name -> returns false
        Attribute differentAttributeName = new Attribute("Enrolment Year", "2027");
        assertNotEquals(attribute, differentAttributeName);

        // different attribute value -> returns false
        Attribute differentAttributeValue = new Attribute("Graduation Year", "2026");
        assertNotEquals(attribute, differentAttributeValue);
    }

    @Test
    public void hashCode_equal() {
        Attribute attribute = new Attribute("Graduation Year", "2027");
        Attribute attributeCopy = new Attribute("Graduation Year", "2027");
        Attribute differentAttributeValue = new Attribute("Graduation Year", "2026");

        assertEquals(attribute.hashCode(), attributeCopy.hashCode());
        assertEquals(attribute.hashCode(), differentAttributeValue.hashCode());
    }

    @Test
    public void hashCode_notEqual() {
        Attribute attribute = new Attribute("Graduation Year", "2027");
        Attribute differentAttributeName = new Attribute("Enrolment Year", "2027");

        assertNotEquals(attribute.hashCode(), differentAttributeName.hashCode());
    }

    @Test
    public void validAttribute() {
        String[] validAttributes = {"Graduation Year", "2027", "(){}[]<>", "1234567890!@#$%^&*()",
            "-_+''\"\"", "'); DROP DATABASE records;",
            "very very very very very very very very very very very very very very very long string"};
        String[] invalidAttributes = {"", "/", "\\", "//////////////", "\\\\\\\\\\\\\\\\\\\\",
            "/here is a string containing a forward slash", "here is one \\ containing a backslash",
            "here=is=one=containing=equals"};

        for (String s : validAttributes) {
            new Attribute(s, "dummy");
            new Attribute("dummy", s);
            new Attribute(s, s);
        }

        for (String s : invalidAttributes) {
            try {
                new Attribute(s, "dummy");
                fail();
            } catch (IllegalArgumentException e) {
                // Expected
            }

            try {
                new Attribute("dummy", s);
                fail();
            } catch (IllegalArgumentException e) {
                // Expected
            }

            try {
                new Attribute(s, s);
                fail();
            } catch (IllegalArgumentException e) {
                // Expected
            }
        }

        try {
            new Attribute(null, "dummy");
            fail();
        } catch (NullPointerException e) {
            // Expected
        }

        try {
            new Attribute("dummy", null);
            fail();
        } catch (NullPointerException e) {
            // Expected
        }

        try {
            new Attribute(null, null);
            fail();
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
