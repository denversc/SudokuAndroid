package denver.sudoku;

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

    public int[] getPuzzleFromIntent() {
        final int difficulty =
            this.getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
        switch (difficulty) {
            case DIFFICULTY_EASY:
                return Puzzles.getEasyPuzzle();
            case DIFFICULTY_MEDIUM:
                return Puzzles.getMediumPuzzle();
            case DIFFICULTY_HARD:
                return Puzzles.getHardPuzzle();
            default:
                throw new IllegalArgumentException(
                    "invalid difficulty retrieved from intent key \""
                        + KEY_DIFFICULTY + "\": " + difficulty);
        }
    }

    public int getPuzzleValue(int index) {
        final int value = this.puzzle[index];
        return value;
    }

    public boolean isPuzzleValueEditable(int index) {
        return this.puzzleEditable[index];
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.puzzle = this.getPuzzleFromIntent();
        this.puzzleEditable = new boolean[this.puzzle.length];
        for (int i = this.puzzle.length - 1; i >= 0; i--) {
            final int value = this.puzzle[i];
            final boolean editable = (value == 0);
            this.puzzleEditable[i] = editable;
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
