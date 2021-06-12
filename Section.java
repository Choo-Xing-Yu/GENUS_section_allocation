import java.util.*;

public class Section {
    static String ALTO_ONE = "ALTO_ONE";
    static String ALTO_TWO = "ALTO_TWO";
    static String PRIME = "PRIME";
    static String BASS = "BASS";
    static String CONTRABASS = "CONTRABASS";
    static String GUITARRON = "GUITARRON";
    static String INVALID_SECTION = "INVALID_SECTION";

    int numAltoOneGuitar;
    int numAltoTwoGuitar;
    int numPrimeGuitar;
    int numBassGuitar;
    int numContrabassGuitar;
    int numGuitarron;

    static List<String> sections = List.of(Section.ALTO_ONE, Section.ALTO_TWO, Section.PRIME,
            Section.BASS, Section.CONTRABASS, Section.GUITARRON);


    static List<Set<String>> sectionFamilies = List.of(Set.of(Section.ALTO_ONE, Section.ALTO_TWO),
            Set.of(Section.PRIME), Set.of(Section.BASS, Section.CONTRABASS, Section.GUITARRON));

    static boolean isSameFamily(String section, String another) throws Exception {
        if (!isValidSection(section) || !isValidSection(another)) {
            throw new Exception("Please enter a valid section! Here's the list of valid sections: "
                    + Section.sections.toString());
        }
        for (Set<String> family : Section.sectionFamilies) {
            if (family.contains(section) && family.contains(another)) {
                return true;
            }
        }
        return false;
    }

    static boolean isValidSection(String section) {
        return Section.sections.contains(section.toUpperCase());
    }

    static String parseSection(String section) {
        String sectionUpper = section.toUpperCase();
        if (sectionUpper.contains("ALTO")
                && (sectionUpper.contains("ONE") || sectionUpper.contains("1"))) {
            return Section.ALTO_ONE;
        }
        if (sectionUpper.contains("ALTO")
                && (sectionUpper.contains("TWO") || sectionUpper.contains("2"))) {
            return Section.ALTO_TWO;
        }
        if (sectionUpper.equals(Section.PRIME)) {
            return Section.PRIME;
        }
        if (sectionUpper.equals(Section.BASS)) {
            return Section.BASS;
        }
        if (sectionUpper.equals(Section.CONTRABASS)) {
            return Section.CONTRABASS;
        }
        if (sectionUpper.equals(Section.GUITARRON)) {
            return Section.GUITARRON;
        }
        return Section.INVALID_SECTION;
    }

    Section(int numAltoOneGuitar, int numAltoTwoGuitar, int numPrimeGuitar, int numBassGuitar,
            int numContrabassGuitar, int numGuitarron) {
        this.numAltoOneGuitar = numAltoOneGuitar;
        this.numAltoTwoGuitar = numAltoTwoGuitar;
        this.numPrimeGuitar = numPrimeGuitar;
        this.numBassGuitar = numBassGuitar;
        this.numContrabassGuitar = numContrabassGuitar;
        this.numGuitarron = numGuitarron;
    }
}
