package main;

import java.util.ArrayList;

public class Main {/*
public int[][] startVals = {
    {0, 0, 0,  0, 0, 0,  0, 0, 0},
    {0, 0, 0,  0, 0, 0,  0, 0, 0},
    {0, 0, 0,  0, 0, 0,  0, 0, 0},

    {0, 0, 0,  0, 0, 0,  0, 0, 0},
    {0, 0, 0,  0, 0, 0,  0, 0, 0},
    {0, 0, 0,  0, 0, 0,  0, 0, 0},

    {0, 0, 0,  0, 0, 0,  0, 0, 0},
    {0, 0, 0,  0, 0, 0,  0, 0, 0},
    {0, 0, 0,  0, 0, 0,  0, 0, 0},
};

public int[][] startVals = { //MEDIUM
    {5, 0, 9,  6, 8, 0,  0, 4, 0},
    {7, 0, 0,  0, 1, 3,  5, 0, 8},
    {0, 0, 6,  0, 0, 0,  1, 0, 0},

    {0, 0, 0,  0, 0, 1,  4, 0, 0},
    {0, 4, 0,  0, 2, 0,  0, 0, 1},
    {0, 0, 0,  0, 4, 5,  0, 0, 0},

    {2, 0, 0,  4, 0, 0,  6, 8, 5},
    {0, 0, 5,  1, 0, 2,  0, 0, 0},
    {0, 0, 0,  5, 3, 0,  0, 0, 9},
};*/
public int[][] startVals = { //EXPERT
    {4, 0, 0,  0, 0, 0,  0, 0, 2},
    {0, 0, 0,  0, 3, 6,  7, 0, 0},
    {7, 0, 0,  0, 0, 0,  0, 9, 0},

    {0, 0, 4,  2, 0, 0,  0, 0, 1},
    {0, 8, 0,  0, 0, 0,  0, 0, 0},
    {9, 6, 0,  0, 4, 0,  0, 0, 0},

    {0, 0, 0,  0, 0, 0,  0, 1, 3},
    {0, 0, 0,  0, 8, 0,  2, 0, 0},
    {0, 3, 1,  0, 6, 0,  9, 8, 0},
};
/*
public int[][] startVals = {
    {0, 0, 0,  0, 0, 0,  0, 0, 0},
    {0, 0, 0,  0, 0, 3,  0, 8, 5},
    {0, 0, 1,  0, 2, 0,  0, 0, 0},

    {0, 0, 0,  5, 0, 7,  0, 0, 0},
    {0, 0, 4,  0, 0, 0,  1, 0, 0},
    {0, 9, 0,  0, 0, 0,  0, 0, 0},

    {5, 0, 0,  0, 0, 0,  0, 7, 3},
    {0, 0, 2,  0, 1, 0,  0, 0, 0},
    {0, 0, 0,  0, 4, 0,  0, 0, 9},
};*/
public Grid grid;
public ArrayList<Grid> forks = new ArrayList<Grid>();

    public Main() {
        long time = System.nanoTime();
        int numGuess = 0;
        int numWrngGuess = 0;
        grid = new Grid(startVals);
        while (true) {
            boolean err = true;
            do {
                err = grid.refreshCBs();
            } while (grid.fillTrivial() && !err);

            if (grid.noSolution() || err) {
                if (forks.size() == 0) {
                    System.out.println("Sudoku cannot be solved!");
                    return;
                }
                grid = forks.remove(forks.size() - 1);
                numWrngGuess++;               
            } else if (grid.solved()){
                grid.print();
                System.out.println("Solved in " + (System.nanoTime() - time)/1000000 + "ms");
                System.out.println("Guessed " + numGuess + " times and " + numWrngGuess + " incorrect guesses");
                break;
            } else {
                Grid[] guesses = grid.makeGuess();
                grid = guesses[0];
                for (int i = 1; i < guesses.length; i++) {
                    forks.add(guesses[i]);
                }            
                numGuess++;
            }            
        } 
    }

    public static void main(String[] args) {
        new Main();
    }

}