import java.util.ArrayList;
import java.util.Locale;

public class GradientFillShapeDecorator extends ShapeDecorator{

    private static int index=0;
    public GradientFillShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }



    public String toSvg(){
        String code=super.toSvg();
        code+=String.format(Locale.ENGLISH," fill=\"url(#g%d)\" ", index);
        return code;
    }

    public   static class Builder{
        private ArrayList<String> stops=new ArrayList<>();
        private SvgScene scene;
        public Builder stopAdd(double offset, String color){
            String code=String.format(Locale.ENGLISH,"\t\t<stop offset=\"%f\" style=\"stop-color:%s\" />\n", offset, color);
            stops.add(code);
            return this;
        }

        public String build(){
            index++;
            String code=String.format(Locale.ENGLISH,"\t<linearGradient id=\"g%d\" >\n", index);
            for (String stop : stops){
                code+=stop;
            }
            code+="\t</linearGradient>";
            scene=SvgScene.getInstance();
            scene.addDef(code);

            return code;
        }
    }
}
