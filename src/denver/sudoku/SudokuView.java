package denver.sudoku;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class SudokuView extends View {

    private final GameActivity game;

    private final float[] majorLines;
    private final float[] minorLines;
    private final float[] hiliteLines;

    public SudokuView(GameActivity game) {
        super(game);
        this.game = game;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        this.majorLines = new float[4 * 4];
        this.minorLines = new float[4 * 12];
        this.hiliteLines =
            new float[this.majorLines.length + this.minorLines.length];
    }

    protected void onDraw(Canvas canvas) {
        final Resources resources = this.getResources();

        // paint the background
        final int bgColor = resources.getColor(R.color.puzzle_background);
        canvas.drawColor(bgColor);

        // paint the hilite lines
        final Paint hiliteLinesPaint = new Paint();
        final int hiliteLinesColor = resources.getColor(R.color.puzzle_hilite);
        hiliteLinesPaint.setColor(hiliteLinesColor);
        canvas.drawLines(this.hiliteLines, hiliteLinesPaint);

        // paint the minor lines
        final Paint minorLinesPaint = new Paint();
        final int minorLinesColor = resources.getColor(R.color.puzzle_light);
        minorLinesPaint.setColor(minorLinesColor);
        canvas.drawLines(this.minorLines, minorLinesPaint);

        // paint the major lines
        final Paint majorLinesPaint = new Paint();
        final int majorLinesColor = resources.getColor(R.color.puzzle_dark);
        majorLinesPaint.setColor(majorLinesColor);
        canvas.drawLines(this.majorLines, majorLinesPaint);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int index;
        int hiliteIndex = 0;

        // calculate the coordinates of the major lines
        final float majorBoxWidth = w / 3f;
        final float majorBoxHeight = h / 3f;
        index = 0;
        for (int i = 1; i <= 2; i++) {
            this.majorLines[index++] = majorBoxWidth * i;
            this.majorLines[index++] = 0;
            this.majorLines[index++] = majorBoxWidth * i;
            this.majorLines[index++] = h;
        }
        for (int i = 1; i <= 2; i++) {
            this.majorLines[index++] = 0;
            this.majorLines[index++] = majorBoxHeight * i;
            this.majorLines[index++] = w;
            this.majorLines[index++] = majorBoxHeight * i;
        }
        for (int i = 0; i < index; i++) {
            this.hiliteLines[hiliteIndex++] = this.majorLines[i] + 1f;
        }

        // calculate the coordinates of the minor lines
        final float minorBoxWidth = w / 9f;
        final float minorBoxHeight = h / 9f;
        index = 0;
        for (int i = 1; i <= 8; i++) {
            if (i % 3 == 0) {
                continue; // don't draw overtop of the major lines
            }
            this.minorLines[index++] = minorBoxWidth * i;
            this.minorLines[index++] = 0;
            this.minorLines[index++] = minorBoxWidth * i;
            this.minorLines[index++] = h;
        }
        for (int i = 1; i <= 8; i++) {
            if (i % 3 == 0) {
                continue; // don't draw overtop of the major lines
            }
            this.minorLines[index++] = 0;
            this.minorLines[index++] = minorBoxHeight * i;
            this.minorLines[index++] = w;
            this.minorLines[index++] = minorBoxHeight * i;
        }
        for (int i = 0; i < index; i++) {
            this.hiliteLines[hiliteIndex++] = this.minorLines[i] + 1f;
        }
    }
}
