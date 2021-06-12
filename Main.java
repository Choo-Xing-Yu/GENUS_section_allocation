import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        Member alto1player1 = new Member("Alice", Section.ALTO_ONE, List.of(Section.ALTO_ONE, Section.ALTO_TWO, Section.BASS), 7);
        Member alto1player2 = new Member("Bob", Section.ALTO_ONE, List.of(Section.ALTO_ONE, Section.ALTO_TWO, Section.BASS), 7);
        Member alto1player3 = new Member("Carrie", Section.ALTO_ONE, List.of(Section.ALTO_ONE, Section.ALTO_TWO, Section.BASS), 7);
        Member alto1player4 = new Member("Denise", Section.ALTO_ONE, List.of(Section.ALTO_ONE, Section.ALTO_TWO, Section.BASS), 7);
        Member alto1player5 = new Member("Elan", Section.ALTO_ONE, List.of(Section.ALTO_ONE, Section.ALTO_TWO, Section.BASS), 7);
        Member alto1player6 = new Member("Fengyue", Section.ALTO_ONE, List.of(Section.ALTO_ONE, Section.ALTO_TWO, Section.BASS), 7);

        Member xingyu = new Member("Xing Yu", Section.BASS, List.of(Section.BASS, Section.CONTRABASS, Section.GUITARRON), 10);
        Member raine = new Member("Raine", Section.BASS, List.of(Section.BASS, Section.ALTO_ONE, Section.ALTO_TWO), 4);

        Section s = new Section(12, 12, 13, 11, 5, 3);

        List<Member> xs = List.of(alto1player1, alto1player2, alto1player3, alto1player4, alto1player5, alto1player6, xingyu, raine);
        
        SectionAllocator sa = new SectionAllocator(xs, s);
        System.out.println(sa.allocate());
        boolean x = true;
    }
}
