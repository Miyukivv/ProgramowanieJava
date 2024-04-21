import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> cart=new ArrayList<>();

    public void addProduct(Product product, int amount){
        for (int i=0; i<amount; i++){
            cart.add(product);
        }
    }
    double getPrice(int year, int month){
        double valueOfCart=0;
        for (Product p : cart){
            valueOfCart+=p.getPrice(year,month);
        }
        return valueOfCart;
    }
    double getInflation(int year1, int month1, int year2, int month2) {
        double valueofCartInOneMonth = getPrice(year1, month1);
        double valueofCartInSecondMonth = getPrice(year2,month2);


        //(price2 - price1) / price1 * 100 / months * 12
        int valueOfMonths=(year2*12+month2)-(year1*12+month1);
        double valueOfInflation = (valueofCartInSecondMonth-valueofCartInOneMonth)/valueofCartInOneMonth*100/valueOfMonths*12;
        return valueOfInflation;
    }
}
