package be.kuleuven.candycrushopdracht6.model;
import javafx.geometry.Pos;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import be.kuleuven.candycrushopdracht6.model.CandycrushModel.*;

public class Board <E>{

    BoardSize boardSize;
    Map<Position, E> cellsmap = new HashMap<>();
    Map<E, Set<Position>> Reversecellsmap =  new HashMap<>();

    public Board(BoardSize size){
        this.boardSize = size;

        for(int i =0; i< boardSize.Height()* boardSize.Width(); i++){
            //cellsmap.put(Position.fromIndex(i, boardSize), null);
        }

    }

    public List<Position> getPositionsOfElement(E cellType){

        Set<Position> SetofPositions = Reversecellsmap.get(cellType);
        if(SetofPositions == null){
            return Collections.<Position>emptyList(); //return empty immutable list
        }

        List<Position> positionsArrayList = new ArrayList<Position>();
        positionsArrayList.addAll(SetofPositions); //convert set to list
        List<Position> unmodifiable = Collections.unmodifiableList(positionsArrayList);//make list immutable

        return unmodifiable;
    }

    //Position position;

    public E getCellAt(Position position){
        return cellsmap.get(position);
        //return cells.get(position.toIndex());
    }

    public ArrayList<E> getAllCells(){
        ArrayList<E> cells = new ArrayList<>(Collections.nCopies(boardSize.Height()* boardSize.Width(),null));

        for(int i =0; i < boardSize.Height() * boardSize.Width(); i++){
            cells.set(i, cellsmap.get(Position.fromIndex(i, boardSize)));
        }
        return cells;
    }

    public void replaceCellAt(Position position, E newCell){
        E oldCell = cellsmap.get(position);
        cellsmap.put(position, newCell);

        //remove old position location from cell
        Set<Position> Oldpositions  = Reversecellsmap.get(oldCell);
        if(Oldpositions != null){
            Oldpositions.remove(position);
        }
        //add new position location to cell
        Set<Position> positions = Reversecellsmap.get(newCell);
        if(positions == null){
            positions = new HashSet<>();
            Reversecellsmap.put(newCell, positions);
        }else{
            positions.add(position);
        }
        //Reversecellsmap.computeIfAbsent(newCell, k -> new HashSet<>()).add(position);


        //cells.set(position.toIndex(),newCell);
    }

    //Function<Position, E> cellCreator = position -> new jaker();

    //Function<E> cell =
    public void fill(Function<Position, E> cellCreator){

        for(int i =0; i < boardSize.Height() * boardSize.Width(); i++){
                //cells.set(i, cellCreator.apply(Position.fromIndex(i, boardSize)));
            //cellsmap.put(Position.fromIndex(i, boardSize), cellCreator.apply(Position.fromIndex(i, boardSize)));
            replaceCellAt(Position.fromIndex(i, boardSize), cellCreator.apply(Position.fromIndex(i, boardSize)));

        }
    }



    public void copyTo(Board <E> otherBoard){
        if(boardSize.Width() != otherBoard.boardSize.Width() && boardSize.Height() != otherBoard.boardSize.Height()){
            throw new IllegalArgumentException("different sizes");
        }
        for(int i =0; i < boardSize.Height() * boardSize.Width(); i++){
            Position position = Position.fromIndex(i, boardSize);
            otherBoard.replaceCellAt(position, cellsmap.get(position));
               //otherBoard.cells.set(i, cells.get(i));
        }

    }

}
