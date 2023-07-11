package com.sendysofiani.forex;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingProgressbar;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private TextView awgTextView, aznTextView, bamTextView, bbdTextView, bdtTextView, bgnTextView, bhdTextView, bifTextView, bmdTextView, bndTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout1 = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout1);
        awgTextView = (TextView)findViewById(R.id.awgTextView);
        aznTextView = (TextView)findViewById(R.id.aznTextView);
        bamTextView = (TextView)findViewById(R.id.bamTextView);
        bbdTextView = (TextView)findViewById(R.id.bbdTextView);
        bdtTextView = (TextView)findViewById(R.id.bdtTextView);
        bgnTextView = (TextView)findViewById(R.id.bgnTextView);
        bhdTextView = (TextView)findViewById(R.id.bhdTextView);
        bifTextView = (TextView)findViewById(R.id.bifTextView);
        bmdTextView = (TextView)findViewById(R.id.bmdTextView);
        bndTextView = (TextView)findViewById(R.id.bndTextView);
        loadingProgressbar = (ProgressBar)findViewById(R.id.loadingProgressBar);

        initSwipeRefreshLayout();
        initForex();
        }


    private void initSwipeRefreshLayout() {
        swipeRefreshLayout1.setOnRefreshListener(() -> {
            initForex();

            swipeRefreshLayout1.setRefreshing(false);
        });
    }

    public String formatNumber(double number, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }

    private void initForex() {
        loadingProgressbar.setVisibility(TextView.VISIBLE);

        String url = "https://openexchangerates.org/api/latest.json?app_id=f664011c665846c485ec9f75cbc4bb02";

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient.get(url, new AsyncHttpClient() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //Log.d("*, new String(responseBody));
                Gson gson = new Gson();
                RootModel rootModel = gson.fromJson(new String(responseBody), RootModel.class);
                RatesModel ratesModel = rootModel.getRatesModel();

                double awg = ratesModel.getBND() / ratesModel.getAWG();
                double azn = ratesModel.getBND() / ratesModel.getAWG();
                double bam = ratesModel.getBND() / ratesModel.getAWG();
                double bbd = ratesModel.getBND() / ratesModel.getAWG();
                double bdt = ratesModel.getBND() / ratesModel.getAWG();
                double bgn = ratesModel.getBND() / ratesModel.getAWG();
                double bhd = ratesModel.getBND() / ratesModel.getAWG();
                double bif = ratesModel.getBND() / ratesModel.getBIF();
                double bmd = ratesModel.getBND() / ratesModel.getBMD();
                double bnd = ratesModel.getBND();

                awgTextView.setText(formatNumber(awg, "###,##0.##"));
                aznTextView.setText(formatNumber(awg, "###,##0.##"));
                bamTextView.setText(formatNumber(awg, "###,##0.##"));
                bbdTextView.setText(formatNumber(awg, "###,##0.##"));
                bdtTextView.setText(formatNumber(awg, "###,##0.##"));
                bgnTextView.setText(formatNumber(awg, "###,##0.##"));
                bhdTextView.setText(formatNumber(awg, "###,##0.##"));
                bifTextView.setText(formatNumber(awg, "###,##0.##"));
                bmdTextView.setText(formatNumber(awg, "###,##0.##"));
                bndTextView.setText(formatNumber(awg, "###,##0.##"));

                loadingProgressbar.setVisibility(TextView.INVISIBLE);


                @Override
                public void onFailure ( int statusCode, Header[] headers,
                byte[] responseBody, Throwable error ){
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    loadingProgressbar.setVisibility(TextView.INVISIBLE);

                }
            });
        }
    }



