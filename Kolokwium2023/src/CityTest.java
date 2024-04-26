
/*
Napisz klasę CityTest zawierającą sparametryzowany test zawierający:
- poprawne dodanie węgla,
- nieudaną próbę dodania drewna spoza zasięgu miasta,
- poprawne dodanie ryb do miasta portowego,
- nieudaną próbę dodania ryb do miasta nieportowego.
Test będzie wymagał definicji miasta śródlądowego i morskiego, co można wykonać na wiele
sposobów, np. przez ustawienie ich jako pola klasy.
Test powinien przyjmować argumenty: miasto, zasób, oczekiwana wartość logiczna.
Sposób parametryzacji testu jest dowolny. Test, przy użyciu pakietowego dostępu do pola
City.resources powinien sprawdzać, czy testowany zasób został dodany do zbioru.

Dany jest plik map.svg zawierający przykładową mapę. Mapa zawiera niebieski prostokąt
reprezentujący wodę, który można pominąć w dalszych rozważaniach oraz za pomocą znaczników:
- polygon o kolorze zielonym - ląd,
- rect o kolorze czerwonym - miasto,
- circle o kolorze czarnym, brązowym, jasnoniebieskim - zasoby, odpowiednio węgiel, drewno,
ryby.
- text - nazwy miast.
Symbole zasobów nie będą rozważane w dalszej części zadania
 */


import java.util.ArrayList;
import java.util.List;

public class CityTest {
    private List<Resource> resourceList = new ArrayList<>();

    public void testAddRes(City city){
        city.addResourcesInRange(this.resourceList,6.0);
    }

    public CityTest() {
        this.resourceList.add(new Resource(Resource.Type.Coal , new Point(35.0,30.0)));
        this.resourceList.add(new Resource(Resource.Type.Wood , new Point(39.0,30.0)));
        this.resourceList.add(new Resource(Resource.Type.Fish , new Point(35.0,30.0)));
        this.resourceList.add(new Resource(Resource.Type.Fish , new Point(42.0,20.0)));
    }
}
