package org.example;
import java.util.Set;
import java.util.*;

public class Mage implements Comparable<Mage>{
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;
    public Mage(String name, int level, double power, Set<Mage> apprentices){
        this.name = name;
        this.level = level;
        this.power = power;
        this.apprentices = apprentices;
    }

    @Override
    public String toString() {
        return "Mage{name = '" + name + "', level = " + level + ", power = " + power + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mage that = (Mage) obj;
        return Objects.equals(level, that.level) &&
                Double.compare(that.power, power) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, name, power);
    }

    public int compareTo(Mage m) {
        if (this.name.equals(m.name)) {
            if (this.level < m.level) {
                return -1;
            } else if (this.level > m.level) {
                return 1;
            } else {
                return Double.compare(this.power, m.power);
            }
        } else {
            return name.compareTo(m.name);
        }
    }

    public void print(int level, String prefixBase){
        System.out.println(prefixBase +" "+ this.toString());
        if (!(apprentices == null)){
            int counter = 0;
            for(Mage mage:apprentices){
                counter+=1;
                StringBuilder prefix = new StringBuilder(prefixBase  + counter + ".");
                mage.print(level+1, prefix.toString() );
            }
        }
    }

    public void putMapStats(Map<Mage,Integer> stats){
        if (apprentices != null){
            for(Mage mage:apprentices){
                mage.putMapStats(stats);
                int current = stats.getOrDefault ( mage, 0);
                int app = stats.getOrDefault(this, 0 );
                stats.put(this, current + app + 1);
            }
        }
        else {
            stats.put(this, 0);
        }
    }


    public double getPower() {
        return power;
    }
    public int getLevel() {
        return level;
    }
    public String getName() {
        return name;
    }
    public Set<Mage> getApprentices() {
        return apprentices;
    }
    public void setPower(double power) {
        this.power = power;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setApprentices(Set<Mage> apprentices) {
        this.apprentices = apprentices;
    }

}
