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

    public int getPuzzleValue(int index) {
        final int value = this.puzzle[index];
        return value;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.puzzle = new int[9 * 9];
        final Random random = new Random();
        for (int i = this.puzzle.length - 1; i >= 0; i--) {
            final int randomValue = random.nextInt(9);
            this.puzzle[i] = randomValue + 1;
        }

        this.sudokuView = new SudokuView(this);
        this.setContentView(this.sudokuView);
        this.sudokuView.requestFocus();
    }
}
