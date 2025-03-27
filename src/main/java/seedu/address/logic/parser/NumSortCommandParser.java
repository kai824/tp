package seedu.address.logic.parser;

import seedu.address.logic.commands.NumSortCommand;

/**
 * Parses input arguments and creates a new NumSortCommand object
 */
public class NumSortCommandParser extends SortCommandParser {
    @Override
    public NumSortCommand createSortCommand(String attribute) {
        return new NumSortCommand(attribute);
    }
}
