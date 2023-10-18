package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINDEX;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddMusiciantoBandCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddMusiciantoBandCommand object
 */
public class AddMusiciantoBandCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMusiciantoBandCommand
     * and returns an AddMusiciantoBandCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMusiciantoBandCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BINDEX, PREFIX_MINDEX);
        if (!arePrefixesPresent(argMultimap, PREFIX_BINDEX, PREFIX_MINDEX) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMusiciantoBandCommand.MESSAGE_USAGE));
        }
        int bandIndex = Integer.parseInt(argMultimap.getValue(PREFIX_BINDEX).get());
        int musicianIndex = Integer.parseInt(argMultimap.getValue(PREFIX_MINDEX).get());
        return new AddMusiciantoBandCommand(bandIndex, musicianIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

