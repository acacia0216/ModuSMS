package com.modu.modusms.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.modu.modusms.R;
import com.modu.modusms.adapter.ListViewAdapter;
import com.modu.modusms.task.AccountbookGetListTask;
import com.modu.modusms.task.AccountbookSaveTask;
import com.modu.modusms.vo.AccountbookCategoryVo;
import com.modu.modusms.vo.AccountbookResultMapVo;
import com.modu.modusms.vo.AccountbookVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AccountbookActivity extends AppCompatActivity {

    List<AccountbookCategoryVo> categoryList = null;
    List<AccountbookVo> accountList = null;
    ListViewAdapter listViewAdapter;
    AccountbookResultMapVo data;
    String groupNo = "";
    String userNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountbook);

        Intent intent = getIntent();
        groupNo = intent.getStringExtra("groupNo");
        userNo = intent.getStringExtra("userNo");
        System.out.println("그룹번호 : "+groupNo+" / 유저번호 : "+userNo);
        Map<String, String> params = new HashMap<>();
        params.put("groupNo", groupNo);
        params.put("spendFlag", "spend");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy / MM");
        SimpleDateFormat simpleMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simpleYear = new SimpleDateFormat("yyyy");
        String month = "";

        if (simpleMonth.format(date).length() < 2) {
            month = simpleYear.format(date)+" / 0"+simpleMonth.format(date);
        } else {
            month = simpleDateFormat.format(date);
        }
        params.put("month", month);

        AccountbookGetListTask accountbookGetListTask = new AccountbookGetListTask();

        try {
            String result = accountbookGetListTask.execute(params).get();
            Gson gson = new Gson();
            data = gson.fromJson(result, AccountbookResultMapVo.class);
            categoryList = data.getCategoryList();

            AccountbookCategoryVo tmpCate = new AccountbookCategoryVo(0,"미분류",0,0);
            List<AccountbookCategoryVo> tmpList = new ArrayList<>();
            tmpList.add(tmpCate);
            tmpList.addAll(categoryList);
            categoryList = tmpList;

            accountList = data.getAccountList();
            System.out.println("data : " + data);
            System.out.println("categoryList : " + categoryList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AccountbookActivity.this, data.getAccountList().get(position).getAccountbookUsage()+"클릭됨", Toast.LENGTH_SHORT).show();
            }
        });


//        카테고리 리스트 스피너
        Spinner spinner = findViewById(R.id.inputCategorySpinner);
        List<String> categoryNameList = new ArrayList<>();
        for (AccountbookCategoryVo categoryVo : categoryList) {
            categoryNameList.add(categoryVo.getCategoryName());
        }
        ArrayAdapter categorySpinner = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categoryNameList);
        spinner.setAdapter(categorySpinner);


//        사용내역 리스트
//        ListView listView = (ListView) findViewById(R.id.listView);
        LinearLayout.LayoutParams viewWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(viewWeight);
        listViewAdapter = new ListViewAdapter(accountList, categoryList);
        listView.setAdapter(listViewAdapter);

//        달력
        TextView inputDate = findViewById(R.id.inputDate);
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDatePicker();
            }
        });

//        데이터 저장/삽입
        Button btnSave = findViewById(R.id.inputSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dateText = findViewById(R.id.inputDate);
                String date = (String) dateText.getText();
                String dateSplit[] = date.split("/");
                String y = dateSplit[0].trim();
                String m = dateSplit[1].trim();
                if (m.length() < 2) {
                    m = "0" + m;
                }
                String d = dateSplit[2].trim();

                EditText usageText = findViewById(R.id.inputUsage);
                String usage = String.valueOf(usageText.getText());
                EditText priceText = findViewById(R.id.inputPrice);
                String price = String.valueOf(priceText.getText());
                Spinner categoryNameSpinner = findViewById(R.id.inputCategorySpinner);
                String categoryName = categoryNameSpinner.getSelectedItem().toString();
                int categoryNo = 0;
                for (AccountbookCategoryVo tmp : categoryList) {
                    if (tmp.getCategoryName().equals(categoryName)){
                        categoryNo = tmp.getCategoryNo();
                    }
                }

//                System.out.println("잘 가져왔나 보자"+date+"/"+usage+""+price+""+categoryName);

                Map<String, String> params = new HashMap<>();
                params.put("usage", usage);
                params.put("spend", price);
                params.put("category", String.valueOf(categoryNo));
                params.put("groupNo", "2");
                params.put("date", y + "년 " + m + "월 " + d + "일");
                params.put("spendFlag", "spend");

                AccountbookSaveTask accountbookSaveTask = new AccountbookSaveTask();
                usageText.setText("");
                priceText.setText("");
                dateText.setText("");
                try {
                    String result = accountbookSaveTask.execute(params).get();

                    Gson gson = new Gson();
                    AccountbookVo data = gson.fromJson(result, AccountbookVo.class);
                    accountList.add(data);
                    listViewAdapter.notifyDataSetChanged();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void DialogDatePicker() {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            // onDateSet method
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date_selected = String.valueOf(year) + " /" + String.valueOf(monthOfYear + 1) + " /"
                        + String.valueOf(dayOfMonth);
                TextView inputDate = findViewById(R.id.inputDate);
                inputDate.setText(date_selected);
            }
        };
        DatePickerDialog alert =
                new DatePickerDialog(this, mDateSetListener, cyear, cmonth, cday);
        alert.show();
    }
}
