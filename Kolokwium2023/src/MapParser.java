import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
zadanie
Zapoznaj się z dołączoną klasą MapParser służącą do parsowania tego pliku. Klasa wykorzystuje
bibliotekę Jackson Dataformat XML. Dołącz ją do projektu. W klasie MapParser znajduje się lista
obiektów typu Label zawierającego reprezentację znaczników <text>. Metody parse i parseText
prezentują proces konwersji zawartości znacznika na obiekt.

W klasie MapParser stwórz analogiczne do istniejącej listy nazw, nowe listy lądów i miast i zapełnij je
wzorując się na istniejącym kodzie. Napisz publiczny akcesor do listy lądów.

Uwaga! Podczas wczytywania miasta jego nazwa nie jest znana. Można ją tymczasowo ustawić jako
null. Wartość x i y w znaczniku rect definiuje lewy, górny wierzchołek prostokąta. Pamiętaj
o obliczeniu pozycji środka miasta.

zadanie
W klasie MapParser zaimplementuj metodę addCitiesToLands. Metoda powinna pracować na liście
lądów i miast w obiekcie klasy MapParser. Dla każdego lądu, dodać do jego prywatnej listy miast,
miasta, których środki znajdują się na tym lądzie.
 */
public class MapParser {

    static public final class Svg {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("rect")
        private List<Map<String, String>> rects;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("polygon")
        private List<Map<String, String>> polygons;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("text")
        private List<Map<String, String>> texts;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("circle")
        private List<Map<String, String>> circles;
    }

    private record Label(Point point, String text) {}
    private List<Label> labels = new ArrayList<>();
    private List<Land> lands = new ArrayList<>();
    private List<City> cities = new ArrayList<>();

    public List<Land> getLands() {
        return lands;
    }

    public void setLands(List<Land> lands) {
        this.lands = lands;
    }

    private void parseText(Map<String, String> params) {
        addLabel(params.get(""), new Point (Double.parseDouble(params.get("x")), Double.parseDouble(params.get("y"))));
    }

    private void addLabel(String text, Point bottomLeft) {

        labels.add(new Label(bottomLeft, text));
    }
    public void addCity(String text, Point bottomLeft,double range) {
        cities.add(new City(bottomLeft,text,range));
    }
    public void addLand(List<Point> points) {
        lands.add(new Land(points));
    }


    /*W klasie MapParser zaimplementuj metodę matchLabelsToTowns. Metoda powinna dla każdego
miasta w liście miast przyporządkować najbliższy obiekt Label. Konieczny będzie publiczny mutator
nazwy miasta w klasie City.
    void matchLabelsToTowns() {
        // Tutaj mam dodać ten kod
    }

    /*
    W klasie MapParser zaimplementuj metodę addCitiesToLands. Metoda powinna pracować na liście
lądów i miast w obiekcie klasy MapParser. Dla każdego lądu, dodać do jego prywatnej listy miast,
miasta, których środki znajdują się na tym lądzie.
     */
    void addCitiesToLands(City city,Land land) {
        if (!lands.contains(land)){
            lands.add(land);
            if (land.inside(city.center)){
                lands.getLast().addCity(city);
            }
        }
        else{
            if (land.inside(city.center)){
                lands.getLast().addCity(city);
            }
        }
    }

    void parse(String path) {
        XmlMapper xmlMapper = new XmlMapper();
        File file = new File(path);
        try {
            Svg svg = xmlMapper.readValue(file, Svg.class);
            for(var item : svg.texts)
                parseText(item);

            // TODO: Krok 13
//          //matchLabelsToTowns();
//          //addCitiesToLands();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
