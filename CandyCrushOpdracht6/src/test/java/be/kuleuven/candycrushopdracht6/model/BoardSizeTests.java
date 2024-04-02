package be.kuleuven.candycrushopdracht6.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BoardSizeTests {

    @Test
    public void CreateBoardSizeAndGetPositionsAndCheckRowOfFirst(){
        BoardSize board = new BoardSize(4,4);
        Iterable<Position> boardlist = board.positions();

        ArrayList<Position> boardArrayList = new ArrayList();

        for(Position element : boardlist){
            boardArrayList.add(element);
        }

        int result = boardArrayList.getFirst().rij();
        assert (result == 0);
    }

}
