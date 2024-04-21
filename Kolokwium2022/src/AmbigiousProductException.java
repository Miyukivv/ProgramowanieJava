import java.util.ArrayList;
import java.util.List;

public class AmbigiousProductException extends Exception{
    public AmbigiousProductException(List<String> products) {
        super(String.format("Błąd z: %s",products));
    }
}
