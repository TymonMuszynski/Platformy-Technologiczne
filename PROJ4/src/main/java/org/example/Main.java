package org.example;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "Laboratory4";
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        addSampleData(entityManager);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Menu:");
            System.out.println("1. Dodaj maga");
            System.out.println("2. Dodaj tower");
            System.out.println("3. Usuń maga");
            System.out.println("4. Usun tower");
            System.out.println("5. Pokaż wszystkich magów");
            System.out.println("6. Pokaż wszystkie wieze");
            System.out.println("7. Pokaż wszystkie magow w wiezy powyzej");
            System.out.println("8. Wyjdź");

            System.out.print("Wybierz opcję: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addMage(entityManager, scanner);
                    break;
                case 2:
                    addTower(entityManager, scanner);
                    break;
                case 3:
                    removeMage(entityManager, scanner);
                    break;
                case 4:
                    removeTower(entityManager, scanner);
                    break;
                case 5:
                    showAllMages(entityManager);
                    break;
                case 6:
                    showAllTowers(entityManager);
                    break;
                case 7:
                    showAllTowersAbove(entityManager, scanner);
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Nieprawidłowa opcja.");
            }
        }

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void addSampleData(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Tower tower1 = new Tower();
        tower1.setName("Tower1");
        tower1.setHeight(100);

        Tower tower2 = new Tower();
        tower2.setName("Tower2");
        tower2.setHeight(200);


        Mage mage1 = new Mage();
        mage1.setName("Mage1");
        mage1.setLevel(51);
        tower1.addMage(mage1);

        Mage mage2 = new Mage();
        mage2.setName("Mage2");
        mage2.setLevel(52);
        tower1.addMage(mage2);

        Mage mage3 = new Mage();
        mage3.setName("Mage3");
        mage3.setLevel(53);
        tower2.addMage(mage3);

        entityManager.persist(tower1);
        entityManager.persist(tower2);
        entityManager.persist(mage1);
        entityManager.persist(mage2);
        entityManager.persist(mage3);

        entityManager.getTransaction().commit();
    }

    private static void addMage(EntityManager entityManager, Scanner scanner) {
        System.out.print("Podaj nazwę maga: ");
        String name = scanner.nextLine();
        System.out.print("Podaj poziom maga: ");
        int level = scanner.nextInt();
        scanner.nextLine();

        Mage mage = new Mage();
        mage.setName(name);
        mage.setLevel(level);

        System.out.print("Podaj nazwę wieży: ");
        String towerName = scanner.nextLine();

        Tower tower = entityManager.find(Tower.class, towerName);
        if (tower != null) {
            tower.addMage(mage);
            entityManager.getTransaction().begin();
            entityManager.persist(mage);
            entityManager.getTransaction().commit();
        } else {
            System.out.println("Nie znaleziono wieży o podanej nazwie.");
        }
    }

    private static void addTower(EntityManager entityManager, Scanner scanner) {
        System.out.print("Podaj nazwę nowej wieży: ");
        String name = scanner.nextLine();
        System.out.print("Podaj wysokość nowej wieży: ");
        int height = scanner.nextInt();
        scanner.nextLine();

        Tower tower_check = entityManager.find(Tower.class, name);
        if (tower_check != null) {
            System.out.println("Ta wieza juz istnieje");
            return;
        }

        Tower tower = new Tower();
        tower.setName(name);
        tower.setHeight(height);

        entityManager.getTransaction().begin();
        entityManager.persist(tower);
        entityManager.getTransaction().commit();
    }

    private static void removeMage(EntityManager entityManager, Scanner scanner) {
        System.out.print("Podaj nazwę maga do usunięcia: ");
        String mageName = scanner.nextLine();

        Mage mage = entityManager.find(Mage.class, mageName);
        if (mage != null) {
            List<Tower> towers = getAllTowers(entityManager);
            for (Tower tower : towers) {
                tower.removeMage(mage);
            }
            entityManager.getTransaction().begin();
            entityManager.remove(mage);
            entityManager.getTransaction().commit();
            System.out.println("Maga usunięto.");
        } else {
            System.out.println("Nie znaleziono maga o podanej nazwie.");
        }
    }

    private static void removeTower(EntityManager entityManager, Scanner scanner) {
        System.out.print("Podaj nazwę tower do usunięcia: ");
        String towerName = scanner.nextLine();

        Tower tower = entityManager.find(Tower.class, towerName);
        if (tower != null) {
            List<Mage> mages_in_tower = tower.getMages();
            for (Mage mage : mages_in_tower) {
                if (mage != null) {
                    entityManager.getTransaction().begin();
                    entityManager.remove(mage);
                    entityManager.getTransaction().commit();
                } else {
                    System.out.println("Nie znaleziono maga o podanej nazwie.");
                }
            }
            entityManager.getTransaction().begin();
            entityManager.remove(tower);
            entityManager.getTransaction().commit();
            System.out.println("Tower usunięto.");
        } else {
            System.out.println("Nie znaleziono maga o podanej nazwie.");
        }
    }


    private static void showAllMages(EntityManager entityManager) {
        List<Mage> mages = getAllMages(entityManager);
        if (mages.isEmpty()) {
            System.out.println("Brak magów.");
        } else {
            System.out.println("Lista wszystkich magów:");
            for (Mage mage : mages) {
                System.out.println("Mage: " + mage.getName() + ", Level: " + mage.getLevel() + ", Tower: " + mage.getTower().getName());
            }
        }
    }
    private static void showAllTowers(EntityManager entityManager){
    System.out.println("Lista wszystkich wież:");
    List<Tower> towers = getAllTowers(entityManager);
        for (Tower tower_entity : towers) {
            System.out.println("Tower: " + tower_entity.getName() + ", Height: " + tower_entity.getHeight());
            for (Mage mage: tower_entity.getMages()){
                System.out.println("Mage: " + mage.getName() + ", Level: " + mage.getLevel() + ", Tower: " + mage.getTower().getName());
            }
        }
    }

    private static void showAllTowersAbove(EntityManager entityManager,Scanner scanner){

        System.out.print("Podaj nazwę wieży: ");
        String towerName = scanner.nextLine();
        Tower tower_find = entityManager.find(Tower.class, towerName);

        System.out.print("Podaj min level Mage: ");
        int level = scanner.nextInt();

        List<Mage> mage_above = getAllMagesAbove(entityManager,tower_find, level);
        for (Mage mage_above_entity : mage_above) {
            System.out.println("Mage: " + mage_above_entity.getName() + ", Level: " + mage_above_entity.getLevel() + ", Tower: " + mage_above_entity.getTower().getName());
        }
    }
    private static List<Mage> getAllMagesAbove(EntityManager entityManager, Tower tower,int level) {
        Query query = entityManager.createQuery("SELECT m FROM Mage m WHERE m.tower = :tower AND m.level > :level", Mage.class).setParameter("tower", tower).setParameter("level", level);
        return query.getResultList();
    }


    private static List<Mage> getAllMages(EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT m FROM Mage m", Mage.class);
        return query.getResultList();
    }

    private static List<Tower> getAllTowers(EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT t FROM Tower t", Tower.class);
        return query.getResultList();
    }

}