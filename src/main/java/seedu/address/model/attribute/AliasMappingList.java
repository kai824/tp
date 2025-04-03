package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.LogsCenter;

/**
 * Manages correspondences between attribute names and website links.
 * It supports GitHub and LinkedIn by default.
 */
public class AliasMappingList {
    // Immutable since Java 9.
    // Source: https://www.geeksforgeeks.org/immutable-map-in-java/.
    public static final Map<String, String> DEFAULT_DICTIONARY =
        Map.of("github", "https://github.com/",
        "linkedin", "https://www.linkedin.com/in/");

    private static final Logger logger = LogsCenter.getLogger(AliasMappingList.class);

    private Map<String, String> dictionary = new HashMap<>(DEFAULT_DICTIONARY);
    private ObservableMap<String, String> unmodifiableDictionary =
        FXCollections.unmodifiableObservableMap(FXCollections.observableMap(dictionary));

    public AliasMappingList() { }

    /**
     * Replaces the alias mappings with the given {@code mappings}.
     * The keys (i.e., attribute names) must be in lowercase.
     */
    public void setAliases(ObservableMap<String, String> mappings) {
        requireNonNull(mappings);
        assert mappings.keySet().stream().allMatch(name -> name.equals(name.toLowerCase()));
        this.dictionary.clear();
        this.dictionary.putAll(mappings);
    }

    /**
     * Updates an alias mapping between an attribute name and a site link.
     * If there is no mapping for the attribute name currently, it creates a new mapping.
     * If the given siteLink is empty, the currently existing mapping will be removed (if it exists).
     *
     * @param attributeName The attribute name, which is case insensitive.
     * @param siteLink The site link which will be displayed and copied on behalf of the attribute name.
     *     To remove the current mapping, set it empty.
     */
    public void updateAlias(String attributeName, Optional<String> siteLink) {
        requireNonNull(attributeName);
        requireNonNull(siteLink);
        if (siteLink.isPresent()) {
            requireNonNull(siteLink.get());
            logger.fine("Successfully set a website link `" + siteLink.get() + "` associated with " + attributeName);
            dictionary.put(attributeName.toLowerCase(), siteLink.get());
        } else {
            logger.fine("Successfully removed a website link associated with " + attributeName);
            dictionary.remove(attributeName.toLowerCase());
        }
    }

    /**
     * Returns a mapping for the given attribute name.
     * @param attributeName The attribute name for which the mapped site link will be returned. Case insensitive.
     * @return The mapped site link is returned if there is a mapping for the given name.
     *     Otherwise, an empty instance will be returned.
     */
    public Optional<String> getAlias(String attributeName) {
        requireNonNull(attributeName);
        if (dictionary.containsKey(attributeName.toLowerCase())) {
            return Optional.of(dictionary.get(attributeName.toLowerCase()));
        }
        return Optional.empty();
    }

    /**
     * Returns the unmodifiable version of internal Map.
     */
    public ObservableMap<String, String> getUnmodifiableAliases() {
        return this.unmodifiableDictionary;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof AliasMappingList otherList) {
            return otherList.dictionary.equals(this.dictionary)
                && otherList.unmodifiableDictionary.equals(this.unmodifiableDictionary);
        }
        return false;
    }
}
