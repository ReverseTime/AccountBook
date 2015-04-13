package com.Michael;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
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
public class ArcChartActivity extends Activity {
    private TextView text_arc_incomeBegin;
    private TextView text_arc_incomeEnd;
    private MySQLiteOpenHelper dbHelper;
    private String dateBeginString;
    private String dateEndString;
    private int salarySum = 0;
    private int perkSum = 0;
    private ArcChartView arcChartView_arc_arcChartView;
    //工资集合
    private List<Map<String, Object>> salaryList;
    //外快集合
    private List<Map<String, Object>> perkList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_arcchartview);


        text_arc_incomeBegin = (TextView) findViewById(R.id.text_arc_incomeBegin);
        text_arc_incomeEnd = (TextView) findViewById(R.id.text_arc_incomeEnd);
        arcChartView_arc_arcChartView = (ArcChartView) findViewById(R.id.arcChartView_arc_arcChartView);

        dbHelper = new MySQLiteOpenHelper(this);
        salaryList = new ArrayList<Map<String, Object>>();
        perkList = new ArrayList<Map<String, Object>>();

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

                salaryList = dbHelper.selectList(
                        "select money from tb_myAccount where bigKind= ?",
                        new String[]{"工资"});

                for (int i = 0; i < salaryList.size(); i++) {
                    int s = Integer.parseInt(salaryList.get(i).get("money").toString());
                    salarySum += s;
                }
                perkList = dbHelper.selectList(
                        "select money from tb_myAccount  where bigKind= ?",
                        new String[]{"外快"});
                for (int i = 0; i < perkList.size(); i++) {
                    int s = Integer.parseInt(perkList.get(i).get("money").toString());
                    perkSum += s;
                }

                arcChartView_arc_arcChartView.setData(new int[]
                                {salarySum, perkSum}
                );
                break;
        }
    }
}