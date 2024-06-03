package org.example;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional; // Dodaj import klasy Optional

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MageControllerTest {
    @Test
    public void testDeleteNonExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);

        doThrow(new IllegalArgumentException("not found")).when(repository).delete("Merlin");

        String result = controller.delete("Merlin");

        assertEquals("not found", result);
        verify(repository, times(1)).delete("Merlin");
    }

    @Test
    public void testFindNonExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);

        when(repository.find("Merlin")).thenReturn(Optional.empty());

        String result = controller.find("Merlin");

        assertEquals("not found", result);
    }

    @Test
    public void testFindExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);

        when(repository.find("Gandalf")).thenReturn(Optional.of(new Mage("Gandalf", 15)));

        String result = controller.find("Gandalf");

        assertEquals("Mage: Gandalf, Level: 15", result);
    }

    @Test
    public void testSaveExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);

        doThrow(new IllegalArgumentException("bad request")).when(repository).save(any(Mage.class));

        String result = controller.save("Merlin", 10);

        assertEquals("bad request", result);
        verify(repository, times(1)).save(any(Mage.class));
    }

    @Test
    public void testDeleteExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController mageController = new MageController(repository);
        Mage mage = new Mage("Merlin", 50);
        when(repository.find("Merlin")).thenReturn(Optional.of(mage));
        String result = mageController.delete("Merlin");

        verify(repository).delete("Merlin");
        assertEquals("done", result);
    }

    @Test
    public void testSaveNewMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);

        when(repository.save(new Mage("Merlin", 10))).thenReturn("done");

        String result = controller.save("Merlin", 10);

        assertEquals("done", result);
    }



}
