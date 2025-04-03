package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attribute.Attribute;

/**
 * Manages a pair of attribute name and site link in a Jackson-friendly way.
 */
public class JsonAdaptedAliasMapping {
    private final String attributeName;
    private final String siteLink;

    /**
     * Constructs a {@code JsonAdaptedAliasMapping} with the given {@code attributeName} and {@code siteLink}.
     */
    @JsonCreator
    public JsonAdaptedAliasMapping(@JsonProperty("name") String attributeName,
                                @JsonProperty("link") String siteLink) {
        this.attributeName = attributeName;
        this.siteLink = siteLink;
    }

    /**
     * Returns the raw {@code attributeName}.
     */
    public String getAttributeName() throws IllegalValueException {
        if (!Attribute.isValidAttribute(attributeName)) {
            throw new IllegalValueException(Attribute.MESSAGE_CONSTRAINTS);
        }
        return attributeName.toLowerCase();
    }

    /**
     * Returns the raw {@code siteLink}.
     */
    public String getSiteLink() {
        return siteLink;
    }
}
