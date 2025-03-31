package seedu.address.storage;

import java.util.Optional;

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
    private final String siteLink;

    /**
     * Constructs a {@code JsonAdaptedAttribute} with the given {@code attributeName} and {@code attributeValue}.
     */
    @JsonCreator
    public JsonAdaptedAttribute(@JsonProperty("name") String attributeName,
                                @JsonProperty("value") String attributeValue,
                                @JsonProperty("link") String siteLink) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.siteLink = siteLink;
    }

    /**
     * Converts a given {@code Attribute} into this class for Jackson use.
     */
    public JsonAdaptedAttribute(Attribute source) {
        attributeName = source.getCaseAwareAttributeName();
        attributeValue = source.getAttributeValue();
        siteLink = source.getSiteLink().orElse(null);
    }

    public String getAttributeName() {
        return attributeName.toLowerCase();
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public Optional<String> getSiteLink() {
        return Optional.ofNullable(siteLink);
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
        return new Attribute(attributeName, attributeValue).updateSiteLink(Optional.ofNullable(siteLink));
    }

}
