import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Person> people = Person.fromCsvFile("family(2).csv");
//        String output = Person.generateDiagram(people);
//        System.out.println(output);

        CustomList<String> newList = new CustomList<>();
        newList.addLast("Hello");
        System.out.println(newList.getLast());
        newList.addLast("World");
        System.out.println((newList.getLast()));
        Iterator<String> iter = newList.iterator();
        //tu przechowuje hello i world
        String text=iter.next();
        System.out.println(text);
/*
Hello ma nastepnika w postaci World, ale za world juz nic nie ma
wiec nasze hasNext z hello przeszlo od razu na world, dlatego w petli
nie wyswietlil sie zaden napis
 */
        while (iter.hasNext()){
            text=iter.next();
            System.out.println(text);
        }


    }

}