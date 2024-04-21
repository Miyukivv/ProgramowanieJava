import java.util.Locale;

public class TransformationDecorator extends ShapeDecorator{

    private boolean translate;  //alt i w dół ->zaznaczamy całą kolumnę
    private Vec2 translateVector;
    private boolean rotate;
    private double rotateAngle;
    private Vec2 rotateCenter;
    private boolean scale;
    private Vec2 scaleVector;
    public TransformationDecorator(Shape decoratedShape) { //zadanie 4
        super(decoratedShape);
    } //

    public TransformationDecorator(Shape decoratedShape, boolean translate, Vec2 translateVector, boolean rotate, double rotateAngle, Vec2 rotateCenter, boolean scale, Vec2 scaleVector) {
        super(decoratedShape);
        this.translate = translate;
        this.translateVector = translateVector;
        this.rotate = rotate;
        this.rotateAngle = rotateAngle;
        this.rotateCenter = rotateCenter;
        this.scale = scale;
        this.scaleVector = scaleVector;
    }

    public static  class Builder{
        private boolean translate;  //alt i w dół ->zaznaczamy całą kolumnę
        private Vec2 translateVector;
        private boolean rotate;
        private double rotateAngle;
        private Vec2 rotateCenter;
        private boolean scale;
        private Vec2 scaleVector;

        public  Builder scale(Vec2 scaleVector) {
            this.scaleVector=scaleVector;
            this.scale=true;
            return this;
        }
        //ta flaga bedzie nam mowic ze raz wywolamy i wystarczy; potem sie bedziemy odwolywac w tosvg(if flaga=true)
        public Builder rotate(double rotateAngle, Vec2 rotateCenter){  //metoda nie static ->najpierw stworzyc obiekt danej klasy dopiero wtedy metode
            this.rotateAngle=rotateAngle; //centre -> oś obrotu -> względem punktu obróć o 30*
            this.rotateCenter=rotateCenter;
            this.rotate=true;

            return this;
        }
        public Builder translate(Vec2 translateVector){
            this.translateVector=translateVector;
            this.translate=true;

            return this;
        }

        //ostatnia metoda Build(aby zbudowac to wszystko) !! potrzebujemy konstruktora !!
        public TransformationDecorator build(Shape shape){ //budowniczy z dekoratorem
            return new TransformationDecorator(shape, translate, translateVector, rotate, rotateAngle, rotateCenter, scale, scaleVector);
        }
    }

    public String toSvg() { //musimy nadpisac metode tą, bo potrzebujemy przypisać dekorację do niego

        StringBuilder sb = new StringBuilder(decoratedShape.toSvg());
        //Każdy budowniczy zwraca samego siebie; nie trzeba tworzyc nowego obiektu
        sb.append(" transform=\"");

        if (rotate)
            sb.append(String.format(Locale.ENGLISH," rotate(%f %d %d)", rotateAngle, rotateCenter.getX(), rotateCenter.getY())); //String format -> nie wymaga zadnego istnienia obiektu tej klasy dlatego jest static

        if (translate)
            sb.append(String.format(Locale.ENGLISH," translate(%d %d)", translateVector.getX(), translateVector.getY()));

        if (scale)
            sb.append(String.format(Locale.ENGLISH," scale(%d %d)", scaleVector.getX(), scaleVector.getY()));

        sb.append("\"");

        return sb.toString();
    }
}
