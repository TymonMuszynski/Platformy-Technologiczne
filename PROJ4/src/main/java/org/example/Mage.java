package org.example;
import javax.persistence.*;
import java.util.List;

@Entity
public class Mage {
    @Id
    private String name;
    private int level;

    @ManyToOne
    private Tower tower;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }
}