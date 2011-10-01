package denver.sudoku;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    public static final String KEY_DIFFICULTY =
        "denver.sudoku.GameActivity.KEY_DIFFICULTY";

    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;

    private SudokuView sudokuView;
    private int[] puzzle;
    private boolean[] puzzleEditable;

    public int getPuzzleValue(int index) {
        final int value = this.puzzle[index];
        return value;
    }

    public boolean isPuzzleValueEditable(int index) {
        return this.puzzleEditable[index];
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.puzzle = new int[9 * 9];
        this.puzzleEditable = new boolean[this.puzzle.length];
        final Random random = new Random();
        for (int i = this.puzzle.length - 1; i >= 0; i--) {
            final int randomInclude = random.nextInt(5);
            final int value;
            if (randomInclude != 0) {
                value = 0;
            } else {
                final int randomValue = random.nextInt(9);
                value = randomValue + 1;
            }
            this.puzzle[i] = value;
            this.puzzleEditable[i] = (value == 0);
        }

        this.sudokuView = new SudokuView(this);
        this.setContentView(this.sudokuView);
        this.sudokuView.requestFocus();
    }

    public boolean setPuzzleValue(int index, int value) {
        if (value < 0 && value > 9) {
            throw new IllegalArgumentException("value==" + value);
        } else if (!this.puzzleEditable[index]) {
            return false;
        }
        this.puzzle[index] = value;
        return true;
    }
}
