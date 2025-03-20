package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.attribute.Attribute.MESSAGE_CONSTRAINTS_FOR_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces
        SortCommand expectedSortCommand =
                new SortCommand("Graduation Year");
        assertParseSuccess(parser, "sort a/Graduation Year", expectedSortCommand);
        assertParseSuccess(parser, "sort a/    Graduation Year   ", expectedSortCommand);
    }

    @Test
    public void parse_invalidArgs_throwsExceptions() throws Exception {
        SortCommandParser parser = new SortCommandParser();
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SortCommand.MESSAGE_USAGE), () ->
                parser.parse(SortCommand.COMMAND_WORD));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SortCommand.MESSAGE_USAGE), () ->
                parser.parse(SortCommand.COMMAND_WORD + " major"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_CONSTRAINTS_FOR_NAME), () ->
                parser.parse(SortCommand.COMMAND_WORD + " " + PREFIX_ATTRIBUTE.toString() + "/"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_CONSTRAINTS_FOR_NAME), () ->
                parser.parse(SortCommand.COMMAND_WORD + " " + PREFIX_ATTRIBUTE.toString() + "major n/pochi"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_CONSTRAINTS_FOR_NAME), () ->
                parser.parse(SortCommand.COMMAND_WORD + " " + PREFIX_ATTRIBUTE.toString() + "\\"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_CONSTRAINTS_FOR_NAME), () ->
                parser.parse(SortCommand.COMMAND_WORD + " " + PREFIX_ATTRIBUTE.toString() + "="));
        assertThrows(ParseException.class, () ->
                parser.parse(SortCommand.COMMAND_WORD + " "
                    + PREFIX_ATTRIBUTE.toString() + " " + PREFIX_ATTRIBUTE.toString()));
    }

}
