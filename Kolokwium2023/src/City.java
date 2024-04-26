import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
zadanie 4
Napisz klasę City dziedziczącą po Polygon. W klasie zdefiniuj publiczną ostateczną zmienną center -
punkt będący środkiem miasta oraz prywatną nazwę miasta. Napisz publiczny konstruktor, takie dwa
argumenty oraz liczbę zmiennoprzecinkową - długość ściany muru.

Konstruktor powinien zapisać
w polu klasy środek miasta oraz nazwę, a w liście punktów odziedziczonej z klasy Polygon,
wierzchołki kwadratu stanowiącego mury.

Uwaga! Ten krok da się rozwiązać bez zmiany modyfikatora dostępu do listy wierzchołków wielokąta
na chroniony. Można dokonać takiej zmiany, ale takie rozwiązanie nie będzie w pełni punktowane.
Miasta mogą znajdować się na lądzie - nie na wodzie.


zadanie
W klasie City dodaj prywatne pole logiczne port określające, czy miasto jest portowe.

zadanie
Załóżmy bez sprawdzenia, że węgiel i drewno znajdują się na lądzie, a ryby w wodzie.
W klasie City utwórz zbiór (Set) obiektów Resource.Type o nazwie resources i dostępie pakietowym.
W tej samej klasie napisz metodę addResourcesInRange, która przyjmie listę obiektów Resource
oraz liczbę zmiennoprzecinkową range i umieści w zbiorze typy tych zasobów, które znajdują się
w odległości nie większej niż range od środka miasta. Ryby powinny być uwzględniane wyłącznie
w miastach portowych

 */
public class City extends Polygon{
    public final Point center; //punkt będący środkiem miasta
    private final String name;
    private boolean port; //dodane w dalszej części: określa czy miasto jest portowe
    public double range;

 /*
 W klasie City utwórz zbiór (Set) obiektów Resource.Type o nazwie resources i dostępie pakietowym.

HashSet<Resource.Type> oznacza, że tworze HashSet,
który będzie przechowywał obiekty typu Resource.Type
*/
    HashSet<Resource.Type> resources = new HashSet<>();

    /*
Konstruktor powinien zapisać
w polu klasy środek miasta oraz nazwę, a w liście punktów odziedziczonej z klasy Polygon,
wierzchołki kwadratu stanowiącego mury
     */
    public City(List<Point> listOfPoints, Point center, String name, boolean port, double range) {
        super(walls(range,center)); //wierzchołki kwadratu stanowiącego mury
        this.center = center;
        this.name = name;
        this.port = port;
        this.range = range;
    }

    public Point getCenter() {
        return center;
    }

    public String getName() {
        return name;
    }

    public boolean isPort() {
        return port;
    }

    public double getRange() {
        return range;
    }

    public HashSet<Resource.Type> getResources() {
        return resources;
    }

    public void setPort(boolean port) {
        this.port = port;
    }

    public static List<Point> walls(double range, Point center){
        List<Point> pktList = new ArrayList<>();
        Point p1 = new Point(center.x-(range/2),center.y-(range/2));
        Point p2 = new Point(center.x+(range/2),center.y-(range/2));
        Point p3 = new Point(center.x+(range/2),center.y+(range/2));
        Point p4 = new Point(center.x-(range/2),center.y+(range/2));
        pktList.add(p1);
        pktList.add(p2);
        pktList.add(p3);
        pktList.add(p4);
        return pktList;
    }

    /*
W tej samej klasie napisz metodę addResourcesInRange, która przyjmie listę obiektów Resource
oraz liczbę zmiennoprzecinkową range i umieści w zbiorze typy tych zasobów, które znajdują się
w odległości nie większej niż range od środka miasta. Ryby powinny być uwzględniane wyłącznie
w miastach portowych
     */

    public void addResourcesInRange(List<Resource> resourceList,double range){
        for (Resource r: resourceList) {
            double dx = r.location.x - center.x;
            double dy = r.location.y - center.y;
            double d = Math.sqrt(dx * dx - dy * dy);
            if (d <= range) {
                if (r.type == Resource.Type.Fish) {
                    if (this.port) {
                        resources.add(r.type);
                        System.out.println(r.type + " added to " + name);
                    } else {
                        System.out.println(r.type + " can't be added to " + name + " | Reason: city is not port");
                    }
                } else {
                    resources.add(r.type);
                    System.out.println(r.type + " added to " + name);
                }
            } else {
                System.out.println(r.type + " can't be added to " + name + " | Reason: out of range");
            }
        }
    }
}
