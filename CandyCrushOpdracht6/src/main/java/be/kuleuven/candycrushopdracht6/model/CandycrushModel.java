package be.kuleuven.candycrushopdracht6.model;


import javafx.stage.PopupWindow;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;


public class CandycrushModel {
    private String speler;

    public BoardSize boardsize;// = new BoardSize(4, 4);
    private int score;

    //private ArrayList<Candy> speelbordCandy;
    Function<Position, Candy> cellCreator = position -> CandycrushModel.createRandomCandy();
    Board<Candy> speelbordCandy;// = new Board<Candy>(boardsize);


    public void setCandyAt(Position pos, Candy candy){
        speelbordCandy.replaceCellAt(pos, candy);
    }



    public CandycrushModel(BoardSize boardsize) {
        //this.speler = speler;

        this.boardsize = boardsize;
        speelbordCandy = new Board<Candy>(boardsize);


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

            /*score++;
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
            }*/

            score += updateBoard();


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

    void clearMatch(List<Position> match){

        //Position firstpos = match.getFirst();
        if(match.size() != 0){
            speelbordCandy.replaceCellAt(match.getFirst(), null);
            //match.removeFirst();
            List<Position> match2 = match.stream().skip(1).toList(); // remove first one
            clearMatch(match2);
        }else{
            //speelbordCandy.replaceCellAt(match.getFirst(), null);
            //stop
        }
    }

    void fallDownTo(Position pos){

        if(speelbordCandy.getCellAt(pos) == null && pos.rij() != 0){ // if current cell is empty
            Position pos2 = new Position(boardsize, pos.rij() -1, pos.kolom());
            while(true){ // check if any above current cell can fall down

                Candy CandyAbove = speelbordCandy.getCellAt(pos2);

                if(CandyAbove != null){ // if there is no candy
                    speelbordCandy.replaceCellAt(pos, CandyAbove); //replace candy with candy above
                    speelbordCandy.replaceCellAt(pos2, null); //remove candy above
                    fallDownTo(new Position(boardsize, pos.rij() -1, pos.kolom())); // check cell above
                    break;
                }else{
                    if(pos2.rij() == 0){
                        break;
                    }
                    pos2 = new Position(boardsize, pos2.rij() -1, pos2.kolom()); // go up more
                }

            }

        }else{ // cell contains candy
            if(pos.rij() == 0){ // if row is top row -> dont call function again and stop

            }else{
                fallDownTo(new Position(boardsize, pos.rij() -1, pos.kolom())); // check cell above
            }
        }
    }

    int updateBoard(){

        int score = 0;

        Set<List<Position>> matches = findAllMatches();



        if(matches.size() == 0){
            return 0;
        }

        for(List<Position> match : matches){ // first remove all matches
            clearMatch(match);
            score = score + match.size();
        }

        for(List<Position> match : matches){ //drop all
            for(int i =0; i < match.size(); i++) {       // go through every position of a match
                fallDownTo(match.get(i));

            }
        }


        score = score + updateBoard();

        return score;

    }
/*
    interface PartialSolution {
        boolean isComplete();
        //boolean shouldAbort();
        //Board<Candy>  speelbord = clone(speelbordCandy);

        //int currentscore =0;

      //  Collection<Extension> extensions();
       // Solution toSolution();
       // boolean canImproveUpon(Solution solution);
    }



    public interface Extension {
        void apply(PartialSolution solution);
        void undo(PartialSolution solution);
    }

    interface Solution {
        boolean isBetterThan(Solution other);
    }
*/
    int bestScore = 0;
    int bestwissel = 0;

    //List<Integer> solutions = new ArrayList<>();

    List<Position[]> BestSwitchSequence = new ArrayList<>();

    private int findOptimalScore(boolean done, int currentScore, Board<Candy> speelbord, int wissels, List<Position[]> SwitchSequence){


        if(done){
            //solutions.add(currentScore);
            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestwissel = wissels;
                BestSwitchSequence = new ArrayList<>(SwitchSequence);
            }else if(currentScore == bestScore){
                if(wissels<bestwissel){
                    bestwissel = wissels;
                }
            }
            return bestScore;
        }

        /*if(wissels > bestwissel){
            //   bestwissel = wissels;
        }*/


        //boolean prevDone = done;
        //speelbord.copyTo(speelbordCandy);



        /*if (current.isComplete()) {
            var solution = current.toSolution();
            if (bestSoFar == null || solution.isBetterThan(bestSoFar)) {
                return solution;
            } else {
                return bestSoFar;
            }
        }

        if (current.shouldAbort() ||
                (bestSoFar != null && !current.canImproveUpon(bestSoFar))) {
            return bestSoFar;
        }*/

         //if(done)



        Position currentpos;
        Position pos2;

        for(int j = 0; j < boardsize.Height(); j++){

            for(int i = 0; i < boardsize.Width(); i++){
                currentpos = new Position(boardsize, j, i);
                for(int c = 0; c < 4; c++){
                    switch(c) {
                        case 0:
                            if (currentpos.kolom() == 0) {pos2 = null;break;}
                            pos2 = new Position(boardsize, currentpos.rij(), currentpos.kolom() - 1); //left
                            break;
                        case 1:
                            if (currentpos.kolom() == boardsize.Width() - 1) {pos2 = null;break;}
                            pos2 =new Position(boardsize, currentpos.rij(), currentpos.kolom() + 1);//right
                            break;
                        case 2:
                            if (currentpos.rij() == 0) {pos2 = null;break;}
                            pos2 = new Position(boardsize, currentpos.rij() - 1, currentpos.kolom());//top
                            break;
                        case 3:
                            if (currentpos.rij() == boardsize.Height() - 1) {pos2 = null;break;}
                            pos2 =new Position(boardsize, currentpos.rij() + 1, currentpos.kolom());//bottom
                            break;
                        default:
                            pos2 = null;
                    }
                    /*
                    if(i == boardsize.Width()-1 && j == boardsize.Height()-1){
                        int a = 1;
                    }

                    if(i == 1 && j ==2 && c == 3 && wissels ==0){
                        int a = 1;
                        //testthingy++;
                    }
                    if(i == 1 && j ==1 && c == 0 && wissels ==1
                            && speelbordCandy.getCellAt(new Position(boardsize, 0, 0)) == null
                            && speelbordCandy.getCellAt(new Position(boardsize, 1, 0)) == null
                            && speelbordCandy.getCellAt(new Position(boardsize, 2, 0)) == null
                    ){
                        int a = 1;
                        //testthingy++;
                    }
                    if(i == 1 && j ==2 && c == 3 && wissels ==2){
                        ArrayList<Candy> testtt = speelbordCandy.getAllCells();
                        int a = 1;
                    }*/

                    if(pos2 != null) { // if pos2 is not invalid
                        if (matchAfterSwitch(currentpos, pos2)) {
                            //make choices

                            //save old
                            Board<Candy> vorigspeelbord = new Board<>(speelbord.boardSize);
                            speelbordCandy.copyTo(vorigspeelbord); // = speelbordCandy
                            int prevscore = currentScore;


                            //execute
                            SwitchSequence.add(new Position[]{currentpos, pos2});
                            switchCandies(currentpos, pos2);
                            currentScore += updateBoard();
                            wissels++;
                            findOptimalScore(done, currentScore, speelbordCandy, wissels, SwitchSequence);


                            //backtrack
                            SwitchSequence.removeLast();
                            vorigspeelbord.copyTo(speelbordCandy);
                            currentScore = prevscore;
                            wissels--;



                            //done = prevDone;// not necessary

                        } else {
                            done = true;
                            findOptimalScore(done, currentScore, speelbord, wissels, SwitchSequence);
                            done = false;
                        }
                    }else{// if pos2 is invalid
                        done = true;
                        findOptimalScore(done, currentScore, speelbord, wissels, SwitchSequence);
                        done = false;
                    }

                }
            }
        }

        return bestScore;

    }

    int maximizeScore(BoardSize size){


        int currentScore=0;
        Board<Candy> speelbord = new Board<>(size);
        speelbordCandy.copyTo(speelbord);
        List<Position[]> SwitchSequence = new ArrayList<>();
        int score = findOptimalScore(false, currentScore, speelbord, 0, SwitchSequence);
        //int score = (updateBoard();
        if(score == 0){

        }
        int ssss = 0;

        return score;

    }

    Boolean switchCandies(Position pos1, Position pos2){

        Candy candy1 = speelbordCandy.getCellAt(pos1);
        Candy candy2 = speelbordCandy.getCellAt(pos2);

        if(candy1 == null || candy2 == null){
            return false;
        }


        speelbordCandy.replaceCellAt(pos1, candy2);
        speelbordCandy.replaceCellAt(pos2, candy1);

        return true;
    }

    boolean matchAfterSwitch(Position pos1, Position pos2){

        if(switchCandies(pos1,pos2) == false){
            return false;
        }
        int matches = findAllMatches().size();
        switchCandies(pos1,pos2);

        if(matches == 0){
            return false;
        }else{
            return true;
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
                .allMatch(position -> checkIfPositionContainsCandyType(position, candy));
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
                .takeWhile(position2 -> compareCandies(pos,position2))
                .toList();

    }

    List<Position> longestMatchDown(Position pos){

        return pos.walkDown()
                .takeWhile(position2 -> compareCandies(pos,position2))
                .toList();

    }

    boolean checkIfPositionContainsCandyType(Position pos, Candy candy){
        if(speelbordCandy.getCellAt(pos)==null|| candy == null){
            return false;
        }else if(speelbordCandy.getCellAt(pos).equals(candy)){
            return true;
        }else{
            return false;
        }

    }

    boolean compareCandies(Position pos1, Position pos2){
        if(speelbordCandy.getCellAt(pos1)==null||speelbordCandy.getCellAt(pos2)==null){
            return false;
        }else if(speelbordCandy.getCellAt(pos1).equals(speelbordCandy.getCellAt(pos2))){
            return true;
        }else{
            return false;
        }
    }

}
