package denver.sudoku;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class SudokuView extends View {

    private final GameActivity game;

    private final int bgColor;
    private final Paint hiliteLinesPaint;
    private final Paint minorLinesPaint;
    private final Paint majorLinesPaint;
    private final Paint fgPaint;

    private final float[] majorLines;
    private final float[] minorLines;
    private final float[] hiliteLines;
    private final PointF[] boxes;
    private float boxWidth;
    private float boxHeight;

    public SudokuView(GameActivity game) {
        super(game);
        this.game = game;

        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        final Resources resources = this.getResources();
        this.bgColor = resources.getColor(R.color.puzzle_background);

        this.hiliteLinesPaint = new Paint();
        final int hiliteLinesColor = resources.getColor(R.color.puzzle_hilite);
        this.hiliteLinesPaint.setColor(hiliteLinesColor);

        this.minorLinesPaint = new Paint();
        final int minorLinesColor = resources.getColor(R.color.puzzle_light);
        this.minorLinesPaint.setColor(minorLinesColor);

        this.majorLinesPaint = new Paint();
        final int majorLinesColor = resources.getColor(R.color.puzzle_dark);
        this.majorLinesPaint.setColor(majorLinesColor);

        this.fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        final int fgColor = resources.getColor(R.color.puzzle_foreground);
        this.fgPaint.setColor(fgColor);
        this.fgPaint.setStyle(Paint.Style.FILL);
        this.fgPaint.setTextAlign(Paint.Align.CENTER);

        this.majorLines = new float[4 * 4];
        this.minorLines = new float[4 * 12];
        this.hiliteLines =
            new float[this.majorLines.length + this.minorLines.length];
        this.boxes = new PointF[9 * 9];

        for (int i = this.boxes.length - 1; i >= 0; i--) {
            this.boxes[i] = new PointF();
        }
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(this.bgColor);
        canvas.drawLines(this.hiliteLines, this.hiliteLinesPaint);
        canvas.drawLines(this.minorLines, this.minorLinesPaint);
        canvas.drawLines(this.majorLines, this.majorLinesPaint);

        // paint the numbers
        this.fgPaint.setTextSize(this.boxHeight * 0.75f);
        this.fgPaint.setTextScaleX(this.boxWidth / this.boxHeight);

        final FontMetrics fontMetrics = this.fgPaint.getFontMetrics();
        final float fontHeight = fontMetrics.ascent + fontMetrics.descent;
        final float fontHeightDiv2 = fontHeight / 2f;

        final char[] text = new char[1];
        for (int i = this.boxes.length - 1; i >= 0; i--) {
            final int digit = this.game.getPuzzleValue(i);
            final char c = digitToChar(digit);
            text[0] = c;
            final PointF point = this.boxes[i];
            final float x = point.x;
            final float y = point.y - fontHeightDiv2;
            canvas.drawText(text, 0, 1, x, y, this.fgPaint);
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

        // calculate the coordinates of the center of the boxes
        final float boxWidth = w / 9f;
        final float boxHeight = h / 9f;
        index = 0;
        float y = 0f;
        final RectF box = new RectF();
        for (int row = 0; row < 9; row++) {
            float x = 0f;
            for (int col = 0; col < 9; col++) {
                box.left = x;
                x += boxWidth;
                box.right = x;
                box.top = y;
                box.bottom = y + boxHeight;

                final PointF point = this.boxes[index++];
                point.x = box.centerX();
                point.y = box.centerY();
            }
            y += boxHeight;
        }
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.showKeypad();
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    public void showKeypad() {
        final KeypadDialog keypad = new KeypadDialog(this.game);
        keypad.show();
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
