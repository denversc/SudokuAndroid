package denver.sudoku;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class SudokuView extends View {

    private final GameActivity game;

    private final float[] majorLines;
    private final float[] minorLines;
    private final float[] hiliteLines;
    private final RectF[] boxes;

    public SudokuView(GameActivity game) {
        super(game);
        this.game = game;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        this.majorLines = new float[4 * 4];
        this.minorLines = new float[4 * 12];
        this.hiliteLines =
            new float[this.majorLines.length + this.minorLines.length];
        this.boxes = new RectF[9 * 9];

        for (int i = this.boxes.length - 1; i >= 0; i--) {
            this.boxes[i] = new RectF();
        }
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

        // paint the numbers
        final Paint numbersPaint = new Paint();
        final int numbersColor = Color.BLACK;
        numbersPaint.setColor(numbersColor);
        final char[] text = new char[1];
        for (int i = this.boxes.length - 1; i >= 0; i--) {
            final RectF box = this.boxes[i];
            final int digit = this.game.getPuzzleValue(i);
            final char c = digitToChar(digit);
            text[0] = c;
            final float x = box.left;
            final float y = box.top;
            canvas.drawText(text, 0, 1, x, y, numbersPaint);
        }
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

        // calculate the sizes of the boxes
        final float boxWidth = w / 9f;
        final float boxHeight = h / 9f;
        index = 0;
        float y = 0f;
        for (int row = 0; row < 9; row++) {
            float x = 0f;
            for (int col = 0; col < 9; col++) {
                final RectF rect = this.boxes[index++];
                rect.left = x;
                x += boxWidth;
                rect.right = x;
                rect.top = y;
                rect.bottom = y + boxHeight;
            }
            y += boxHeight;
        }
    }

    public static char digitToChar(int digit) {
        switch (digit) {
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            case 9:
                return '9';
            default:
                throw new IllegalArgumentException("digit==" + digit);
        }
    }
}
