package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.attribute.Attribute.MESSAGE_CONSTRAINTS_FOR_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LexSortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LexSortCommandParserTest {

    private final LexSortCommandParser parser = new LexSortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LexSortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces
        LexSortCommand expectedSortCommand =
                new LexSortCommand("Graduation Year");
        assertParseSuccess(parser, "sort a/Graduation Year", expectedSortCommand);
        assertParseSuccess(parser, "sort a/    Graduation Year   ", expectedSortCommand);
    }

    @Test
    public void parse_invalidArgs_throwsExceptions() throws Exception {
        LexSortCommandParser parser = new LexSortCommandParser();
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            LexSortCommand.MESSAGE_USAGE), () ->
                parser.parse(LexSortCommand.COMMAND_WORD));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            LexSortCommand.MESSAGE_USAGE), () ->
                parser.parse(LexSortCommand.COMMAND_WORD + " major"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_CONSTRAINTS_FOR_NAME), () ->
                parser.parse(LexSortCommand.COMMAND_WORD + " " + PREFIX_ATTRIBUTE + "/"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_CONSTRAINTS_FOR_NAME), () ->
                parser.parse(LexSortCommand.COMMAND_WORD + " " + PREFIX_ATTRIBUTE + "major n/pochi"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_CONSTRAINTS_FOR_NAME), () ->
                parser.parse(LexSortCommand.COMMAND_WORD + " " + PREFIX_ATTRIBUTE + "\\"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_CONSTRAINTS_FOR_NAME), () ->
                parser.parse(LexSortCommand.COMMAND_WORD + " " + PREFIX_ATTRIBUTE + "="));
        assertThrows(ParseException.class, () ->
                parser.parse(LexSortCommand.COMMAND_WORD + " "
                    + PREFIX_ATTRIBUTE.toString() + " " + PREFIX_ATTRIBUTE.toString()));
    }

}
