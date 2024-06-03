package org.example;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Tower {
    @Id
    private String name;
    private int height;

    @OneToMany(mappedBy = "tower")
    private List<Mage> mages = new ArrayList<>();

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void addMage(Mage mage) {
        mages.add(mage);
        mage.setTower(this); // Ustawienie wieży dla maga
    }
    public void removeMage(Mage mage) {
        if (mages != null) {
            mages.remove(mage);
            mage.setTower(null);
        }
    }
}