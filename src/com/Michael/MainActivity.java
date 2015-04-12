package com.Michael;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.Michael.AccountBook.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.*;

public class MainActivity extends Activity {

    private MySQLiteOpenHelper dbHelper;
    private TextView text_main_date;
    private RadioGroup radioGroup;
    private EditText editText_mian_input;
    private List<Map<String, Object>> totalList;
    private SimpleAdapter adapter;
    private ListView listView_main_bill;
    //收入、支出状态
    private String state;
    //收入、支出的数组
    private String[] arr;
    //支出数组
   // private String[] arr;

    //
    private Spinner spinner;
    //第一个下拉框
    private String item1;

    private String[] arrBig;
    private String[][] arrSmail;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init();

    }

    public void init() {
        text_main_date = (TextView) findViewById(R.id.text_main_date);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        editText_mian_input = (EditText) findViewById(R.id.editText_mian_input);
        dbHelper = new MySQLiteOpenHelper(this);
        totalList = new ArrayList<Map<String, Object>>();
        listView_main_bill = (ListView) findViewById(R.id.listView_main_bill);


        //listView添加适配器
        adapter = new SimpleAdapter(this, totalList, R.layout.layout_listview_itme, new String[]{"state", "money", "bigKind", "smallKind", "date"},
                new int[]{R.id.textView_item_state, R.id.textView_item_money, R.id.textView_item_bigKind, R.id.textView_item_smallKind, R.id.textView_item_date});

        listView_main_bill.setAdapter(adapter);
        reloadListView();


        //收入类型
        arr = new String[]{"工资","外快"};
        changeSpinner();
    }
    public void changeSpinner(){
        spinner = (Spinner) findViewById(R.id.spinner1);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1, arr);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item1 = adapter1.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void clickButton(View view) {
        switch (view.getId()) {
            //选择日期
            case R.id.text_main_date:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int monthOfYear = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dateString = year + "-" + (monthOfYear + 1)
                                        + "-" + dayOfMonth;
                                text_main_date.setText(dateString);
                            }
                        }, year, monthOfYear, dayOfMonth);
                dDialog.show();
                break;

            //确定记账
            case R.id.button_confirm:

                //输入的金额
                String money = editText_mian_input.getText().toString();
                //输入时选择的日期
                String date = text_main_date.getText().toString();

                if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton_main_expend) {
                    state = "支出";

                }
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton_main_income) {
                    state = "收入";
                }

                if (TextUtils.isEmpty(money) || date.equals("选择日期")) {
                    Toast.makeText(this, "请检查金额和日期是否填写正确！", Toast.LENGTH_SHORT).show();
                } else {
                    String insertSql = "insert into tb_myAccount (state,money,bigKind,smallKind,date) values(?,?,?,?,?)";

                    boolean flag = dbHelper.execData(insertSql,
                            new String[]{state, money, item1, null, date});
                    if (flag) {
                        reloadListView();
                    }
                }
        }
    }

    public void changeSpinner(View view){
        switch (view.getId()){
            case R.id.radioButton_main_expend:
                arr = new String[]{"吃","穿","住","行"};
                changeSpinner();
                break;
            case R.id.radioButton_main_income:
                arr = new String[]{"工资","外快"};
                changeSpinner();
                break;
        }
    }

    public void reloadListView() {
        totalList.clear();
        List<Map<String, Object>> currentList = dbHelper.selectList(
                "select state,money,bigKind,smallKind,date from tb_myAccount order by id desc", null);
        totalList.addAll(currentList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_intent_circleChartActivity:
                Intent intent = new Intent();
                intent.setClass(this,ArcChartActivity.class);
                startActivity(intent);
                break;
            case R.id.action_intent_outcircleChartActivity:
                Intent intent1 = new Intent();
                intent1.setClass(this,outArcChartActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

