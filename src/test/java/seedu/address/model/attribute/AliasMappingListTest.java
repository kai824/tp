package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class AliasMappingListTest {
    @Test
    void creation_defaultValue() {
        AliasMappingList list = new AliasMappingList();
        assertTrue(list.getAlias("github").isPresent());
        assertTrue(list.getAlias("github").get().equals("https://github.com/"));
        assertTrue(list.getAlias("LINKEDIN").isPresent());
        assertTrue(list.getAlias("LINKEDIN").get().equals("https://www.linkedin.com/in/"));
        assertFalse(list.getAlias("").isPresent());
    }
    @Test
    void updateAlias_success() {
        AliasMappingList list = new AliasMappingList();
        String name1 = "jsi390209dsifnir";
        String link1 = "/\\09438nfqJui43-0(*)";
        list.updateAlias(name1, Optional.of(link1));
        list.updateAlias("github", Optional.empty());
        assertFalse(list.getAlias("github").isPresent());
        assertTrue(list.getAlias("LINKEDIN").isPresent());
        assertTrue(list.getAlias("LINKEDIN").get().equals("https://www.linkedin.com/in/"));
        assertFalse(list.getAlias("").isPresent());
        assertTrue(list.getAlias(name1).isPresent());
        assertTrue(list.getAlias(name1.toUpperCase()).isPresent());
        assertTrue(list.getAlias(name1).get().equals(link1));
        assertTrue(list.getAlias(name1.toUpperCase()).get().equals(link1));
        Map<String, String> internalList = list.getUnmodifiableAliases();
        assertTrue(internalList.containsKey(name1));
        assertFalse(internalList.containsKey(""));
        assertFalse(internalList.containsKey("github"));
        assertTrue(internalList.get(name1.toLowerCase()).equals(link1));
    }
}
