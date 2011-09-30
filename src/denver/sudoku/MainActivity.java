package denver.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

    public static final String TAG = "Sudoku";

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_button:
                final Intent i =
                    new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(i);
                break;
            case R.id.new_button:
                new OpenNewGameDialog(this).show();
                break;
            case R.id.exit_button:
                this.finish();
                break;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        final View continueButton = this.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        final View newButton = this.findViewById(R.id.new_button);
        newButton.setOnClickListener(this);
        final View aboutButton = this.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        final View exitButton = this.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        final boolean result = super.onCreateOptionsMenu(menu);
        final MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return result;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                this.startActivity(new Intent(this, PrefsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void startGame(int difficulty) {
        Log.d(TAG, "Starting game with difficulty " + difficulty);
    }

    private static class OpenNewGameDialog extends AlertDialog.Builder
            implements DialogInterface.OnClickListener {

        public OpenNewGameDialog(Context context) {
            super(context);
            this.setTitle(R.string.new_game_title);
            this.setItems(R.array.difficulty, this);
        }

        public void onClick(DialogInterface dialog, int which) {
            startGame(which);
        }

    }

}