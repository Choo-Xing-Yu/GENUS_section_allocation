import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    public static void main(String[] args) throws Exception{

        List<Member> members = new ArrayList<>();
        boolean isHeader = true;
        try (BufferedReader br = new BufferedReader(new FileReader("./test.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] values = line.split(",");

                // int randomNum = ThreadLocalRandom.current().nextInt(0, 11);

                String name = values[0];
                String previousSection = values[1].toUpperCase();
                String firstChoice = values[2].toUpperCase();
                String secondChoice = values[3].toUpperCase();
                String thirdChoice = values[4].toUpperCase();
                int ability = Integer.parseInt(values[5]);

                List<String> choice = List.of(firstChoice, secondChoice, thirdChoice);
                Member m = new Member(name, previousSection, choice, ability);
                members.add(m);
            }
        }


        Section s = new Section(12, 12, 13, 11, 5, 3);
        SectionAllocator sa = new SectionAllocator(members, s);
        System.out.println(sa.allocate());
    }
}
