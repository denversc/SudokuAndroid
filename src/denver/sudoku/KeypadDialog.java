package denver.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class KeypadDialog extends Dialog {

    private final boolean[] disabledKeys;

    private OnKeySelectedListener onKeySelectedListener;

    public KeypadDialog(Context context) {
        super(context);
        this.disabledKeys = new boolean[10];
    }

    public void doSelectDigit(int digit) {
        this.notifyKeySelected(digit);
        this.dismiss();
    }

    public OnKeySelectedListener getOnKeySelectedListener() {
        return this.onKeySelectedListener;
    }

    private void initButton(int id, View.OnClickListener onClickListener) {
        final View view = this.findViewById(id);
        view.setOnClickListener(onClickListener);
        final int digit = getDigitByButtonId(id);
        if (!this.isKeyEnabled(digit)) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isKeyEnabled(int digit) {
        final boolean disabled = this.disabledKeys[digit];
        return !disabled;
    }

    public void notifyKeySelected(int digit) {
        final OnKeySelectedListener listener = this.getOnKeySelectedListener();
        if (listener != null) {
            listener.keySelected(digit);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.keypad_title);
        this.setContentView(R.layout.keypad);

        final View.OnClickListener buttonClickListener =
            new KeyButtonOnClickListener();
        this.initButton(R.id.keypad1, buttonClickListener);
        this.initButton(R.id.keypad2, buttonClickListener);
        this.initButton(R.id.keypad3, buttonClickListener);
        this.initButton(R.id.keypad4, buttonClickListener);
        this.initButton(R.id.keypad5, buttonClickListener);
        this.initButton(R.id.keypad6, buttonClickListener);
        this.initButton(R.id.keypad7, buttonClickListener);
        this.initButton(R.id.keypad8, buttonClickListener);
        this.initButton(R.id.keypad9, buttonClickListener);
        this.initButton(R.id.keypadClear, buttonClickListener);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final int digit = getKeyCodeByDigit(keyCode);
        if (digit >= 0) {
            this.doSelectDigit(digit);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void setKeyEnabled(int digit, boolean enabled) {
        this.disabledKeys[digit] = !enabled;
    }

    public void setOnKeySelectedListener(OnKeySelectedListener listener) {
        this.onKeySelectedListener = listener;
    }

    private static int getDigitByButtonId(int id) {
        switch (id) {
            case R.id.keypad1:
                return 1;
            case R.id.keypad2:
                return 2;
            case R.id.keypad3:
                return 3;
            case R.id.keypad4:
                return 4;
            case R.id.keypad5:
                return 5;
            case R.id.keypad6:
                return 6;
            case R.id.keypad7:
                return 7;
            case R.id.keypad8:
                return 8;
            case R.id.keypad9:
                return 9;
            case R.id.keypadClear:
                return 0;
            default:
                throw new IllegalArgumentException("id==" + id);
        }
    }

    public static int getKeyCodeByDigit(int keycode) {
        switch (keycode) {
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_SPACE:
                return 0;
            case KeyEvent.KEYCODE_1:
                return 1;
            case KeyEvent.KEYCODE_2:
                return 2;
            case KeyEvent.KEYCODE_3:
                return 3;
            case KeyEvent.KEYCODE_4:
                return 4;
            case KeyEvent.KEYCODE_5:
                return 5;
            case KeyEvent.KEYCODE_6:
                return 6;
            case KeyEvent.KEYCODE_7:
                return 7;
            case KeyEvent.KEYCODE_8:
                return 8;
            case KeyEvent.KEYCODE_9:
                return 9;
            default:
                return -1;
        }
    }

    private class KeyButtonOnClickListener implements View.OnClickListener {

        public void onClick(View v) {
            final int id = v.getId();
            final int digit = getDigitByButtonId(id);
            if (digit >= 0) {
                KeypadDialog.this.doSelectDigit(digit);
            }
        }

    }

    public static interface OnKeySelectedListener {
        public void keySelected(int digit);
    }
}
