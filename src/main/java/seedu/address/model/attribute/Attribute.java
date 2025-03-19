package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Attribute in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAttribute(String)}.
 * The hash code of an Attribute is that of the attribute name.
 */
public class Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Attribute names and values should not contain /, \\, or =";
    public static final String NO_DUPLICATES = "Duplicate attribute names with different values are not allowed!";
    public static final String VALIDATION_REGEX = "[^\\\\/=]+";

    public final String attributeName;
    public final String attributeValue;

    /**
     * Constructs an {@code Attribute}.
     *
     * @param attributeName A valid attribute name.
     * @param attributeValue A valid attribute value.
     */
    public Attribute(String attributeName, String attributeValue) {
        requireNonNull(attributeName);
        requireNonNull(attributeValue);
        checkArgument(isValidAttribute(attributeName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidAttribute(attributeValue), MESSAGE_CONSTRAINTS);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    /**
     * Returns true if a given string is a valid attribute name or value.
     */
    public static boolean isValidAttribute(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Attribute attribute) {
            return this.matchesName(attribute.attributeName)
                    && attributeValue.equals(attribute.attributeValue);
        }

        return false;
    }

    /**
     * Checks if attribute name matches a given string, case insensitively.
     *
     * @param name Attribute name to check against
     * @return True if it matches, false otherwise
     */
    public boolean matchesName(String name) {
        return attributeName.equalsIgnoreCase(name);
    }

    /**
     * Compare to another attributes with the same attribute name by their attribute value.
     *
     * @param other The other attribute to compare with
     * @return 0 if they have the same attribute value, -1 if attribute1 has a smaller value, 1 otherwise
     */
    public int compareToAttributeOfSameAttributeName(Attribute other) {
        assert this.attributeName.equals(other.attributeName);
        return this.attributeValue.compareTo(other.attributeValue);
    }

    @Override
    public int hashCode() {
        return attributeName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[%s: %s]", attributeName, attributeValue);
    }

}
