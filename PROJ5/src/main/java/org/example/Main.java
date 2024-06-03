package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Tworzenie repozytorium i kontrolera
        MageRepository repository = new MageRepository();
        MageController controller = new MageController(repository);

        // Dodawanie kilku magów
        controller.save("Gandalf", 15);
        controller.save("Merlin", 10);

        // Wyszukiwanie maga
        System.out.println(controller.find("Gandalf")); // Powinno wypisać: Mage: Gandalf, Level: 15

        // Usuwanie maga
        System.out.println(controller.delete("Merlin")); // Powinno wypisać: done

        // Wyszukiwanie nieistniejącego maga
        System.out.println(controller.find("Merlin")); // Powinno wypisać: not found
    }
}
