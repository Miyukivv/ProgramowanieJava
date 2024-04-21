import java.util.ArrayList;

public class CountryWithProvinces extends Country{

    private ArrayList<Country> provinces;

    public CountryWithProvinces(String name, ArrayList<Country> provinces) {
        super(name);
        if (provinces == null){
            provinces=new ArrayList<>();
        }
        else {
            provinces=this.provinces;
        }
    }


}
