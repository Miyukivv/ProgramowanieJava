import java.util.ArrayList;
import java.util.List;
/*
zadanie
Napisz klasę Land dziedziczącą po klasie Polygon. Napisz w niej taki sam konstruktor jak w Polygon.
Na mapie znajdują się także miasta. Każde miasto otoczone jest murami w kształcie kwadratu,
o ścianach wychodzących na cztery strony świata (parami równoległe do osi układu współrzędnych).
Środek miasta leży na przecięciu przekątnych kwadratu.
 */
public class Land extends  Polygon{
    public Land(List<Point> listOfPoints) {
        super(listOfPoints);
    }

    public List<City> getCityList() {
        return cityList;
    }

    /*
    zadanie
    W klasie Land stwórz prywatną listę miast. Napisz metodę addCity(City), która doda miasto do tej
    listy. Miasto może zostać dodane wyłącznie jeżeli jego środek znajduje się na lądzie. W przeciwnym
    razie należy rzucić wyjątek RuntimeException, którego metoda getMessage() powinna wyświetlić
    nazwę miasta.
         */
    private List<City> cityList = new ArrayList<>();

/*Napisz metodę addCity(City), która doda miasto do tej
listy.*/
    public void addCity(City city) {
        /*Miasto może zostać dodane wyłącznie jeżeli jego środek znajduje się na lądzie.*/
        if (inside(city.center)) {
            cityList.add(city);
            System.out.println("City added: " + city.getName());
            city.setPort(isPort(city));

/*W przeciwnym
razie należy rzucić wyjątek RuntimeException, którego metoda getMessage() powinna wyświetlić
nazwę miasta.*/

        } else {
            throw new RuntimeException("City is not on land: " + city.getName());
        }
    }
    /*
    zadanie
Napisz test sprawdzający rzucanie i poprawność nazwy wyświetlanej przez getMessage().
Miasta portowe mają dostęp do wody.

Miasto jest miastem portowym, jeżeli co najmniej jeden z wierzchołków kwadratu stanowiącego mury miasta
znajduje się poza lądem.
Pole to powinna ustawiać metoda znająca zarówno obiekt City, jak i obiekt Land, na którym istnieje
lub do którego dodawane jest miasto. Samodzielnie wybierz odpowiednie położenie definicji
i wywołania oraz symbol tej metody.
Lądy i morza są pełne zasobów
     */
    private boolean isPort(City city){
        for (Point corner: City.walls(city.range, city.center)){
            if (!inside(corner)){ //jest poza lądem
                System.out.println("City "+ city.getName() + " is port");
                return true;
            }
        }
        return false;
    }

}
