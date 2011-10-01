package denver.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class KeypadDialog extends Dialog {

    private OnKeySelectedListener onKeySelectedListener;

    public KeypadDialog(Context context) {
        super(context);
    }

    public void doSelectDigit(int digit) {
        this.notifyKeySelected(digit);
        this.dismiss();
    }

    public void doSelectDigitByButtonId(int id) {
        final int digit;
        switch (id) {
            case R.id.keypad1:
                digit = 1;
                break;
            case R.id.keypad2:
                digit = 2;
                break;
            case R.id.keypad3:
                digit = 3;
                break;
            case R.id.keypad4:
                digit = 4;
                break;
            case R.id.keypad5:
                digit = 5;
                break;
            case R.id.keypad6:
                digit = 6;
                break;
            case R.id.keypad7:
                digit = 7;
                break;
            case R.id.keypad8:
                digit = 8;
                break;
            case R.id.keypad9:
                digit = 9;
                break;
            case R.id.keypadClear:
                digit = 0;
                break;
            default:
                return;
        }
        this.doSelectDigit(digit);
    }

    public OnKeySelectedListener getOnKeySelectedListener() {
        return this.onKeySelectedListener;
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
        this.findViewById(R.id.keypad1).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypad2).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypad3).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypad4).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypad5).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypad6).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypad7).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypad8).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypad9).setOnClickListener(buttonClickListener);
        this.findViewById(R.id.keypadClear).setOnClickListener(
            buttonClickListener);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final int digit = keyCodeToDigit(keyCode);
        if (digit >= 0) {
            this.doSelectDigit(digit);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void setOnKeySelectedListener(OnKeySelectedListener listener) {
        this.onKeySelectedListener = listener;
    }

    public static int keyCodeToDigit(int keycode) {
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
            KeypadDialog.this.doSelectDigitByButtonId(id);
        }

    }

    public static interface OnKeySelectedListener {
        public void keySelected(int digit);
    }
}
