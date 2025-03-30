package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ATTRIBUTE_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_NAME_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_ATTRIBUTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.attribute.Attribute;

public class AliasCommandParserTest {
    private final AliasCommandParser parser = new AliasCommandParser();
    private String name1 = "k3209g09krr";
    private String link1 = "https://404-not-found";

    @Test
    void parse_removeAlias_success() throws Exception {
        assertParseSuccess(parser, " " + PREFIX_REMOVE_ATTRIBUTE + "github",
            AliasCommand.removeAliasCommand("github"));
    }

    @Test
    void parse_removeAlias_failure() throws Exception {
        assertParseFailure(parser, " " + PREFIX_REMOVE_ATTRIBUTE + "github=",
            Attribute.MESSAGE_CONSTRAINTS_FOR_NAME);
        // Attribute name empty
        assertParseFailure(parser, " " + PREFIX_REMOVE_ATTRIBUTE,
            Attribute.MESSAGE_CONSTRAINT_NON_EMPTY_FOR_NAME);
    }

    @Test
    void parse_addAlias_success() throws Exception {
        assertParseSuccess(parser, " " + PREFIX_ATTRIBUTE + name1 + "=" + link1,
            AliasCommand.addAliasCommand(name1, link1));
        assertParseSuccess(parser, ATTRIBUTE_MAJOR,
            AliasCommand.addAliasCommand(VALID_ATTRIBUTE_NAME_MAJOR, VALID_ATTRIBUTE_VALUE_MAJOR));
    }

    @Test
    void parse_addAlias_failure() throws Exception {
        // Invalid attribute name
        assertParseFailure(parser, " " + PREFIX_ATTRIBUTE + link1 + "=",
            Attribute.MESSAGE_CONSTRAINTS_FOR_NAME);
        // Attribute name empty
        assertParseFailure(parser, " " + PREFIX_ATTRIBUTE + "=",
            AliasCommand.MESSAGE_USAGE);
        // Empty argument
        assertParseFailure(parser, " " + PREFIX_ATTRIBUTE,
            AliasCommand.MESSAGE_USAGE);
        // Missing =
        assertParseFailure(parser, " " + PREFIX_ATTRIBUTE + name1 + " " + link1,
            AliasCommand.MESSAGE_USAGE);
        // Site link empty
        assertParseFailure(parser, " " + PREFIX_ATTRIBUTE + name1 + "=",
            Attribute.MESSAGE_CONSTRAINT_NON_EMPTY_FOR_SITE_LINK);
    }

    @Test
    void parse_numOfArguments_failure() throws Exception {
        // Two adds
        assertParseFailure(parser, " " + PREFIX_ATTRIBUTE + name1 + "= " + PREFIX_ATTRIBUTE + name1 + "=",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_ACCEPT_ONE_ARGUMENT));
        // One add, one remove
        assertParseFailure(parser, " " + PREFIX_ATTRIBUTE + name1 + "= " + PREFIX_REMOVE_ATTRIBUTE + name1,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_ACCEPT_ONE_ARGUMENT));
        // Two removes
        assertParseFailure(parser, " " + PREFIX_REMOVE_ATTRIBUTE + name1 + " " + PREFIX_REMOVE_ATTRIBUTE + name1,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_ACCEPT_ONE_ARGUMENT));
        // No argument
        assertParseFailure(parser, " ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_ACCEPT_ONE_ARGUMENT));
    }
}
