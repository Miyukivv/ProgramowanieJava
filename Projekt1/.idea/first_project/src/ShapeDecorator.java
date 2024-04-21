public class ShapeDecorator implements Shape {
    protected Shape decoratedShape; //moga miec dostep dziedziczace klasy (tylko klasa ktora ma to pole+jej dzieci)

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public String toSvg() {
        return decoratedShape.toSvg(); //musimy zawolac  metode z dekorowanego obiektu (dodajemy kolejne dodatki do pizzy)
    }

}
