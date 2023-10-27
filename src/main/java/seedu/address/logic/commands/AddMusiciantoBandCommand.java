package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.band.Band;
import seedu.address.model.musician.Musician;

/**
 * Adds a musician to a band.
 */
public class AddMusiciantoBandCommand extends Command {
    public static final String COMMAND_WORD = "addm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a musician to a band. "
            + "Parameters: "
            + PREFIX_BINDEX + "INDEX OF BAND "
            + PREFIX_MINDEX + "INDEX OF MUSICIAN\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BINDEX + "1 "
            + PREFIX_MINDEX + "1";

    public static final String MESSAGE_SUCCESS = "New musician added to band: %1$s";
    public static final String MESSAGE_DUPLICATE_MUSICIAN = "This musician already exists in the band";
    public static final String MESSAGE_OUT_OF_BOUNDS = "The index of musician is not found in the list below. "
            + "Do enter the command 'list' and check if the index is in the list!";

    private final Index bandToAddInto;
    private final Index musicianToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Musician}
     */
    public AddMusiciantoBandCommand(Index bandToAddInto, Index musicianToAdd) {
        requireNonNull(bandToAddInto);
        requireNonNull(musicianToAdd);
        this.bandToAddInto = bandToAddInto;
        this.musicianToAdd = musicianToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            if (model.hasMusicianInBand(bandToAddInto.getZeroBased(), musicianToAdd.getZeroBased())) {
                throw new CommandException(MESSAGE_DUPLICATE_MUSICIAN);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
        }
        List<Band> lastShownBandList = model.getFilteredBandList();
        List<Musician> lastShownMusicianList = model.getFilteredMusicianList();
        Band band = lastShownBandList.get(bandToAddInto.getZeroBased());
        Musician musician;
        try {
            musician = lastShownMusicianList.get(musicianToAdd.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
        }
        model.addMusicianToBand(bandToAddInto.getZeroBased(), musicianToAdd.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(band, musician)));
    }
}
