package be.kuleuven.candycrushopdracht6.model;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;


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


    Set<List<Position>> findAllMatches(){
//dit zijn startposties van lagner dan 3 maar moet lijst hiervan zijn

        Set<List<Position>> AllMatches = new HashSet<>();

        //List<Position> teettt = horizontalStartingPositions().toList();

        Stream<List<Position>> MatchesHorizontalStream = horizontalStartingPositions()  //.filter(position -> longestMatchToRight(position).size() >= 3)
                .map(this::longestMatchToRight)
                .filter(listPositions -> listPositions.size() >= 3);

        Stream<List<Position>> MatchesVerticalStream = verticalStartingPositions()  //.filter(position -> longestMatchToRight(position).size() >= 3)
                .map(this::longestMatchDown)
                .filter(listPositions -> listPositions.size() >= 3);



        //List<List<Position>> teessstt = MatchesHorizontalStream.toList();

        //ArrayList<Candy> allcelss = speelbordCandy.getAllCells();

        Stream<List<Position>> AllMatchesStream = Stream.concat(MatchesHorizontalStream, MatchesVerticalStream);

        AllMatches.addAll(AllMatchesStream.toList());

        //int testtt = AllMatches.size();

        return  AllMatches;

    }


    boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions){

        //if(positions.takeWhile(position -> speelbordCandy.getCellAt(position) == candy)
         //       .count() >= 2)
        //{
        //    return true;
        //}else{
        //    return false;
        //}

        return positions.limit(2)
                .allMatch(position -> speelbordCandy.getCellAt(position).equals(candy));
    }


    Stream<Position> horizontalStartingPositions(){ // links ander snoepje
        //check of er wel een staat
        return boardsize.positions().stream().filter(position -> !firstTwoHaveCandy(speelbordCandy.getCellAt(position), position.walkLeft()) || position.kolom()==0);

    }


    Stream<Position> verticalStartingPositions(){ // boven ander snoepje

        return boardsize.positions().stream().filter(position -> !firstTwoHaveCandy(speelbordCandy.getCellAt(position), position.walkUp()) || position.rij()==0);

    }

    List<Position> longestMatchToRight(Position pos){

        return pos.walkRight()
                .takeWhile(position2 -> speelbordCandy.getCellAt(pos).equals(speelbordCandy.getCellAt(position2)))
                .toList();

    }

    List<Position> longestMatchDown(Position pos){

        return pos.walkDown()
                .takeWhile(position2 -> speelbordCandy.getCellAt(pos).equals(speelbordCandy.getCellAt(position2)))
                .toList();

    }

}
