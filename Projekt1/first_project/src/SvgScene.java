import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class SvgScene {

    private ArrayList<Shape> shapes;
    private static SvgScene instance=null;

    private ArrayList<String> defs;
    private SvgScene() {
        shapes = new ArrayList<>();
        defs=new ArrayList<>();
    }

    public static SvgScene getInstance() {
        if (instance==null){
            instance=new SvgScene();
        }
        return instance;
    }

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    public void addDef(String def){
        defs.add(def);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }
    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }
    public void saveToFile(String filePath){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(toSvg());
            writer.close();
            System.out.println("Plik " + filePath + " został poprawnie zapisany!");
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas zapisywania do pliku: " + e.getMessage());
        }
    }

    public String toSvg(){
        String code = "<svg height=\"360\" width=\"360\" xmlns=\"http://www.w3.org/2000/svg\">";
        for (Shape poly : shapes){
            code += "\n\t";
            code += poly.toSvg();
            code+="/>";
        }


        code += "\n</svg>";
        return code;
    }

    public void saveHtml(String path){
        String content="<!DOCTYPE html>\n<html>\n<body>\n\n<svg viewBox=\"0 0 2000 1000\" xmlns=\"http://www.w3.org/2000/svg\">\n";
        content+="<defs>\n";

        for (String def : defs){
            content+=def+"\n";
        }
        content+="</defs>\n";
        for (Shape p : shapes){
            content+=p.toSvg();
            content+="/>\n";
        }
        content+="</svg>\n\n</body>\n</html>";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(content);
            writer.close();
            System.out.println("Plik " + path + " został poprawnie zapisany!");
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas zapisywania do pliku: " + e.getMessage());
        }
    }
}
