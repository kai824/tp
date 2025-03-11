package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AttributeRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class AttributeRemoveCommandParser implements Parser<AttributeRemoveCommand> {

    @Override
    public AttributeRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ATTRIBUTE);
        
        String candidateName = argMultimap.getValue(PREFIX_NAME).orElseThrow(
                () -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AttributeRemoveCommand.MESSAGE_USAGE))
        );
        String attributeName = argMultimap.getValue(PREFIX_ATTRIBUTE).orElseThrow(
            () -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AttributeRemoveCommand.MESSAGE_USAGE))
    );
        return new AttributeRemoveCommand(new Name(candidateName), attributeName);
    }
    ;
}
