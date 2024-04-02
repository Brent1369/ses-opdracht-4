package be.kuleuven.candycrushopdracht6.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Function;

public class BoardTest {


    @Test
    public void FillBoardAndCheckType(){

        Function<Position, Candy> cellCreator = position -> CandycrushModel.createRandomCandy();
        Board<Candy> board = new Board<Candy>(new BoardSize(4,4));
        board.fill(cellCreator);
        Candy test = board.cells.get(0);
        assert (board.cells.get(0)  instanceof  Candy);
    }

    @Test
    public void copyBoardandCheckIfSame(){
        Function<Position, Candy> cellCreator = position -> CandycrushModel.createRandomCandy();
        Board<Candy> board = new Board<Candy>(new BoardSize(4,4));
        Board<Candy> board2 = new Board<Candy>(new BoardSize(4,4));
        board.fill(cellCreator);
        board.copyTo(board2);

        for (int i = 0; i < 16; i++){
            assert (board.cells.get(i)  == board2.cells.get(i));
        }
    }
}
