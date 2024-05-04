package be.kuleuven.candycrushopdracht6.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record Position(BoardSize board, int rij, int kolom) {

    public Position{

        if(rij >= board.Width() || kolom >= board.Height() || rij < 0 || kolom < 0){
            throw new IllegalArgumentException("out of bounds");
        }
    }

    public int toIndex(){
        int index = rij * board.Width() + kolom;

        if(index >= board.Width() * board.Height() || index < 0){
            throw new IllegalArgumentException("out of bounds");
        }else{
            return index;
        }
    }

    public static Position fromIndex(int index, BoardSize size){

        if (index >= size.Width() * size.Height() || index < 0)
            throw new IllegalArgumentException("out of bounds");

        int row = index / size.Width();
        int column = index % size.Width();

        Position newPosition = new Position(size, row, column);

        return newPosition;
    }

    Iterable<Position> neighborPositions(){

        List<Position> neighbors = new ArrayList<>();
        if (rij > 0)
            neighbors.add(new Position(board,rij - 1, kolom ));
        if (rij < board.Height() - 1)
            neighbors.add(new Position(board,rij + 1, kolom));
        if (kolom > 0)
            neighbors.add(new Position(board,rij, kolom - 1));
        if (kolom < board.Width() - 1)
            neighbors.add(new Position(board,rij, kolom + 1));
        return neighbors;

    }

    boolean isLastColumn(){
        return kolom == board.Width() - 1;
    }


    public Stream<Position> walkLeft(){

        //return board.positions().stream()
        //        .filter( position -> position.kolom>=0 && position.rij == rij) //alleen geldige
        //        .sort

        return Stream.iterate(this, position -> new Position(board, position.rij, position.kolom-1))
                .limit(kolom+1);
    }


    public Stream<Position> walkRight(){

        return Stream.iterate(this, position -> new Position(board, position.rij, position.kolom+1))
                .limit(board.Width()-kolom);

    }

    public Stream<Position> walkUp(){

        return Stream.iterate(this, position -> new Position(board, position.rij-1, position.kolom))
                .limit(rij+1);
    }

    public Stream<Position> walkDown(){

        return Stream.iterate(this, position -> new Position(board, position.rij+1, position.kolom))
                .limit(board.Height()-rij);

    }

}
