package org.example;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MageRepositoryTest {
    @Test
    public void testDeleteNonExistingMage() {
        MageRepository repository = new MageRepository();

        try {
            repository.delete("Merlin");
            fail("Error with parameters");
        } catch (IllegalArgumentException e) {
            assertEquals("Mage with name Merlin not found", e.getMessage());
        }
    }


    @Test
    public void testFindNonExistingMage() {
        MageRepository repository = new MageRepository();

        Optional<Mage> result = repository.find("Merlin");

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindExistingMage() {
        MageRepository repository = new MageRepository();
        repository.save(new Mage("Gandalf", 15));

        Optional<Mage> result = repository.find("Gandalf");

        assertTrue(result.isPresent());
        assertEquals("Gandalf", result.get().getName());
        assertEquals(15, result.get().getLevel());
    }

    @Test
    public void testSaveExistingMage() {
        MageRepository repository = new MageRepository();
        repository.save(new Mage("Merlin", 10));

        try {
            repository.save(new Mage("Merlin", 10));
            fail("Error with parameters");
        } catch (IllegalArgumentException e) {
            assertEquals("Mage with name Merlin already exists", e.getMessage());
        }
    }

}
