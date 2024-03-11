package be.kuleuven.candycrushopdracht6.model;

import org.junit.jupiter.api.Test;

public class CandycrushModelTests {
//gegeven_wanneer_dan

    @Test
    public void CreateGameWithNameAndCheckThatName(){
        CandycrushModel model = new CandycrushModel("test");
        String result = model.getSpeler();
        assert (result.equals("test"));
    }


    @Test
    public void SetScoreAndCheckScore(){
        CandycrushModel model = new CandycrushModel("test");
        model.setScore(10);
        int result = model.getScore();
        assert (result == 9);
    }

    @Test
    public void getIndexFromRowColumnCheck(){//assume 4x4 grid
        CandycrushModel model = new CandycrushModel("test");

        int result = model.getIndexFromRowColumn(1,2);
        assert (result == 6);
    }

    @Test
    public void CheckWidth(){
        CandycrushModel model = new CandycrushModel("test");

        int result = model.getWidth();
        assert (result == 4);
    }


    @Test
    public void CheckHeight(){
        CandycrushModel model = new CandycrushModel("test");

        int result = model.getHeight();
        assert (result == 4);
    }


    @Test
    public void CheckBoardSize(){
        CandycrushModel model = new CandycrushModel("test");

        int result = model.getSpeelbord().size();
        assert (result == model.getHeight() * model.getWidth());
    }

    @Test
    public void testScoreAtStart(){
        CandycrushModel model = new CandycrushModel("test");


        int result = model.getScore();

        assert (result == 0);
    }

    @Test
    public void SelectCandyWithTooLowIndexIndexAndCheckIfScoreChange(){

        CandycrushModel model = new CandycrushModel("test");

        model.candyWithIndexSelected(-1);

        int result = model.getScore();

        assert (result == 0);
    }

    @Test
    public void SelectCandyWithTooHighIndexIndexAndCheckIfScoreChange(){

        CandycrushModel model = new CandycrushModel("test");

        model.candyWithIndexSelected(model.getHeight() * model.getWidth() + 11);

        int result = model.getScore();

        assert (result == 0);
    }

    @Test
    public void SelectCandyAndCheckIfScoreChange(){
        CandycrushModel model = new CandycrushModel("test");

        model.candyWithIndexSelected(0);

        int result = model.getScore();

        assert (result != 0);
    }


    //TODO: Delete previous test and test your own code

}
