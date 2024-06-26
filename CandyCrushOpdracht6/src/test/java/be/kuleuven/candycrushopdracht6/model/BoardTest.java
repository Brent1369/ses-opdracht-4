package be.kuleuven.candycrushopdracht6.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BoardTest {


    @Test
    public void FillBoardAndCheckType(){

        Function<Position, Candy> cellCreator = position -> CandycrushModel.createRandomCandy();
        BoardSize boardsize = new BoardSize(4,4);

        Board<Candy> board = new Board<Candy>(boardsize);
        board.fill(cellCreator);
        //Candy test = board.getCellAt(Position.fromIndex(0, boardsize));
        assert (board.getCellAt(Position.fromIndex(0, boardsize))  instanceof  Candy);
    }

    @Test
    public void copyBoardandCheckIfSame(){
        Function<Position, Candy> cellCreator = position -> CandycrushModel.createRandomCandy();
        BoardSize boardsize = new BoardSize(4,4);
        Board<Candy> board = new Board<Candy>(boardsize);
        Board<Candy> board2 = new Board<Candy>(boardsize);
        board.fill(cellCreator);
        board.copyTo(board2);

        for (int i = 0; i < 16; i++){
            assert (board.getCellAt(Position.fromIndex(i, boardsize))  == board2.getCellAt(Position.fromIndex(i, boardsize)));
        }
    }

/*
    @Test
    public void getPositionsOfElementTest(){
        Function<Position, Candy> cellCreator = position -> CandycrushModel.createRandomCandy();
        BoardSize boardsize = new BoardSize(5,5);
        Board<Candy> board = new Board<Candy>(boardsize);
        board.fill(cellCreator);
        List<Position> PositionsKea = board.getPositionsOfElement(new kea());

        PositionsKea.set(0, Position.fromIndex(1,boardsize));

        int test = 0;
        assert(1==1);
    }*/
}
