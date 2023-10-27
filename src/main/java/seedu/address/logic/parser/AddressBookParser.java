package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindBandCommand;
import seedu.address.logic.commands.band.AddBandCommand;
import seedu.address.logic.commands.band.AddMusicianToBandCommand;
import seedu.address.logic.commands.band.DeleteBandCommand;
import seedu.address.logic.commands.general.ClearCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.musician.AddCommand;
import seedu.address.logic.commands.musician.DeleteCommand;
import seedu.address.logic.commands.musician.EditCommand;
import seedu.address.logic.commands.musician.FindCommand;
import seedu.address.logic.commands.musician.ListCommand;
import seedu.address.logic.parser.band.AddBandCommandParser;
import seedu.address.logic.parser.band.AddMusicianToBandCommandParser;
import seedu.address.logic.parser.band.DeleteBandCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.musician.AddCommandParser;
import seedu.address.logic.parser.musician.DeleteCommandParser;
import seedu.address.logic.parser.musician.EditCommandParser;
import seedu.address.logic.parser.musician.FindCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case FindBandCommand.COMMAND_WORD:
            return new FindBandCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddBandCommand.COMMAND_WORD:
            return new AddBandCommandParser().parse(arguments);

        case AddMusicianToBandCommand.COMMAND_WORD:
            return new AddMusicianToBandCommandParser().parse(arguments);

        case DeleteBandCommand.COMMAND_WORD:
            return new DeleteBandCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
