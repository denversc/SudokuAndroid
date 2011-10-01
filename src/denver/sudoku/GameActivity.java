package denver.sudoku;

import java.util.Arrays;

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

    public boolean[] getAvailableDigits(int index) {
        final boolean[] available = new boolean[9];
        Arrays.fill(available, true);

        // search the row for already used values
        int rowIndex = index;
        while (rowIndex > 0 && rowIndex % 9 != 0) {
            rowIndex--;
        }
        for (int i = rowIndex + 8; i >= rowIndex; i--) {
            final int value = this.puzzle[i];
            if (value != 0) {
                available[value - 1] = false;
            }
        }

        // search the column for already used values
        int colIndex = index;
        while (colIndex >= 0) {
            colIndex -= 9;
        }
        for (int i = colIndex + 9; i < this.puzzle.length; i += 9) {
            final int value = this.puzzle[i];
            if (value != 0) {
                available[value - 1] = false;
            }
        }

        return available;
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

        final int difficulty =
            this.getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
        switch (difficulty) {
            case DIFFICULTY_EASY:
                this.puzzle = Puzzles.getEasyPuzzle();
                break;
            case DIFFICULTY_MEDIUM:
                this.puzzle = Puzzles.getMediumPuzzle();
                break;
            case DIFFICULTY_HARD:
                this.puzzle = Puzzles.getHardPuzzle();
                break;
            default:
                throw new IllegalArgumentException(
                    "invalid difficulty retrieved from intent key \""
                        + KEY_DIFFICULTY + "\": " + difficulty);
        }

        this.puzzleEditable = new boolean[this.puzzle.length];
        for (int i = this.puzzle.length - 1; i >= 0; i--) {
            final int value = this.puzzle[i];
            final boolean editable = (value == 0);
            this.puzzleEditable[i] = editable;
        }

        this.sudokuView = new SudokuView(this);

        final boolean hintsEnabled = (difficulty != DIFFICULTY_HARD);
        this.sudokuView.setHintsEnabled(hintsEnabled);

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
