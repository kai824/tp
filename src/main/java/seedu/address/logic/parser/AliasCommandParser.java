package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Attribute;

/**
 * Parses input arguments and creates a new AliasCommand object.
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    @Override
    public AliasCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE);

        List<String> aliasStrings = argMultimap.getAllValues(PREFIX_ATTRIBUTE);

        if (aliasStrings.size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        String aliasString = aliasStrings.get(0).trim();

        Matcher matcher = Pattern.compile("^(.*)=(.*)$").matcher(aliasString);

        if (!matcher.find()) {
            throw new ParseException(AliasCommand.COMMAND_WORD);
        }

        String attributeName = matcher.group(1).trim();
        String siteLink = matcher.group(2).trim();

        if (!Attribute.isValidAttribute(attributeName)) {
            throw new ParseException(Attribute.MESSAGE_CONSTRAINTS_FOR_NAME);
        }

        return new AliasCommand(attributeName, siteLink);
    }

}
