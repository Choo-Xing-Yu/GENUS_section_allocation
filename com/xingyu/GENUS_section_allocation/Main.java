package com.xingyu.GENUS_section_allocation;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.math.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter csv file name (same directory) [eg: for preferences.csv enter 'preferences']:");
        String fileName = sc.next();
        String parsedFileName =  "./" + fileName.replace(".csv", "") + ".csv";
        Path path = Path.of(parsedFileName);
        if (!Files.exists(path)) {
            throw new Exception(path.toString() + " does not exist!");
        }
        System.out.print("Enter previousSection weightage [eg: for 50% enter 0.5]:");
        Double previousSectionWeight = sc.nextDouble();
        BigDecimal previousSectionWeightBD = BigDecimal.valueOf(previousSectionWeight);
        System.out.print("Enter choicesWeight weightage [eg: for 50% enter 0.5]:");
        Double choicesWeight = sc.nextDouble();
        BigDecimal choicesWeightBD = BigDecimal.valueOf(choicesWeight);
        sc.close();

        if (previousSectionWeightBD.add(choicesWeightBD).compareTo(BigDecimal.ONE) != 0) {
            throw new Exception("Make sure previousSection + choicesWeight adds up to 1!");
        }

        List<Member> members = new ArrayList<>();
        boolean isHeader = true;
        try (BufferedReader br = new BufferedReader(new FileReader(path.toString()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                int expectedNumberOfColumns = 5;
                String regexForCSV = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
                String[] values = line.split(regexForCSV, -1);
                if (values.length != expectedNumberOfColumns) {
                    throw new Exception("Make sure there's no comma in any cell");
                }

                // removes ""(quotes) from name if any
                String name = values[0].replace("\"", "");
                String previousSection = Section.parseSection(values[1]);

                List<String> choices = new ArrayList<>();

                for (int i = 2; i < values.length; i++) {
                    choices.add(Section.parseSection(values[i]));
                }

//                String firstChoice = Section.parseSection(values[2]);
//                String secondChoice = Section.parseSection(values[3]);
//                String thirdChoice = Section.parseSection(values[4]);
//                // int ability = Integer.parseInt(values[5]);
//
//                List<String> choices = List.of(firstChoice, secondChoice, thirdChoice);
                Member m = new Member(name, previousSection, choices);
                m.generatePoints(previousSectionWeight, choicesWeight);
                members.add(m);
            }
        }


        Section s = new Section(12, 12, 13, 11, 5, 3);
        SectionAllocator sa = new SectionAllocator(members, s);
        String allocation = sa.allocate();
        Files.write(Path.of("./allocation.txt"), Collections.singleton(allocation));
    }
}
