package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attribute.Attribute;

/**
 * Jackson-friendly version of {@link Attribute}.
 */
class JsonAdaptedAttribute {

    private final String attributeName;
    private final String attributeValue;

    /**
     * Constructs a {@code JsonAdaptedAttribute} with the given {@code attributeName} and {@code attributeValue}.
     */
    @JsonCreator
    public JsonAdaptedAttribute(@JsonProperty("name") String attributeName,
                                @JsonProperty("value") String attributeValue) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    /**
     * Converts a given {@code Attribute} into this class for Jackson use.
     */
    public JsonAdaptedAttribute(Attribute source) {
        attributeName = source.getCaseAwareAttributeName();
        attributeValue = source.getAttributeValue();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * Converts this Jackson-friendly adapted attribute object into the model's {@code Attribute} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attribute.
     */
    public Attribute toModelType() throws IllegalValueException {
        if (!Attribute.isValidAttribute(attributeName) || !Attribute.isValidAttribute(attributeValue)) {
            throw new IllegalValueException(Attribute.MESSAGE_CONSTRAINTS);
        }
        return new Attribute(attributeName, attributeValue);
    }

}
