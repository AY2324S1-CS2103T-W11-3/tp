package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.band.AddBandCommand;
import seedu.address.logic.commands.band.AddMusicianToBandCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.musician.AddCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.band.Band;
import seedu.address.model.band.UniqueBandList;
import seedu.address.model.musician.Musician;
import seedu.address.model.musician.UniqueMusicianList;
import seedu.address.testutil.BandBuilder;
import seedu.address.testutil.MusicianBuilder;

public class AddMusicianToBandCommandTest {

    @Test
    public void constructor_nullMusician_throwsNullPointerException() {
        Index bandIndex = Index.fromOneBased(1);
        assertThrows(NullPointerException.class, () -> new AddMusicianToBandCommand(bandIndex, null));
    }
    @Test
    public void constructor_nullBand_throwsNullPointerException() {
        Index musicianIndex = Index.fromOneBased(1);
        assertThrows(NullPointerException.class, () -> new AddMusicianToBandCommand(null, musicianIndex));
    }
    @Test
    public void constructor_nullBandAndMusician_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMusicianToBandCommand(null, null));
    }
    @Test
    public void execute_musicianAcceptedByModel_addSuccessful() throws Exception {
        AddMusicianToBandCommandTest.ModelStubAcceptingMusicianAddedToBand modelBandStub =
                new AddMusicianToBandCommandTest.ModelStubAcceptingMusicianAddedToBand();

        Musician validMusician = new MusicianBuilder().build();
        Band validBand = new BandBuilder().build();
        CommandResult addCommandResult = new AddCommand(validMusician).execute(modelBandStub);
        CommandResult addBandCommandResult = new AddBandCommand(validBand).execute(modelBandStub);
        Index bandIndex = Index.fromOneBased(1);
        Index musicianIndex = Index.fromOneBased(1);
        CommandResult commandResult = new AddMusicianToBandCommand(bandIndex, musicianIndex).execute(modelBandStub);

        assertEquals(String.format(AddMusicianToBandCommand.MESSAGE_SUCCESS,
                        Messages.format(modelBandStub.bandsAdded.get(bandIndex),
                                modelBandStub.musiciansAdded.get(musicianIndex))),
                commandResult.getFeedbackToUser());
        assertEquals(validBand, modelBandStub.bandsAdded.get(bandIndex));
        assertEquals(validBand.getMusicians(), modelBandStub.bandsAdded.get(bandIndex).getMusicians());
    }
    @Test
    public void execute_duplicateMusicianAddedtoBand_throwsCommandException() throws Exception {
        AddMusicianToBandCommandTest.ModelStubAcceptingMusicianAddedToBand modelBandStub =
                new AddMusicianToBandCommandTest.ModelStubAcceptingMusicianAddedToBand();

        Musician validMusician = new MusicianBuilder().build();
        Band validBand = new BandBuilder().build();
        CommandResult addCommandResult = new AddCommand(validMusician).execute(modelBandStub);
        CommandResult addBandCommandResult = new AddBandCommand(validBand).execute(modelBandStub);
        Index bandIndex = Index.fromOneBased(1);
        Index musicianIndex = Index.fromOneBased(1);
        CommandResult firstAddMusiciancommandResult =
                new AddMusicianToBandCommand(bandIndex, musicianIndex).execute(modelBandStub);

        assertThrows(CommandException.class,
                AddMusicianToBandCommand.MESSAGE_DUPLICATE_MUSICIAN, () ->
                        new AddMusicianToBandCommand(bandIndex, musicianIndex).execute(modelBandStub));
    }
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMusician(Musician musician) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMusician(Musician musician) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMusician(Musician target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMusician(Musician target, Musician editedMusician) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Musician> getFilteredMusicianList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Band> getFilteredBandList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMusicianList(Predicate<Musician> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBandList(Predicate<Band> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBandMusicianList(Predicate<Band> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBand(Band band) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBand(Band band) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMusicianInBand(int addInto, int toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMusicianToBand(int addInto, int toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBand(Band target) {
            throw new AssertionError("This method should not be called.");
        }
    }
    /**
     * A Model stub that always accept the musician being added into the band.
     */
    private class ModelStubAcceptingMusicianAddedToBand extends AddMusicianToBandCommandTest.ModelStub {
        final UniqueBandList bandsAdded = new UniqueBandList();
        final UniqueMusicianList musiciansAdded = new UniqueMusicianList();
        @Override
        public boolean hasMusicianInBand(int bandToAddInto, int musicianToAdd) {
            requireNonNull(bandToAddInto);
            requireNonNull(musicianToAdd);
            Index bandIndex = Index.fromZeroBased(bandToAddInto);
            Index musicianIndex = Index.fromZeroBased(musicianToAdd);
            return bandsAdded.get(bandIndex)
                    .hasMusician(musiciansAdded.get(musicianIndex));
        }
        @Override
        public void addMusicianToBand(int bandToAddInto, int musicianToAdd) {
            Index bandIndex = Index.fromZeroBased(bandToAddInto);
            Index musicianIndex = Index.fromZeroBased(musicianToAdd);
            bandsAdded.get(bandIndex).getModifiableMusicianList()
                    .add(musiciansAdded.get(musicianIndex));
        }
        @Override
        public void addMusician(Musician musician) {
            requireNonNull(musician);
            musiciansAdded.add(musician);
        }
        @Override
        public void addBand(Band band) {
            requireNonNull(band);
            bandsAdded.add(band);
        }
        @Override
        public boolean hasBand(Band band) {
            requireNonNull(band);
            return bandsAdded.contains(band);
        }
        @Override
        public boolean hasMusician(Musician musician) {
            requireNonNull(musician);
            return musiciansAdded.contains(musician);
        }
        @Override
        public ObservableList<Band> getFilteredBandList() {
            return new FilteredList<>(bandsAdded.asUnmodifiableObservableList());
        }
        @Override
        public ObservableList<Musician> getFilteredMusicianList() {
            return new FilteredList<>(musiciansAdded.asUnmodifiableObservableList());
        }
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
