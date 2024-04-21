import java.util.Locale;

public class Ellipse implements Shape{
    private Vec2 middle;
    private double radiusX;
    private double radiusY;
    private Style style;

    public Ellipse(Vec2 middle, double radiusX, double radiusY, Style style){
        this.radiusX=radiusX;
        this.radiusY=radiusY;
        this.middle=middle;
        this.style=style;
    }

    @Override
    public String toSvg() {
        String code= String.format(Locale.ENGLISH, "<ellipse cx=\"%f\" cy=\"%f\" rx=\"%f\" ry=\"%f\"", middle.getX(), middle.getY(), radiusX, radiusY);

        return code;
    }
}
