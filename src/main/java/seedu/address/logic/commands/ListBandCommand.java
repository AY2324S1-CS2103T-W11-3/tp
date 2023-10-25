package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.band.Band;

/**
 * Lists all musicians in a band input by the user.
 */
public class ListBandCommand extends Command {

    public static final String COMMAND_WORD = "listb";

    public static final Object MESSAGE_USAGE = COMMAND_WORD + ": Finds all musicians who are part of "
        + "the band at the specified index and displays them as a list with index numbers.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public ListBandCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Band> filteredBandList = model.getFilteredBandList();
        if (targetIndex.getZeroBased() >= filteredBandList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BAND_DISPLAYED_INDEX);
        }

        model.updateFilteredMusicianList(targetIndex.getZeroBased());
        return new CommandResult(
            String.format(Messages.MESSAGE_MUSICIANS_LISTED_OVERVIEW, model.getFilteredMusicianList().size()));
    }
}
