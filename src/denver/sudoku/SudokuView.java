package denver.sudoku;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class SudokuView extends View {

    private final GameActivity game;

    private final int bgColor;
    private final Paint hiliteLinesPaint;
    private final Paint minorLinesPaint;
    private final Paint majorLinesPaint;
    private final Paint fgPaint;
    private final Paint selectedPaint;

    private final float[] majorLines;
    private final float[] minorLines;
    private final float[] hiliteLines;
    private final PointF[] boxCenters;
    private final RectF[] boxes;
    private float boxWidth;
    private float boxHeight;

    private int selectedBoxIndex;

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

        this.selectedPaint = new Paint();
        final int selectedColor = resources.getColor(R.color.puzzle_selected);
        this.selectedPaint.setColor(selectedColor);

        this.majorLines = new float[4 * 4];
        this.minorLines = new float[4 * 12];
        this.hiliteLines =
            new float[this.majorLines.length + this.minorLines.length];
        this.boxCenters = new PointF[9 * 9];
        this.boxes = new RectF[9 * 9];

        for (int i = this.boxes.length - 1; i >= 0; i--) {
            this.boxCenters[i] = new PointF();
            this.boxes[i] = new RectF();
        }

        this.selectedBoxIndex = -1;
    }

    public int getBoxIndexByCoords(float x, float y) {
        for (int i = this.boxes.length - 1; i >= 0; i--) {
            final RectF box = this.boxes[i];
            if (box.contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    public void invalidateBox(int box) {
        this.invalidateBox(box, new Rect());
    }

    public void invalidateBox(int box, Rect tempRect) {
        final RectF rectf = this.boxes[box];
        rectf.roundOut(tempRect);
        this.invalidate(tempRect);
    }

    public void moveSelectedBox(int delta) {
        final int newBoxIndex;
        if (this.selectedBoxIndex == -1) {
            newBoxIndex = 0;
        } else {
            final int temp = this.selectedBoxIndex + delta;
            if (temp < 0) {
                newBoxIndex = this.boxes.length + temp;
            } else {
                newBoxIndex = temp % this.boxes.length;
            }
        }

        this.setSelectedBox(newBoxIndex);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(this.bgColor);
        canvas.drawLines(this.hiliteLines, this.hiliteLinesPaint);

        this.fgPaint.setTextSize(this.boxHeight * 0.75f);
        this.fgPaint.setTextScaleX(this.boxWidth / this.boxHeight);

        final FontMetrics fontMetrics = this.fgPaint.getFontMetrics();
        final float fontHeight = fontMetrics.ascent + fontMetrics.descent;
        final float fontHeightDiv2 = fontHeight / 2f;

        final char[] text = new char[1];
        for (int i = this.boxes.length - 1; i >= 0; i--) {
            if (i == this.selectedBoxIndex) {
                final RectF rect = this.boxes[i];
                canvas.drawRect(rect, this.selectedPaint);
            }

            final int digit = this.game.getPuzzleValue(i);
            if (digit == 0) {
                continue;
            }
            final char c = digitToChar(digit);
            text[0] = c;
            final PointF point = this.boxCenters[i];
            final float x = point.x;
            final float y = point.y - fontHeightDiv2;
            canvas.drawText(text, 0, 1, x, y, this.fgPaint);
        }

        canvas.drawLines(this.minorLines, this.minorLinesPaint);
        canvas.drawLines(this.majorLines, this.majorLinesPaint);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                this.moveSelectedBox(-9);
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                this.moveSelectedBox(9);
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                this.moveSelectedBox(-1);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                this.moveSelectedBox(1);
                return true;
            case KeyEvent.KEYCODE_0:
                this.setSelectedBoxValue(0);
                return true;
            case KeyEvent.KEYCODE_1:
                this.setSelectedBoxValue(1);
                return true;
            case KeyEvent.KEYCODE_2:
                this.setSelectedBoxValue(2);
                return true;
            case KeyEvent.KEYCODE_3:
                this.setSelectedBoxValue(3);
                return true;
            case KeyEvent.KEYCODE_4:
                this.setSelectedBoxValue(4);
                return true;
            case KeyEvent.KEYCODE_5:
                this.setSelectedBoxValue(5);
                return true;
            case KeyEvent.KEYCODE_6:
                this.setSelectedBoxValue(6);
                return true;
            case KeyEvent.KEYCODE_7:
                this.setSelectedBoxValue(7);
                return true;
            case KeyEvent.KEYCODE_8:
                this.setSelectedBoxValue(8);
                return true;
            case KeyEvent.KEYCODE_9:
                this.setSelectedBoxValue(9);
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_SPACE:
                if (this.selectedBoxIndex >= 0) {
                    this.showKeypad();
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
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
        for (int row = 0; row < 9; row++) {
            float x = 0f;
            for (int col = 0; col < 9; col++) {
                final RectF rect = this.boxes[index];
                final PointF point = this.boxCenters[index];
                index++;
                rect.left = x;
                x += boxWidth;
                rect.right = x;
                rect.top = y;
                rect.bottom = y + boxHeight;

                point.x = rect.centerX();
                point.y = rect.centerY();
            }
            y += boxHeight;
        }
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            final float eventX = event.getX();
            final float eventY = event.getY();
            final int boxIndex = this.getBoxIndexByCoords(eventX, eventY);
            if (boxIndex >= 0) {
                this.setSelectedBox(boxIndex);
                this.showKeypad();
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    public void setSelectedBox(int boxIndex) {
        if (boxIndex < -1 || boxIndex >= this.boxes.length) {
            throw new IllegalArgumentException("boxIndex==" + boxIndex);
        }

        final int oldBoxIndex = this.selectedBoxIndex;
        this.selectedBoxIndex = boxIndex;

        final Rect tempRect = new Rect();
        if (oldBoxIndex >= 0) {
            this.invalidateBox(oldBoxIndex, tempRect);
        }
        if (boxIndex >= 0) {
            this.invalidateBox(boxIndex, tempRect);
        }

        this.selectedBoxIndex = boxIndex;
    }

    public void setSelectedBoxValue(int newValue) {
        final int boxIndex = this.selectedBoxIndex;
        if (boxIndex >= 0) {
            if (this.game.setPuzzleValue(boxIndex, newValue)) {
                this.invalidateBox(boxIndex);
            }
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
