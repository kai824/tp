package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AttributeRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AttributeRemoveCommand object
 */
public class AttributeRemoveCommandParser implements Parser<AttributeRemoveCommand> {

    @Override
    public AttributeRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttributeRemoveCommand.MESSAGE_USAGE), e);
        }

        List<String> attributes = argMultimap.getAllValues(PREFIX_ATTRIBUTE);

        if (attributes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AttributeRemoveCommand.MESSAGE_USAGE));
        }

        return new AttributeRemoveCommand(index, attributes);
    }
}
