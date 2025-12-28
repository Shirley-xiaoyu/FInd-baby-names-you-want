import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.*;

public class BabyNamesAnalyzer {

    public void totalBirthsAboveNameIn1990(String targetName, String genderFilter) {
        // Select file dialog, e.g. yob1990.csv
        FileResource fr = new FileResource();

        List<NameCount> names = new ArrayList<>();

        for (CSVRecord record : fr.getCSVParser(false)) {
            String name = record.get(0);
            String gender = record.get(1);
            int count = Integer.parseInt(record.get(2));
            if (gender.equals(genderFilter)) {
                names.add(new NameCount(name, count));
            }
        }

        // Sort by count descending (higher count = higher rank)
        Collections.sort(names, (a, b) -> b.count - a.count);

        int targetRank = -1;
        int rank = 1;
        int totalBirthsAbove = 0;

        for (NameCount nc : names) {
            if (nc.name.equalsIgnoreCase(targetName)) {
                targetRank = rank;
                break;
            }
            totalBirthsAbove += nc.count;
            rank++;
        }

        if (targetRank == -1) {
            System.out.println(targetName + " not found in the file");
        } else {
            System.out.println("In 1990, total births with names ranked higher than " 
                + targetName + " is: " + totalBirthsAbove);
        }
    }

    private class NameCount {
        String name;
        int count;

        public NameCount(String name, int count) {
            this.name = name;
            this.count = count;
        }
    }

    // Helper method to check for girl name Emily
    public void countBirthsAboveEmily1990() {
        totalBirthsAboveNameIn1990("Emily", "F");
    }

    // Helper method to check for boy name Drew
    public void countBirthsAboveDrew1990() {
        totalBirthsAboveNameIn1990("Drew", "M");
    }

}
