import java.util.ArrayList;

public class Polygon implements Shape{
    private ArrayList<Vec2> points;
    private Style style;
    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
    public ArrayList<Vec2> getPoints() {
        return points;
    }
    public void setPoints(ArrayList<Vec2> points) {
        this.points = points;
    }

    public Polygon(ArrayList<Vec2> points, Style style) {
        this.style = style;
        this.points = points;
    }
    public Polygon(){};
    public Polygon(Polygon other) {
        this.style = new Style(other.getStyle());
        this.points = new ArrayList<>();
        for (Vec2 point : other.getPoints()) {
            this.points.add(new Vec2(point.getX(), point.getY()));
        }
    }
    public String toSvg() {
        String code = "\n";
        code += "\t";
        code += "<polygon points=\"";
        for (Vec2 p : points){
            code += p.getX() + "," + p.getY() + " ";
        }
        code+="\"";
        code+=getStyle().toSvg();
        return  code;
    }

    public  void translate(int x, int y){
        ArrayList<Vec2> newPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++){
            int newx = points.get(i).getX() + x;
            int newy = points.get(i).getY() + y;
            newPoints.set(i,new Vec2(newx, newy));
        }
        setPoints(newPoints);
    }
}
