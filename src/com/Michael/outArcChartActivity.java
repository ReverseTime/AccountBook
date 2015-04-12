package com.Michael;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import com.Michael.AccountBook.R;

import java.util.*;

/**
 * Created with IntelliJ IDEA
 * Created by MaDianYong
 * Date: 2015/4/10
 */
public class outArcChartActivity extends Activity {
    private TextView text_arc_incomeBegin;
    private TextView text_arc_incomeEnd;
    private MySQLiteOpenHelper dbHelper;
    private String dateBeginString;
    private String dateEndString;
    private int eatSum=0;
    private int wearSum=0;
    private int liveSum=0;
    private int trafficSum=0;
    private outArcChartView arcChartView_arc_outarcChartView;
    //吃
    private List<Map<String, Object>> eatList;
    //穿
    private List<Map<String, Object>> wearList;
    //住
    private List<Map<String, Object>> liveList;
    //行
    private List<Map<String, Object>> trafficList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_outarcchartview);

        text_arc_incomeBegin = (TextView) findViewById(R.id.text_arc_incomeBegin);
        text_arc_incomeEnd = (TextView) findViewById(R.id.text_arc_incomeEnd);
        arcChartView_arc_outarcChartView = (outArcChartView) findViewById(R.id.arcChartView_arc_outarcChartView);

        dbHelper = new MySQLiteOpenHelper(this);
        eatList = new ArrayList<Map<String, Object>>();
        wearList = new ArrayList<Map<String, Object>>();
        liveList = new ArrayList<Map<String, Object>>();
        trafficList = new ArrayList<Map<String, Object>>();


    }

    public void income_clickButton(View view) {
        switch (view.getId()) {
            case R.id.textButton_arc_incomBegin:

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int monthOfYear = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateBeginString = year + "-" + (monthOfYear + 1)
                                        + "-" + dayOfMonth;
                                text_arc_incomeBegin.setText(dateBeginString);
                            }
                        }, year, monthOfYear, dayOfMonth);
                dDialog.show();
                break;
            case R.id.textButton_arc_incomEnd:

                Calendar calendar1 = Calendar.getInstance();
                int year1 = calendar1.get(Calendar.YEAR);
                int monthOfYear1 = calendar1.get(Calendar.MONTH);
                int dayOfMonth1 = calendar1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dDialog1 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateEndString = year + "-" + (monthOfYear + 1)
                                        + "-" + dayOfMonth;
                                text_arc_incomeEnd.setText(dateEndString);
                            }
                        }, year1, monthOfYear1, dayOfMonth1);
                dDialog1.show();
                break;
            case R.id.button_arc_search:

               eatList = dbHelper.selectList(
                        "select money from tb_myAccount where bigKind= ?",
                        new String []{"吃"});

                for (int i = 0; i < eatList.size(); i++) {
                    int s = Integer.parseInt(eatList.get(i).get("money").toString());
                    eatSum+=s;
                }
                wearList = dbHelper.selectList(
                        "select money from tb_myAccount  where bigKind= ?",
                        new String []{"穿"});
                for (int i = 0; i < wearList.size(); i++) {
                    int s = Integer.parseInt(wearList.get(i).get("money").toString());
                    wearSum+=s;
                }
                liveList = dbHelper.selectList(
                        "select money from tb_myAccount  where bigKind= ?",
                        new String []{"住"});
                for (int i = 0; i < liveList.size(); i++) {
                    int s = Integer.parseInt(liveList.get(i).get("money").toString());
                    liveSum+=s;
                }
               trafficList = dbHelper.selectList(
                        "select money from tb_myAccount  where bigKind= ?",
                        new String []{"行"});
                for (int i = 0; i < trafficList.size(); i++) {
                    int s = Integer.parseInt(trafficList.get(i).get("money").toString());
                    trafficSum+=s;
                }

                arcChartView_arc_outarcChartView.setData(new int[]
                                {eatSum,wearSum,liveSum,trafficSum}
                );
                break;
        }

    }
}