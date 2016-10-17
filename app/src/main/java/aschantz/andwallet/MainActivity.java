package aschantz.andwallet;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.Currency;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SUM = "KEY_SUM";
    public int sum;
    private int btnToggleCount;

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etAmount)
    EditText etAmount;
    @BindView(R.id.layoutAmount)
    LinearLayout layoutAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etAmount = (EditText) findViewById(R.id.etAmount);


        final ToggleButton toggle = (ToggleButton) findViewById(R.id.btnToggle);
        toggle.setEnabled(true);
        Log.d("enabled", "onCreate: ");
        btnToggleCount = 1;
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggle.getText() == getResources().getText(R.string.income).toString()) {
                    toggle.getTextOff();
                } else {
                    toggle.getTextOn();
                }
            }
        });

        Button btnSummary = (Button) findViewById(R.id.summary);
        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSummaryActivity(sum);
            }
        });


        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (isEmpty(etAmount) == true && isEmpty(etName)==true) {
                    emptyMessage(getResources().getText(R.string.emptyBoth).toString());
                } else if(isEmpty(etAmount) == true) {
                    emptyMessage(getResources().getText(R.string.emptyAmount).toString());
                } else if (isEmpty(etName) == true) {
                    emptyMessage(getResources().getText(R.string.emptyName).toString());
                } else {

                View nameRow = getLayoutInflater().inflate(
                        R.layout.row_amount, null, false);

                ImageView imageView = (ImageView) nameRow.findViewById(R.id.imageView);
                TextView tvName = (TextView) nameRow.findViewById(R.id.tvName);
                TextView tvAmount = (TextView) nameRow.findViewById(R.id.tvAmount);
                    TextView tvCurrency = (TextView) nameRow.findViewById(R.id.currency);

                if (toggle.getText() == getResources().getText(R.string.income).toString()) {
                    imageView.setImageResource(R.drawable.plus);
                    sum = sum + Integer.valueOf(etAmount.getText().toString());
                } else {
                    imageView.setImageResource(R.drawable.minus);
                    sum = sum - Integer.valueOf(etAmount.getText().toString());
                }

                tvName.setText(etName.getText().toString());
                tvAmount.setText(etAmount.getText().toString());
                    tvCurrency.setText(Currency.getInstance(Locale.getDefault()).getSymbol());

                layoutAmount.addView(nameRow, 0);
            }
                etName.setText("");
                etAmount.setText("");
        }
        });


        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.summary:
                Intent intent = new Intent(this, SummaryActivity.class);
                this.startActivity(intent);
                break;
            case R.id.logger:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }




    @OnClick(R.id.btnClear)
    public void clearClickHandler(Button btnClear) {
        layoutAmount.removeAllViews();
        etName.setText("");
        etAmount.setText("");
        sum = 0;
    }


    private void emptyMessage(String message) {
        Snackbar.make(etAmount, message, Snackbar.LENGTH_LONG).show();
    }


    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    private void startSummaryActivity(int sum) {
        //OPENS OTHER ACTIVITY (GameActivity)
        Intent intentStartGame = new Intent();
        intentStartGame.setClass(this, SummaryActivity.class);

        //pass the parameter
        //this is bad because it uses hard coded keys instead of constants
        //do command option c on with the key name highlighted
        intentStartGame.putExtra(KEY_SUM, sum);

        //finish method would make it so you can't go back with the back button
        finish();

        startActivity(intentStartGame);
        //END OF OPENING NEW ACTIVITY
    }



}
