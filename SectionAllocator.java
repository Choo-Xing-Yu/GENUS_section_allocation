import java.util.*;

public class SectionAllocator {
    List<Member> members;
    PriorityQueue<Member> altoOnePQ;
    PriorityQueue<Member> altoTwoPQ;
    PriorityQueue<Member> primePQ;
    PriorityQueue<Member> bassPQ;
    PriorityQueue<Member> contrabassPQ;
    PriorityQueue<Member> guitarronPQ;

    List<PriorityQueue<Member>> listOfPQ;

    HashMap<String, PriorityQueue<Member>> sectionToPQ;

    Section section;

    HashMap<String, List<Member>> allocations;

    int NUMBER_OF_MEMBERS;
    public int numAltoOneMember;
    public int numAltoTwoMember;
    public int numPrimeMember;
    public int numBassMember;
    public int numContrabassMember;
    public int numGuitarronMember;

    HashMap<String, Integer> sectionToPax;

    public SectionAllocator(List<Member> members, Section section) {
        this.NUMBER_OF_MEMBERS = members.size();
        this.section = section;
        this.members = members;
        this.allocations = new HashMap<>();
        this.allocations.put(Section.ALTO_ONE, new ArrayList<>());
        this.allocations.put(Section.ALTO_TWO, new ArrayList<>());
        this.allocations.put(Section.PRIME, new ArrayList<>());
        this.allocations.put(Section.BASS, new ArrayList<>());
        this.allocations.put(Section.CONTRABASS, new ArrayList<>());
        this.allocations.put(Section.GUITARRON, new ArrayList<>());

        this.altoOnePQ = new PriorityQueue<>(new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                return (int) Math.floor(m2.points.get(Section.ALTO_ONE) - m1.points.get(Section.ALTO_ONE));
            }
        });

        this.altoTwoPQ = new PriorityQueue<>(new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                return (int) Math.floor(m2.points.get(Section.ALTO_TWO) - m1.points.get(Section.ALTO_TWO));
            }
        });

        this.primePQ = new PriorityQueue<>(new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                return (int) Math.floor(m2.points.get(Section.PRIME) - m1.points.get(Section.PRIME));
            }
        });

        this.bassPQ = new PriorityQueue<>(new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                return (int) Math.floor(m2.points.get(Section.BASS) - m1.points.get(Section.BASS));
            }
        });

        this.contrabassPQ = new PriorityQueue<>(new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                return (int) Math.floor(m2.points.get(Section.CONTRABASS) - m1.points.get(Section.CONTRABASS));
            }
        });

        this.guitarronPQ = new PriorityQueue<>(new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                return (int) Math.floor(m2.points.get(Section.GUITARRON) - m1.points.get(Section.GUITARRON));
            }
        });

        this.sectionToPQ = new HashMap<>();
        this.sectionToPQ.put(Section.ALTO_ONE, this.altoOnePQ);
        this.sectionToPQ.put(Section.ALTO_TWO, this.altoTwoPQ);
        this.sectionToPQ.put(Section.PRIME, this.primePQ);
        this.sectionToPQ.put(Section.BASS, this.bassPQ);
        this.sectionToPQ.put(Section.CONTRABASS, this.contrabassPQ);
        this.sectionToPQ.put(Section.GUITARRON, this.guitarronPQ);

        this.listOfPQ = new ArrayList<>();
        this.listOfPQ.addAll(List.of(altoOnePQ, altoTwoPQ, primePQ, bassPQ, contrabassPQ, guitarronPQ));

        double a1Percent = 0.2;
        double a2Percent = 0.2;
        double primePercent = 0.3;
        double bassPercent = 0.2;
        double contraPercent = 0.05;
        double guitarronPercent = 0.05;

        this.numAltoOneMember = Math.min(section.numAltoOneGuitar, (int) Math.floor(a1Percent * this.NUMBER_OF_MEMBERS));
        this.numAltoTwoMember = Math.min(section.numAltoTwoGuitar, (int) Math.floor(a2Percent * this.NUMBER_OF_MEMBERS));
        this.numPrimeMember = Math.min(section.numPrimeGuitar, (int) Math.floor(primePercent * this.NUMBER_OF_MEMBERS));
        this.numBassMember = Math.min(section.numBassGuitar, (int) Math.floor(bassPercent * this.NUMBER_OF_MEMBERS));
        this.numContrabassMember = Math.min(section.numContrabassGuitar, (int) Math.floor(contraPercent * this.NUMBER_OF_MEMBERS));
        this.numGuitarronMember = Math.min(section.numGuitarron, (int) Math.floor(guitarronPercent * this.NUMBER_OF_MEMBERS));

        int numAllocatedMembers = this.numAltoOneMember + this.numAltoTwoMember + this.numPrimeMember + this.numBassMember + this.numContrabassMember + this.numGuitarronMember;
        if (numAllocatedMembers != this.NUMBER_OF_MEMBERS) {
            // fill up alto 1
            this.numAltoOneMember += this.NUMBER_OF_MEMBERS - numAllocatedMembers;
        }

        this.sectionToPax = new HashMap<>();
        this.sectionToPax.put(Section.ALTO_ONE, this.numAltoOneMember);
        this.sectionToPax.put(Section.ALTO_TWO, this.numAltoTwoMember);
        this.sectionToPax.put(Section.PRIME, this.numPrimeMember);
        this.sectionToPax.put(Section.BASS, this.numBassMember);
        this.sectionToPax.put(Section.CONTRABASS, this.numContrabassMember);
        this.sectionToPax.put(Section.GUITARRON, this.numGuitarronMember);
    }

    public String allocate() throws Exception {
        for(Member m : this.members) {
            m.generatePoints();
            this.altoOnePQ.add(m);
            this.altoTwoPQ.add(m);
            this.primePQ.add(m);
            this.bassPQ.add(m);
            this.contrabassPQ.add(m);
            this.guitarronPQ.add(m);
        }

        for (String section : Section.sections) {
            List<Member> allocation = this.allocations.get(section);
            int maxPax = this.sectionToPax.get(section);
            PriorityQueue<Member> pq = this.sectionToPQ.get(section);
            while (allocation.size() < maxPax && !pq.isEmpty()) {
                Member m = pq.poll();
                for (PriorityQueue<Member> pqs : this.listOfPQ) {
                    pqs.remove(m);
                }
                allocation.add(m);
            }
        }
        
        String report = "";
        String a1 = Section.ALTO_ONE + "(" + Integer.toString(this.numAltoOneMember) + ")" + "\n";
        for (Member a1Players : this.allocations.get(Section.ALTO_ONE)) {
            a1 += a1Players.toString() + "\n";
        }
        String a2 = Section.ALTO_TWO + "(" + Integer.toString(this.numAltoTwoMember) + ")" + "\n";
        for (Member a2Players : this.allocations.get(Section.ALTO_TWO)) {
            a2 += a2Players.toString() + "\n";
        }
        String prime = Section.PRIME + "(" + Integer.toString(this.numPrimeMember) + ")" + "\n";
        for (Member primePlayers : this.allocations.get(Section.PRIME)) {
            prime += primePlayers.toString() + "\n";
        }
        String bass = Section.BASS + "(" + Integer.toString(this.numBassMember) + ")" + "\n";
        for (Member bassPlayers : this.allocations.get(Section.BASS)) {
            bass += bassPlayers.toString() + "\n";
        }
        String contrabass = Section.CONTRABASS + "(" + Integer.toString(this.numContrabassMember) + ")" + "\n";
        for (Member contraPlayer : this.allocations.get(Section.CONTRABASS)) {
            contrabass += contraPlayer.toString() + "\n";
        }
        String guitarron = Section.GUITARRON + "(" + Integer.toString(this.numGuitarronMember) + ")" + "\n";
        for (Member guitarronPlayer : this.allocations.get(Section.GUITARRON)) {
            guitarron += guitarronPlayer.toString() + "\n";
        }
        report += a1 + "\n" + a2 + "\n" + prime + "\n" + bass + "\n" + contrabass + "\n" + guitarron;

        return report;
    }
}
