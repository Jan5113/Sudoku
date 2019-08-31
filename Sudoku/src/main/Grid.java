package main;

public class Grid {
    Field[][] allFields;
    public Grid(int[][] startVals) {
        allFields = new Field[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                allFields[r][c] = new Field(startVals[r][c]);
            }
        }

    }
    private Grid(Field[][] f) {
        allFields = new Field[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                allFields[i][j] = f[i][j].clone();
            }
        }
    }

    public boolean refreshCBs() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                allFields[i][j].resetCanBe();
            }
        }
        for (int i = 0; i < 9; i++) {
            int[] cbRows = rowIsSet(i);
            int[] cbColumns = columnIsSet(i);
            int[] cbSectors = sectorIsSet(i);
            for (int j = 0; j < 9; j++) {
               if (allFields[i][j].addCanBe(cbRows) ||
                allFields[j][i].addCanBe(cbColumns) ||
                allFields[3* (i/3) + j/3][3 * (i%3) + j%3].addCanBe(cbSectors)) return true; 
            }
        }
        return false;
    }

    public boolean fillTrivial() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int c = allFields[i][j].countPossible();
                if (c == 1) {
                    allFields[i][j].setValue(0);
                    return true;
                } else if (c == 0) {
                    return false;
                }
            }
        }
        return false;
    }

    private int[] rowIsSet(int n) {
        int[] row = new int[10];
        for (int i = 0; i < 9; i++) {
            row[allFields[n][i].getValue()]++;
        }
        return row;
    }

    private int[] columnIsSet(int n) {
        int[] column = new int[10];
        for (int i = 0; i < 9; i++) {
            column[allFields[i][n].getValue()]++;
        }
        return column;

    }

    private int[] sectorIsSet(int s) {
        int[] sector = new int[10];
        for (int i = 0; i < 9; i++) {
            sector[allFields[3* (s/3) + i/3][3 * (s%3) + i%3].getValue()]++;
        }
        return sector;
    }

	public void print() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + allFields[i][j].getValueStr());
                if (j == 2 || j == 5) System.out.print(" |");
            }
            System.out.println("");
            if (i == 2 || i == 5) System.out.println("-------+-------+-------");
        }
        System.out.println("");
	}

	public void printNPos() {        
        for (int i = 0; i < 9; i++) {
            System.out.print("                             ");
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + allFields[i][j].countPossibleStr());
                if (j == 2 || j == 5) System.out.print(" |");
            }
            System.out.println("");
            if (i == 2 || i == 5) System.out.println("                             -------+-------+-------");
        }
        System.out.println("");
	}

	public boolean solved() {
		for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (allFields[i][j].countPossible() != 10) return false;
            }
        }
        return true;
    }
    
    public Grid clone() {
        return new Grid(allFields);
    }
	public boolean noSolution() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (allFields[i][j].countPossible() == 0) return true;
            }
        }
		return false;
	}
	public Grid[] makeGuess() {
        int min = 10;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int c = allFields[i][j].countPossible();
                if (c < min) min = c;
            }
        }
        Grid[] guesses = new Grid[min];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (allFields[i][j].countPossible() == min) {
                    for (int k = 0; k < min; k++) {
                        guesses[k] = clone();
                        guesses[k].allFields[i][j].setValue(k);
                    }
                    return guesses;
                }
            }
        }
		return null;
	}

}