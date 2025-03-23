package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Manages a pair of attribute name and site link in a Jackson-friendly way.
 */
public class JsonAdaptedAliasMapping {
    private static final String MESSAGE_NULL = "%1$s cannot be null!";
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
     * @throws IllegalValueException Thrown when {@code attributeName} is null.
     */
    public String getAttributeName() throws IllegalValueException {
        if (attributeName == null) {
            throw new IllegalValueException(String.format(MESSAGE_NULL, "Attribute name"));
        }
        return attributeName;
    }

    /**
     * Returns the raw {@code siteLink}.
     * @throws IllegalValueException Thrown when {@code siteLink} is null.
     */
    public String getSiteLink() throws IllegalValueException {
        if (siteLink == null) {
            throw new IllegalValueException(String.format(MESSAGE_NULL, "Site link"));
        }
        return siteLink;
    }
}
