import java.util.*;

public class Member {
    String name;
    HashMap<String, Double> points;
    String previousSection;
    List<String> choice;
    int ability;

    public Member(String name, String previousSection, List<String> choice, int ability) throws Exception{
        this.name = name;
        this.points = new HashMap<>();
        this.points.put(Section.ALTO_ONE, 0.0);
        this.points.put(Section.ALTO_TWO, 0.0);
        this.points.put(Section.PRIME, 0.0);
        this.points.put(Section.BASS, 0.0);
        this.points.put(Section.CONTRABASS, 0.0);
        this.points.put(Section.GUITARRON, 0.0);
        this.previousSection = previousSection;
        this.choice = choice;
        this.ability = ability;
    }

    public void generatePoints() throws Exception {
        if (!Section.isValidSection(previousSection)) {
            throw new Exception(previousSection + " is not a valid section! [previousSection]");
        }
        if (ability < 0 || ability > 10) {
            throw new Exception("Ability should be [0, 10]");
        }
        // Factors
        // 1) previousSection [0 - 10]
        // 2) choice [0 - 10]
        // 3) ability [0 - 10]

        // Weightage 
        // 40% - previousSectionWeight
        double previousSectionWeight = 0.4;

        // 40% - choiceWeight
        double choiceWeight = 0.4;

        // 20% - abilityWeight
        double abilityWeight = 0.2;

        // =============================== CHOICE ===============================
        double choicePoint = 10 * choiceWeight;
        for(int i = 0; i < choice.size(); i++) {
            String section = choice.get(i);
            if (!Section.isValidSection(section)) {
                throw new Exception(section + " is not a valid section! [choice]");
            }
            Double prevPoint = this.points.get(section);
            this.points.put(section, prevPoint + choicePoint);
            choicePoint /= 2;
        }
        // =============================== /CHOICE ===============================

        // =============================== ABILITY ===============================
        double abilityPoint = ability * abilityWeight;
        for(String section : Section.sections) {
            Double prevPoint = this.points.get(section);
            this.points.put(section, prevPoint + abilityPoint);
        }
        // =============================== /ABILITY ===============================

        // =============================== PREVIOUS_SECTION ===============================
        double previousSectionSamePoint = 10 * previousSectionWeight;
        double previousSectionSameFamilyPoint = 5 * previousSectionWeight;
        for(int i = 0; i < choice.size(); i++) {
            String section = choice.get(i);
            if (!Section.isValidSection(section)) {
                throw new Exception(section + " is not a valid section! [choice]");
            }
            Double prevPoint = this.points.get(section);
            if (section.equals(previousSection)) {
                this.points.put(section, prevPoint + previousSectionSamePoint);
            } else if (Section.isSameFamily(section, previousSection)){
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