package be.kuleuven.candycrushopdracht6.view;

import be.kuleuven.candycrushopdracht6.model.*;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.Iterator;

public class CandycrushView extends Region {
    private CandycrushModel model;
    private int widthCandy;
    private int heigthCandy;



    public CandycrushView(CandycrushModel model) {
        this.model = model;
        widthCandy = 30;
        heigthCandy = 30;
        update();
    }

    public void update(){
        getChildren().clear();
        int i = 0;
        int height = 0;
        Iterator<Candy> iter = model.getSpeelbord().iterator();
        while(iter.hasNext()) {
            Candy candy = iter.next();
            Rectangle rectangle = new Rectangle(i * widthCandy, height * heigthCandy, widthCandy,heigthCandy); //outside rectangle
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);

            Node node = makeCandyShape(new Position(model.boardsize, i, height),candy);

            //Text text = new Text("" + candy);

            node.setLayoutX(rectangle.getX() /*+ (rectangle.getWidth() ) / 2*/);
            node.setLayoutY(rectangle.getY() /*+ (rectangle.getHeight() ) / 2*/);

            //text.setX(rectangle.getX() + (rectangle.getWidth() - text.getBoundsInLocal().getWidth()) / 2);
            //text.setY(rectangle.getY() + (rectangle.getHeight() + text.getBoundsInLocal().getHeight()) / 2);


            getChildren().addAll(rectangle,node);

            if (i == model.getWidth() - 1) {
                i = 0;
                height++;
            } else {
                i++;
            }
        }
    }

    public int getIndexOfClicked(MouseEvent me){
        int index = -1;
        int row = (int) me.getY()/heigthCandy;
        int column = (int) me.getX()/widthCandy;
        System.out.println(me.getX()+" - "+me.getY()+" - "+row+" - "+column);
        if (row < model.getWidth() && column < model.getHeight()){
            index = model.getIndexFromRowColumn(row,column);
            System.out.println(index);
        }
        return index;
    }


    Node makeCandyShape(Position position, Candy candy){

        //Candy candy;
        Node shape;
        Circle circle = new Circle(widthCandy/2, Color.RED);
        //circle.setRadius(widthCandy/2);
        //Rectangle rectangle = new Rectangle(widthCandy/2,widthCandy/2,widthCandy/2,widthCandy/2, Color.RED);


        int a = 0;

        if(candy==null){
            return new Rectangle(widthCandy,widthCandy, Color.WHITE);
        }

        return switch(candy){
            case NormalCandy(int color) ->
                switch (color){
                    case 0 -> new Rectangle(widthCandy,widthCandy, Color.RED);
                    case 1 -> new Rectangle(widthCandy,widthCandy, Color.GREEN);
                    case 2 -> new Rectangle(widthCandy,widthCandy, Color.BLUE);
                    case 3 -> new Rectangle(widthCandy,widthCandy, Color.YELLOW);
                    default -> new Rectangle(widthCandy,widthCandy, Color.BLACK);
                };
            case kea() -> new Circle(widthCandy/2,widthCandy/2, widthCandy/2, Color.RED);

            case jaker() -> new Circle(widthCandy/2,widthCandy/2,widthCandy/2, Color.GREEN);

            case loep() -> new Circle(widthCandy/2,widthCandy/2,widthCandy/2, Color.BLUE);

            case poad() -> new Circle(widthCandy/2,widthCandy/2,widthCandy/2, Color.YELLOW);


        };

        //speelbord.set




    }
}
