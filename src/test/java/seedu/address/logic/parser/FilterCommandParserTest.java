package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ATTRIBUTE_GRAD_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.ATTRIBUTE_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_NAME_GRAD_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_NAME_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_GRAD_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.AttributeMatchesPredicate;

public class FilterCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

    private FilterCommandParser parser = new FilterCommandParser();

    private Attribute major = new Attribute(VALID_ATTRIBUTE_NAME_MAJOR, VALID_ATTRIBUTE_VALUE_MAJOR);
    private Attribute year = new Attribute(VALID_ATTRIBUTE_NAME_GRAD_YEAR, VALID_ATTRIBUTE_VALUE_GRAD_YEAR);

    private FilterCommand createFilterCommand(Attribute... attributes) {
        return new FilterCommand(new AttributeMatchesPredicate(Set.of(attributes)));
    }

    @Test
    public void parse_noArgument_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_onlyPrefix_failure() {
        assertParseFailure(parser, PREFIX_ATTRIBUTE.toString(), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_oneAttribute_success() {
        assertParseSuccess(parser, ATTRIBUTE_MAJOR, createFilterCommand(major));
    }

    @Test
    public void parse_twoAttributes_success() {
        assertParseSuccess(parser, ATTRIBUTE_MAJOR + PREAMBLE_WHITESPACE + ATTRIBUTE_GRAD_YEAR,
            createFilterCommand(major, year));
    }
}
