import java.util.Locale;

public class DropShadowDecorator extends ShapeDecorator{
    private static int index=0;
    private static boolean shadowAddedToScene=false;
    public DropShadowDecorator(Shape decoratedShape) {
        super(decoratedShape);
        this.index++;
        SvgScene scene=SvgScene.getInstance();
        scene.addDef(getShadow());
        shadowAddedToScene=true;
    }
    private String getShadow(){

        String code=String.format(Locale.ENGLISH,"\t<filter id=\"f%d\" x=\"-100%%\" y=\"-100%%\" width=\"300%%\" height=\"300%%\">\n" +
        "\t\t<feOffset result=\"offOut\" in=\"SourceAlpha\" dx=\"5\" dy=\"5\" />\n" +
        "\t\t<feGaussianBlur result=\"blurOut\" in=\"offOut\" stdDeviation=\"5\" />\n" +
        "\t\t<feBlend in=\"SourceGraphic\" in2=\"blurOut\" mode=\"normal\" />\n" +
        "\t</filter>", index);
        return code;

    }
    public String toSvg(){
        String code=super.toSvg();
        code+=String.format(Locale.ENGLISH," filter=\"url(#f%d)\" ", index);
        return code;
    }


}
