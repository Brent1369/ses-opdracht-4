package be.kuleuven.candycrushopdracht6.model;

import javafx.geometry.Pos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import be.kuleuven.candycrushopdracht6.model.CandycrushModel.*;

public class Board <E>{

    BoardSize boardSize;
    private ArrayList<E> cells;


    public Board(BoardSize size){
        this.boardSize = size;
        //cells = (E[][]) new Object[size.Height()][size.Width()];

        cells = new ArrayList<>(Collections.nCopies(size.Height()* size.Width(),null));
        //cells = Array.newInstance(E, size.Height(), size.Width());
    }

    //Position position;

    public E getCellAt(Position position){
        return cells.get(position.toIndex());
    }

    public ArrayList<E> getAllCells(){
        return cells;
    }

    public void replaceCellAt(Position position, E newCell){
        cells.set(position.toIndex(),newCell);
    }

    //Function<Position, E> cellCreator = position -> new jaker();

    //Function<E> cell =
    public void fill(Function<Position, E> cellCreator){

        for(int i =0; i < boardSize.Height() * boardSize.Width(); i++){
                cells.set(i, cellCreator.apply(Position.fromIndex(i, boardSize)));
        }
    }



    public void copyTo(Board <E> otherBoard){
        if(boardSize.Width() != otherBoard.boardSize.Width() && boardSize.Height() != otherBoard.boardSize.Height()){
            throw new IllegalArgumentException("different sizes");
        }
        for(int i =0; i < boardSize.Height() * boardSize.Width(); i++){
               otherBoard.cells.set(i, cells.get(i));
        }

    }

}
