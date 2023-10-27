package seedu.address.logic.commands.band;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.band.Band;
import seedu.address.model.musician.Musician;

/**
 * Adds a musician to a band.
 */
public class AddMusicianToBandCommand extends Command {
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

    private final Index bandToAddInto;
    private final Index musicianToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Musician}
     */
    public AddMusicianToBandCommand(Index bandToAddInto, Index musicianToAdd) {
        requireNonNull(bandToAddInto);
        requireNonNull(musicianToAdd);
        this.bandToAddInto = bandToAddInto;
        this.musicianToAdd = musicianToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Band> lastShownBandList = model.getFilteredBandList();
        List<Musician> lastShownMusicianList = model.getFilteredMusicianList();
        if (bandToAddInto.getZeroBased() >= lastShownBandList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BAND_DISPLAYED_INDEX);
        }
        if (musicianToAdd.getZeroBased() >= lastShownMusicianList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MUSICIAN_DISPLAYED_INDEX);
        }

        if (model.hasMusicianInBand(bandToAddInto.getZeroBased(), musicianToAdd.getZeroBased())) {
            throw new CommandException(MESSAGE_DUPLICATE_MUSICIAN);
        }

        Band band = lastShownBandList.get(bandToAddInto.getZeroBased());
        Musician musician = lastShownMusicianList.get(musicianToAdd.getZeroBased());
        model.addMusicianToBand(bandToAddInto.getZeroBased(), musicianToAdd.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(band, musician)));
    }
}
