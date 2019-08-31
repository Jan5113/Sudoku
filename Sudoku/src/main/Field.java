package main;

public class Field {
    private int value = 0;
    private boolean[] canBe = new boolean[9];

    public Field(int in) {
        value = in;
    }
    
    private Field(int in, boolean[] cb) {
        value = in;
        canBe = cb;
    }

    public int getValue() {
        return value;
    }

    public void resetCanBe() {
        boolean t = true;
        canBe = new boolean[]{t, t, t, t, t, t, t, t, t};
    }

    public boolean addCanBe(int[] cb) {
        for (int i = 0; i < 9; i++) {
            if (cb[i + 1] == 1) canBe[i] = false;
            if (cb[i + 1] > 1) return true;
        }
        return false;
    }

    public int countPossible() {
        if (value != 0) return 10;
        int count = 0;
        for (boolean i : canBe) {
            if (i) count++;
        }
        return count;
    }

	public String getValueStr() {
        if (value == 0) return " ";
		return value + "";
    }
    
    public String countPossibleStr() {
        int c = countPossible();
        if (c == 10) return " ";
        return c + "";
    }

	public void setValue(int index) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (canBe[i]) {
                if (count == index) {
                    value = i + 1;
                    break;               
                }
                count++;
            }
        }
    }
    
    public Field clone() {
        return new Field(value, canBe); 
    }
}