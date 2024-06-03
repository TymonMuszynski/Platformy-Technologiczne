package org.example;
import java.util.Comparator;
import java.util.Map;

public class CustomComparator implements Comparator<Mage> {
    @Override
    public int compare(Mage mage1, Mage mage2) {
        if (mage1.getLevel() == mage2.getLevel()) {
            String name1 = mage1.getName();
            String name2 = mage2.getName();
            if (name1.equals(name2)) {
                return Double.compare(mage1.getPower(), mage2.getPower());
            }
            else {
                return name1.compareTo(name2);
            }
        }
        else if (mage1.getLevel() < mage2.getLevel()){
            return -1;
        } else {
            return 1;
        }
    }
}
