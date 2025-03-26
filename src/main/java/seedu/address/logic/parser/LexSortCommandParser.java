package seedu.address.logic.parser;

import seedu.address.logic.commands.LexSortCommand;
import seedu.address.logic.commands.SortCommand;

/**
 * Parses input arguments and creates a new LexSortCommand object
 */
public class LexSortCommandParser extends SortCommandParser {
    @Override
    public SortCommand createSortCommand(String attribute) {
        return new LexSortCommand(attribute);
    }
}
