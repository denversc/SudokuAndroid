package denver.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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