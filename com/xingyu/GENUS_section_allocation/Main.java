package com.xingyu.GENUS_section_allocation;

import java.io.*;
import java.util.*;
import java.math.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
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
        try (BufferedReader br = new BufferedReader(new FileReader("./21_22s1.csv"))) {
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
                String firstChoice = Section.parseSection(values[2]);
                String secondChoice = Section.parseSection(values[3]);
                String thirdChoice = Section.parseSection(values[4]);
                // int ability = Integer.parseInt(values[5]);

                List<String> choices = List.of(firstChoice, secondChoice, thirdChoice);
                Member m = new Member(name, previousSection, choices);
                m.generatePoints(previousSectionWeight, choicesWeight);
                members.add(m);
            }
        }


        Section s = new Section(12, 12, 13, 11, 5, 3);
        SectionAllocator sa = new SectionAllocator(members, s);
        System.out.println(sa.allocate());
    }
}
