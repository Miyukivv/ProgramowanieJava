import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        ArrayList<Vec2> pointsForHegaxon = new ArrayList<>();
        ArrayList<Vec2> pointsForTriangle = new ArrayList<>();
        ArrayList<Vec2> pointsForSquare = new ArrayList<>();
        Vec2 point1=new Vec2(200,300);
        Vec2 point2=new Vec2(300,400);

        pointsForHegaxon.add(new Vec2(100,20));
        pointsForHegaxon.add(new Vec2(180,60));
        pointsForHegaxon.add(new Vec2(180,140));
        pointsForHegaxon.add(new Vec2(100,180));
        pointsForHegaxon.add(new Vec2(20,140));
        pointsForHegaxon.add(new Vec2(20,60));

        pointsForTriangle.add(new Vec2(300,197));
        pointsForTriangle.add(new Vec2(12,209));
        pointsForTriangle.add(new Vec2(48,322));

        pointsForSquare.add(new Vec2(200,200));
        pointsForSquare.add(new Vec2(200,100));
        pointsForSquare.add(new Vec2(100,100));
        pointsForSquare.add(new Vec2(100,200));

        Shape hexagon = new Polygon(pointsForHegaxon, new Style("yellow", "red", 5));
        Shape triangle = new Polygon(pointsForTriangle, new Style("purple", "blue", 5));
        Shape square = new Polygon(pointsForSquare, new Style("black", "gray", 5));

        Segment seg=new Segment(new Style("purple","blue",5),point1,point2);
        System.out.println(seg.atoSvg());

        SvgScene scene=SvgScene.getInstance();
        //scene.addShape(hexagon);
        //scene.addShape(triangle);
      //  scene.addShape(square);

        TransformationDecorator.Builder builder=new TransformationDecorator.Builder();
        builder.scale(new Vec2(2,2)).rotate(45, new Vec2(2,2)).translate(new Vec2(50,-300));
        Shape bigShape=builder.build(square);

        System.out.println(bigShape.toSvg());

        GradientFillShapeDecorator.Builder grad=new GradientFillShapeDecorator.Builder();
        grad.stopAdd(0.3,"red").stopAdd(0.6,"orange").build();
        Shape shapeGradient=new GradientFillShapeDecorator(new Polygon(pointsForHegaxon, new Style( "black", 5)));

        Shape shapeShadowGradient=new DropShadowDecorator(shapeGradient);
        Shape shapeShadowTransformation=new DropShadowDecorator(bigShape);

        scene.addShape(shapeShadowTransformation);
        scene.addShape(shapeShadowGradient);

        scene.saveToFile("testFile.svg");
        scene.saveHtml("testHtml.html");
    }
}