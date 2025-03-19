package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.AttributeMatchesPredicate;

/**
 * Parses input arguments and creates a new FilterCommand instance.
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    /**
     * Parses the given {@code String} of arguments with respect to the FilterCommand
     * and returns an FilterCommand instance.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterCommand parse(String args) throws ParseException {
        // Idea below is from AddCommandParser.java.
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE);

        List<String> attributeStrings = argMultimap.getAllValues(PREFIX_ATTRIBUTE);

        if (attributeStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Set<Attribute> attributes = ParserUtil.parseAttributes(attributeStrings, true);

        AttributeMatchesPredicate predicate = new AttributeMatchesPredicate(attributes);

        return new FilterCommand(predicate);
    }
}
