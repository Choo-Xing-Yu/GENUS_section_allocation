package com.xingyu.GENUS_section_allocation;

import java.util.*;

public class Member {
    String name;
    HashMap<String, Double> points;
    String previousSection;
    List<String> choices;
    // int ability;

    private static String INVALID_SECTION_MESSAGE =
            "Invalid Section! Valid sections are " + Section.sections.toString();

    public Member(String name, String previousSection, List<String> choices) throws Exception {
        if (!Section.isParsed(previousSection)) {
            throw new Exception(
                    "Please use parseSection(section) method on previousSection! || " + name);
        }
        for (String choice : choices) {
            if (!Section.isParsed(choice)) {
                throw new Exception(
                        "Please use parseSection(section) method on choices! || " + name);
            }
        }
        this.name = name;
        this.points = new HashMap<>();
        this.points.put(Section.ALTO_ONE, 0.0);
        this.points.put(Section.ALTO_TWO, 0.0);
        this.points.put(Section.PRIME, 0.0);
        this.points.put(Section.BASS, 0.0);
        this.points.put(Section.CONTRABASS, 0.0);
        this.points.put(Section.GUITARRON, 0.0);
        if (!Section.isValidSection(previousSection)) {
            throw new Exception(
                    Member.INVALID_SECTION_MESSAGE + " || " + this.name + " || [previousSection]");
        }
        this.previousSection = previousSection;
        for (String section : choices) {
            if (!Section.isValidSection(section)) {
                throw new Exception(
                        Member.INVALID_SECTION_MESSAGE + " || " + this.name + " [choices]");
            }
        }
        this.choices = choices;
        // this.ability = ability;
        // this.generatePoints();
    }

    public void generatePoints(double previousSectionWeight, double choicesWeight)
            throws Exception {
        // if (ability < 0 || ability > 10) {
        // throw new Exception("Ability should be [0, 10]");
        // }
        // Factors
        // 1) previousSection [0 - 10]
        // 2) choices [0 - 10]
        // 3) ability [0 - 10]

        // // Weightage
        // // 50% - previousSectionWeight
        // double previousSectionWeight = 0.5;

        // // 50% - choicesWeight
        // double choicesWeight = 0.5;

        // // 30% - abilityWeight
        // double abilityWeight = 0.3;

        // =============================== choices ===============================
        double choicesPoint = 10 * choicesWeight;
        for (int i = 0; i < choices.size(); i++) {
            String section = choices.get(i);
            Double prevPoint = this.points.get(section);
            this.points.put(section, prevPoint + choicesPoint);
            choicesPoint /= 2;
        }
        // =============================== /choices ===============================

        // // =============================== ABILITY ===============================
        // double abilityPoint = ability * abilityWeight;
        // for (String section : Section.sections) {
        // Double prevPoint = this.points.get(section);
        // this.points.put(section, prevPoint + abilityPoint);
        // }
        // // =============================== /ABILITY ===============================

        // =============================== PREVIOUS_SECTION ===============================
        double previousSectionSamePoint = 10 * previousSectionWeight;
        double previousSectionSameFamilyPoint = 5 * previousSectionWeight;
        for (int i = 0; i < choices.size(); i++) {
            String section = choices.get(i);
            Double prevPoint = this.points.get(section);
            if (section.equals(previousSection)) {
                this.points.put(section, prevPoint + previousSectionSamePoint);
            } else if (Section.isSameFamily(section, previousSection)) {
                this.points.put(section, prevPoint + previousSectionSameFamilyPoint);
            }
        }
        // =============================== /PREVIOUS_SECTION ===============================
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Member) {
            Member other = (Member) o;
            return other.name.equals(this.name);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
