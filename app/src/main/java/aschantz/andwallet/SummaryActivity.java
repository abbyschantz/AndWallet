package aschantz.andwallet;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by aschantz on 10/17/16.
 */
public class SummaryActivity extends AppCompatActivity {

    private TextView tvTotalText;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Currency currency = Currency.getInstance(Locale.getDefault());
        String getCurrency = currency.getDisplayName();

        tvTotalText = (TextView) findViewById(R.id.tvTotalText);
        TextView tvSum = (TextView) findViewById(R.id.tvSum);
        TextView tvCurrency = (TextView) findViewById(R.id.tvCurrency);

        int sum = getIntent().getIntExtra(MainActivity.KEY_SUM, 0);

        tvSum.setText(Integer.toString(sum));
        tvCurrency.setText(getCurrency);

        if(sum > 0){
            netMessage(getResources().getText(R.string.positive).toString());
        } else if (sum < 0) {
            netMessage(getResources().getText(R.string.negative).toString());
        } else{
            netMessage(getResources().getText(R.string.zero).toString());
        }

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
            case R.id.logger:
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            case R.id.summary:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void netMessage(String message) {
        Snackbar.make(tvTotalText, message, Snackbar.LENGTH_LONG).show();
    }
}
