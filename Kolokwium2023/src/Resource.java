
/*
Zdefiniuj klasę Resource. W klasie Resource zdefiniuj publiczny typ wyliczeniowy Type {Coal, Wood,
Fish}. Klasa Resource powinna posiadać dwa publiczne, ostateczne pola: punkt (Point) określający
rozmieszczenie pozycję zasobu na mapie oraz typ (Type) zasobu. Napisz konstruktor ustawiający te
pola.
 */
public class Resource {
    public enum Type{Coal,Wood,Fish};
    public final Type type;
    public final Point location;

    public Resource(Type type, Point location) {
        this.type = type;
        this.location = location;
    }
}
