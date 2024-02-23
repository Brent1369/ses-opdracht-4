package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.List;


public class CheckNeighboursInGrid {

    /**
     * This method takes a 1D Iterable and an element in the array and gives back an iterable containing the indexes of all neighbours with the same value as the specified element
     *@return - Returns a 1D Iterable of ints, the Integers represent the indexes of all neighbours with the same value as the specified element on index 'indexToCheck'.
     *@param grid - This is a 1D Iterable containing all elements of the grid. The elements are integers.
     *@param width - Specifies the width of the grid.
     *@param height - Specifies the height of the grid (extra for checking if 1D grid is complete given the specified width)
     *@param indexToCheck - Specifies the index of the element which neighbours that need to be checked
     */


    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid,int width, int height, int indexToCheck){

        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> gridArrayList = new ArrayList<Integer>();

        for (Integer element : grid) {
            gridArrayList.add(element);
        }


        int[] directions = {
                -width, width, -1, 1,  // Up, Down, Left, Right
                -width - 1, -width + 1, width - 1, width + 1  // Diagonals
        };

        for(int i = 0; i < 8; i++){

            if(cellEqualsIterable(gridArrayList, width, height, indexToCheck, indexToCheck + directions[i])){
                result.add(indexToCheck + directions[i]);
            }
        }


        return result;
    }

    private static Boolean cellEqualsIterable(ArrayList<Integer> grid,int width, int height, int indexToCheck, int indexToCheck2){

        if(indexToCheck2 < 0 || indexToCheck2 >= width * height){
            return false;
        }else if(grid.get(indexToCheck) == grid.get(indexToCheck2)){
            return true;
        }else{
            return false;
        }

    }

}
