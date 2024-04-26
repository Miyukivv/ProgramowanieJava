import java.util.ArrayList;
import java.util.List;
/*
zad 2
Zdefiniuj klasę Polygon, która będzie posiadała prywatną listę punktów. Lista punktów powinna być
ustawiana przez konstruktor. Wielokąt zbudowany jest z krawędzi łączących kolejne punkty w liście
oraz ostatni z pierwszym punktem z listy.
 */
public class Polygon {

    List<Point> pointList;

    public Polygon(List<Point> listOfPoints) {
        this.pointList = listOfPoints;
    }

    /*
zad 3
W klasie Polygon zdefiniuj publiczną metodę inside(Point), która zwraca wartość logiczną określającą,
czy dany argumentem metody punkt znajduje się w wielokącie, na rzecz którego wywoływana jest
metoda. Zaimplementuj w niej algorytm podany pseudokodem:

Dane są: wielokąt poly i sprawdzany punkt point.
counter = 0
Dla każdej pary (pa, pb) punktów tworzących krawędź wielokąta poly:
Jeżeli pa.y > pb.y:
Zamień pa z pb
Jeżeli pa.y < point.y < pb.y:
d = pb.x - pa.x
Jeżeli d == 0:
x = pa.x
W przeciwnym razie:
a = (pb.y - pa.y) / d
b = pa.y - a * pa.x
x = (point.y - b) / a
Jeżeli x < point.x:
counter++
Zwróć prawdę, jeżeli counter jest nieparzysty, a fałsz w przeciwnym przypadku.
     */
    public boolean inside(Point point) {
        int counter = 0;
        int N = pointList.size();

        /*
W każdej iteracji, pa i pb to kolejne punkty z listy, tworzące krawędź wielokąta.
Warto zauważyć, że ostatni punkt łączy się z pierwszym punktem.
         */

        for (int i = 0; i < N; i++) {
            Point pa = pointList.get(i);
            Point pb = pointList.get((i + 1) % N);

            /*
Warunek if (pa.y > pb.y) zapewnia, że pa jest punktem o mniejszej wartości y, a pb o większej.
Jeśli to nie jest spełnione, zamieniamy pa z pb, aby zachować ten porządek
             */
            if (pa.y > pb.y) {
                Point temp = pa;
                pa = pb;
                pb = temp;
            }

            /*
 Następnie, sprawdzamy, czy point.y (współrzędna y punktu, który chcemy sprawdzić)
 jest pomiędzy pa.y i pb.y (współrzędne y punktów tworzących aktualnie rozpatrywaną krawędź).
             */
            if (pa.y < point.y && point.y <= pb.y) {
                double d = pb.x - pa.x;
                double x;


                /*Jeśli warunek jest spełniony, to znaczy, że linia od pa do pb przecina poziomo linie y punktu point,
                więc możemy obliczyć współrzędną x punktu przecięcia.*/
                if (d == 0) {
                    x = pa.x;
                }
                else {
                    double a = (pb.y - pa.y) / d;
                    double b = pa.y - a * pa.x;
                    x = (point.y - b) / a;
                }

                /*
Następnie, sprawdzamy czy współrzędna x punktu przecięcia jest mniejsza niż point.x.
Jeśli tak, to znaczy, że linia przechodzi po lewej stronie punktu point, więc zwiększamy licznik counter.
                 */



                if (x < point.x) {
                    counter++;
                }
            }
            /*
Po zakończeniu iteracji sprawdzamy, czy liczba przecięć jest parzysta. Jeśli tak, to punkt znajduje się poza wielokątem (na zewnątrz),
więc zwracamy false. W przeciwnym razie zwracamy true, co oznacza, że punkt znajduje się wewnątrz wielokąta.
             */
        }
        return counter % 2 != 0;
    }
}
