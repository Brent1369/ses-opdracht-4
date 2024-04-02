package be.kuleuven.candycrushopdracht6.model;


import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;


public class CandycrushModel {
    private String speler;

    public BoardSize boardsize = new BoardSize(5, 5);
    private int score;

    //private ArrayList<Candy> speelbordCandy;
    Function<Position, Candy> cellCreator = position -> CandycrushModel.createRandomCandy();
    Board<Candy> speelbordCandy = new Board<Candy>(boardsize);

    public CandycrushModel(String speler) {
        this.speler = speler;

        speelbordCandy.fill(cellCreator);

        /*speelbordCandy = new ArrayList<>();
        for (int i = 0; i < boardsize.Width()*boardsize.Height(); i++){
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbordCandy.add(createRandomCandy());
        }*/
    }
/*
    public static void main(String[] args) {
        CandycrushModel model = new CandycrushModel("arne");
        int i = 1;
        Iterator<Integer> iter = model.getSpeelbord().iterator();
        while(iter.hasNext()){
            int candy = iter.next();
            System.out.print(candy);
            if(i% model.getWidth()==0){
                System.out.print("\n");
                i = 1;
            }
            i++;
        }
        System.out.print("\n");

    }
    */

    public String getSpeler() {
        return speler;
    }

    public ArrayList<Candy> getSpeelbord() {
        return speelbordCandy.getAllCells();
    }

    public int getWidth() {
        return boardsize.Width();
    }

    public int getHeight() {
        return boardsize.Height();
    }

    public void candyWithIndexSelected(int index){




        if (index >= 0 && index < boardsize.Width() * boardsize.Height()){

            Iterable<Position> directNeighbours;

            directNeighbours = getSameNeighbourPositions(Position.fromIndex(index, boardsize));

            Random random = new Random();
            Candy candy = createRandomCandy();
            //speelbordCandy.cells.set(index, candy);
            speelbordCandy.replaceCellAt(Position.fromIndex(index, boardsize), candy);
            score++;
            int i = 0;
            for(Position neighbour : directNeighbours) {

                i++;
            }
            if ( i >=3){
                for(Position neighbour : directNeighbours) {

                    candy = createRandomCandy();
                    speelbordCandy.replaceCellAt(neighbour, candy);
                }
                score= score + i;
            }


        }else{
            System.out.println("model:candyWithIndexSelected:indexOutofBounds");
        }
    }

    public int getIndexFromRowColumn(int row, int column) {
        return column+row*boardsize.Width();
    }

    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return this.score;
    }

    public static Candy createRandomCandy(){
        Random random = new Random();
        int randomGetal = random.nextInt(8) ;
        Candy candy;
        switch(randomGetal){
            case 4:
                candy = new kea();
                break;
            case 5:
                candy = new jaker();
                break;
            case 6:
                candy = new loep();
                break;
            case 7:
                candy = new poad();
                break;
            default:
                candy = new NormalCandy(randomGetal);
        }

        return candy;
    }


    Iterable<Position> getSameNeighbourPositions(Position position){

        ArrayList<Position> result = new ArrayList<Position>();

        int[] directions = {
                -boardsize.Width(), boardsize.Width(), -1, 1,  // Up, Down, Left, Right
                -boardsize.Width() - 1, -boardsize.Width() + 1, boardsize.Width() - 1, boardsize.Width() + 1  // Diagonals
        };

        for(int i = 0; i < 8; i++){

            if(cellEqualsIterable(speelbordCandy.getAllCells(), boardsize.Width(), boardsize.Height(), position.toIndex(), position.toIndex() + directions[i])){

                result.add(Position.fromIndex(position.toIndex() + directions[i], boardsize));
                //result.add(position.toIndex() + directions[i]);
            }
        }


        return result;


    }

    private static Boolean cellEqualsIterable(ArrayList<Candy> grid,int width, int height, int indexToCheck, int indexToCheck2){
        if(indexToCheck2 < 0 || indexToCheck2 >= width * height){
            return false;
        }else if(grid.get(indexToCheck).equals(grid.get(indexToCheck2))){
            return true;
        }else{
            return false;
        }

    }



}
