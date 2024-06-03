package org.example;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<Mage, Integer>Map;
        Set<Mage>setLevel0;
        Set<Mage>setLevel1;
        Set<Mage>setLevel2;
        Set<Mage>setLevel3;
        if(args != null) {
            if (args[0].equals("1")) {
                Map = new TreeMap<Mage, Integer>();
                setLevel0 = new TreeSet<Mage>();
                setLevel1 = new TreeSet<Mage>();
                setLevel2 = new TreeSet<Mage>();
                setLevel3 = new TreeSet<Mage>();
            } else if (args[0].equals("2")) {
                CustomComparator comparatorAlternative = new CustomComparator();
                Map = new TreeMap<Mage, Integer>(comparatorAlternative);
                setLevel0 = new TreeSet<Mage>(comparatorAlternative);
                setLevel1 = new TreeSet<Mage>(comparatorAlternative);
                setLevel2 = new TreeSet<Mage>(comparatorAlternative);
                setLevel3 = new TreeSet<Mage>(comparatorAlternative);
            } else {
                Map = new HashMap<>();
                setLevel0 = new HashSet<Mage>();
                setLevel1 = new HashSet<Mage>();
                setLevel2 = new HashSet<Mage>();
                setLevel3 = new HashSet<Mage>();
            }
        }
        else {
            return;
        }

        Mage m_1 = new Mage("Mage8", 12, 19, null);
        Mage m0 = new Mage("Mage7", 12, 3, null);
        setLevel3.add(m_1);
        setLevel3.add(m0);
        Mage m1 = new Mage("Mage6", 12, 19, null);
        Mage m2 = new Mage("Mage5", 12, 3, null);
        setLevel2.add(m1);
        setLevel2.add(m2);
        Mage m3 = new Mage("Mage4", 6, 4, setLevel2);
        Mage m4 = new Mage("Mage3", 10, 5, null);
        setLevel1.add(m3);
        setLevel1.add(m4);
        Mage m5 = new Mage("Mage2", 6, 3, setLevel1);
        Mage m6 = new Mage("Mage1", 3, 19, setLevel3);
        setLevel0.add(m5);
        setLevel0.add(m6);

        int counter = 0;
        for (Mage mage:setLevel0) {
            counter += 1;
            StringBuilder prefix = new StringBuilder(counter + ".");
            mage.print(1, prefix.toString());
        }

        for (Mage mage:setLevel0) {
            mage.putMapStats(Map);
        }

        System.out. println();
        for (Map.Entry<Mage, Integer> entry : Map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}