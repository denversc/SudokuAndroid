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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sudokuView = new SudokuView(this);
        this.setContentView(this.sudokuView);
        this.sudokuView.requestFocus();
    }
}
