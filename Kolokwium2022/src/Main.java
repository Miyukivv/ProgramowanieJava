import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws AmbigiousProductException {

        //File file1=new File("food/buraki.csv");
        Path garniak = Path.of("nonfood/garnitur.csv");

        Product garnitur = NonFoodProduct.fromCsv(garniak);
        System.out.println(garnitur.getPrice(2010, 5));

        Path cebulaa = Path.of("food/cebula.csv");
        FoodProduct cebula = FoodProduct.fromCsv(cebulaa);
        System.out.println(cebula.getPrice(2010, 5, "LUBELSKIE"));

        Path jajaa = Path.of("food/jaja.csv");
        Product jaja = FoodProduct.fromCsv(jajaa);
        System.out.println(jaja.getPrice(2010, 5));

        Product.addProducts(FoodProduct::fromCsv, Path.of("food/buraki.csv"));
        Product.addProducts(NonFoodProduct::fromCsv, Path.of("nonfood/mydlo.csv"));
        Product.addProducts(FoodProduct::fromCsv, Path.of("food/chleb_pszenno-zytni.csv"));
        Product.addProducts(FoodProduct::fromCsv, Path.of("food/mieso_wieprzowe_bez_kosci.csv"));
        Product.addProducts(FoodProduct::fromCsv, Path.of("food/mieso_wolowe_z_koscia.csv"));


        for (Product p : Product.getListOfProducts()) {
            System.out.println(p.getName());
        }

        Cart cart = new Cart();
        cart.addProduct(jaja, 3);
        cart.getPrice(2010, 5);

        System.out.println(cart.getInflation(2010, 5, 2011, 5));


        // System.out.println(Product.getProduct("Mię").getName());


//        List<String> pps = new ArrayList<>();
//        pps.add(jaja.getName());
//        pps.add(cebula.getName());
//        pps.add(garnitur.getName());

//        try {
//            throw new AmbigiousProductException(pps);
//        } catch (AmbigiousProductException e) {
//            throw new RuntimeException(e);
//        }


        //System.out.println(file1.exists());

    }
}