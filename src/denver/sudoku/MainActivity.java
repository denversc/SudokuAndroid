package denver.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        final View continueButton = this.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new ContinueOnClickListener());
        final View newButton = this.findViewById(R.id.new_button);
        newButton.setOnClickListener(new NewOnClickListener());
        final View aboutButton = this.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new AboutOnClickListener());
        final View exitButton = this.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new ExitOnClickListener());
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

    private class AboutOnClickListener implements OnClickListener {
        public void onClick(View v) {
            final Intent i = new Intent(MainActivity.this, AboutActivity.class);
            MainActivity.this.startActivity(i);
        }
    }

    private class ContinueOnClickListener implements OnClickListener {
        public void onClick(View v) {
        }
    }

    private class ExitOnClickListener implements OnClickListener {
        public void onClick(View v) {
        }
    }

    private class NewOnClickListener implements OnClickListener {
        public void onClick(View v) {
        }
    }
}