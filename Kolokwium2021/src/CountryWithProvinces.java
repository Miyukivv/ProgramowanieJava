import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

    public int provincesGet(String path) {

        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] partsOfCountries = line.split(";");

            line = br.readLine();

            String[] partsOfProvinces = line.split(";");

            for (int i = 0; i < partsOfCountries.length; i++) {
                if (partsOfCountries[i].equals(getName())) {
                    if (partsOfProvinces[i] != null) {
                        CountryWithoutProvinces province = new CountryWithoutProvinces(partsOfProvinces[i]);
                        provinces.add(province);
                    }
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
