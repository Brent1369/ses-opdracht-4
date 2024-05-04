package be.kuleuven.candycrushopdracht6.model;

import org.junit.jupiter.api.Test;

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
        CandycrushModel model = new CandycrushModel("d");

        Set<List<Position>> matches =  model.findAllMatches();

        int a = 1;
        assert (1==1);
    }


}
