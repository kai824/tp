package seedu.address.model.attribute;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Manages correspondencies between attribute names and website links.
 * It supports GitHub and LinkedIn by default.
 */
public class AliasMappingList {
    // Immutable since Java 9.
    // Source: https://www.geeksforgeeks.org/immutable-map-in-java/.
    public static final Map<String, String> DEFAULT_DICTIONARY =
        Map.of("github", "https://github.com/",
        "linkedin", "https://www.linkedin.com/in/");

    private Map<String, String> dictionary = new HashMap<>(DEFAULT_DICTIONARY);
    private ObservableMap<String, String> unmodifiableDictionary =
        FXCollections.unmodifiableObservableMap(FXCollections.observableMap(dictionary));

    public AliasMappingList() { }

    /**
     * Replaces the alias mappings with the given {@code mappings}.
     */
    public void setAliases(ObservableMap<String, String> mappings) {
        this.dictionary.clear();
        this.dictionary.putAll(DEFAULT_DICTIONARY);
        this.dictionary.putAll(mappings);
    }

    /**
     * Adds an alias mapping between an attribute name and a site link.
     *
     * @param attributeName The attribute name, which is case insensitive.
     * @param siteLink The site link which will be displayed and copied on behalf of the attribute name.
     */
    public void addAlias(String attributeName, String siteLink) {
        dictionary.put(attributeName.toLowerCase(), siteLink);
    }

    /**
     * Returns a mapping for the given attribute name.
     * @param attributeName The attribute name for which the mapped site link will be returned. Case insensitive.
     * @return The mapped site link is returned if there is a mapping for the given name.
     *     Otherwise, an attribute name + ": " will be returned.
     */
    public String getAlias(String attributeName) {
        if (dictionary.containsKey(attributeName.toLowerCase())) {
            return dictionary.get(attributeName.toLowerCase());
        } else {
            return attributeName + ": ";
        }
    }

    /**
     * Returns the unmodifiable version of internal Map.
     */
    public ObservableMap<String, String> getUnmodifiableAliases() {
        return this.unmodifiableDictionary;
    }
}
