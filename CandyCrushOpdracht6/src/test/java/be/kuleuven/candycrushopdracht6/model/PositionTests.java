package be.kuleuven.candycrushopdracht6.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTests {


    @Test
    public void CreateBoardSizeAndCheckPositionToIndex(){
        BoardSize board = new BoardSize(4,4);
        Position positionVar = new Position(board, 1, 0);
        int result = positionVar.toIndex();
        assert (result == 4);
    }

    @Test
    public void CreateBoardSizeAndCheckPositionFromIndex(){
        BoardSize board = new BoardSize(4,4);

        Position positionVar = Position.fromIndex(1, board);

        assert (positionVar.rij() == 0);
        assert (positionVar.kolom() == 1);
    }

    @Test
    public void checkNeighsborPositions(){
        BoardSize board = new BoardSize(4,4);
        Position positionVar = new Position(board, 2, 2);
        Iterable<Position>  PositionList= positionVar.neighborPositions();

        ArrayList<Position> PositionArrayList = new ArrayList();

        for(Position element : PositionList){
            PositionArrayList.add(element);
        }

        assert(PositionArrayList.get(0).equals(new Position(board,1,2)));
        assert(PositionArrayList.get(1).equals(new Position(board,3,2)));
        assert(PositionArrayList.get(2).equals(new Position(board,2,1)));
        assert(PositionArrayList.get(3).equals(new Position(board,2,3)));
    }

    @Test
    public void checkIsLastColumn(){
        BoardSize board = new BoardSize(4,4);
        Position positionVar = new Position(board, 1, 3);

        assert (positionVar.isLastColumn());
    }




    //manual debug
    @Test
    public void walkLefttest()
    {
        BoardSize board = new BoardSize(4,4);
        Position positionVar = new Position(board, 1, 3);
        Stream<Position> test = positionVar.walkDown();
        test.forEach(position -> System.out.println(position));
        int a = 1;
        assert (1==1);
    }

    //manual debug
    @Test
    public void findAllMatchesTest()
    {
        //BoardSize board = new BoardSize(6,6);
        CandycrushModel model = new CandycrushModel(new BoardSize(4,4));

        Set<List<Position>> matches =  model.findAllMatches();



        int a = 1;
        assert (1==1);
    }


    @Test
    public void BackTrackTest()
    {
        //BoardSize board = new BoardSize(6,6);
        CandycrushModel model = model2;

        ArrayList<Candy> test = model.speelbordCandy.getAllCells();

        int score = model.maximizeScore(model.boardsize);

        //List<Integer> solutions =  model.solutions;
        //int test = solutions.
        int wissels  = model.bestwissel;

        List<Position[]> BestSwitchSequence = model.BestSwitchSequence;

        System.out.println(score);
        System.out.println(wissels);

        int a = 1;
        assert (score == 24);
        assert (wissels == 7);
    }



    private static Candy characterToCandy(char c) {
        return switch(c) {
            case 'o' -> new NormalCandy(0);
            //case 'o' -> null;
            case '*' -> new NormalCandy(1);
            case '#' -> new NormalCandy(2);
            case '@' -> new NormalCandy(3);
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
    public static CandycrushModel createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        BoardSize size = new BoardSize(lines.size(), lines.getFirst().length());
        var model = new CandycrushModel(size); // deze moet je zelf voorzien
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                model.setCandyAt(new Position(size, row, col), characterToCandy(line.charAt(col)));
            }
        }
        return model;
    }


    CandycrushModel model1 = createBoardFromString("""
   @@o#
   o*#o
   @@**
   *#@@""");

    CandycrushModel model2 = createBoardFromString("""
   #oo##
   #@o@@
   *##o@
   @@*@o
   **#*o""");

    CandycrushModel model3 = createBoardFromString("""
   #@#oo@
   @**@**
   o##@#o
   @#oo#@
   @*@**@
   *#@##*""");

}
