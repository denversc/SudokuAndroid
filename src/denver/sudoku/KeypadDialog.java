package denver.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class KeypadDialog extends Dialog {

    public KeypadDialog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.keypad_title);
        this.setContentView(R.layout.keypad);
    }

}
