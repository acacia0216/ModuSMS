package com.modu.modusms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.modu.modusms.R;
import com.modu.modusms.vo.AccountbookCategoryVo;
import com.modu.modusms.vo.AccountbookVo;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    /*Context context;*/
    private List<AccountbookVo> listViewItemList;
    private List<AccountbookCategoryVo> categoryList;
    LayoutInflater layoutInflater;


    public ListViewAdapter() {
    }

    public ListViewAdapter(Context context,List<AccountbookVo> listViewItemList, List<AccountbookCategoryVo> categoryList) {
        this.listViewItemList = listViewItemList;
        this.categoryList = categoryList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return listViewItemList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("getView 들어옴");

        AccountbookVo accountbookVo = (AccountbookVo) getItem(position);
        System.out.println("포지션 번호 : " + position);
//        System.out.println("사용내역 : " + accountbookVo.getAccountbookUsage() + "/" + accountbookVo.getAccountbookSpend() + "/(" + accountbookVo.getAccountbookRegDate() + ")/" + getItemId(position));
        System.out.println("사용내역 : "+accountbookVo.toString());

//        System.out.println("확인용"+accountbookVo.getAccountbookRegDate()+" 이거랑 "+accountbookVo.getCategoryName());
        System.out.println("확인용"+accountbookVo.getAccountbookRegDate().contains("/"));

        if (accountbookVo.getAccountbookRegDate().contains("/")){
            String tmpDate[] = accountbookVo.getAccountbookRegDate().split("/");
            accountbookVo.setAccountbookRegDate(tmpDate[0]+"년 "+tmpDate[1]+"월 "+tmpDate[2]+"일");
            for (AccountbookCategoryVo tmp : categoryList){
                if (accountbookVo.getCategoryNo() == tmp.getCategoryNo()){
                    accountbookVo.setCategoryName(tmp.getCategoryName());
                }
            }
        }

        LinearLayout.LayoutParams viewWeight1 = new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.MATCH_PARENT, 3);
        LinearLayout.LayoutParams viewWeight2 = new LinearLayout.LayoutParams(80, LinearLayout.LayoutParams.MATCH_PARENT, 3);
        int res = 0;
        if (convertView == null) {

            res = accountbookVo.getType();
            switch (res) {
                case 0:
                    res = R.layout.listview_item_date_form;
                    break;
                case 1:
                    res = R.layout.listview_item_form;
                    break;
            }
            convertView = layoutInflater.inflate(res, parent, false);
        }

        switch (accountbookVo.getType()) {
            case 0:
                TextView usageTextView = (TextView) convertView.findViewById(R.id.usage);
                TextView priceTextView = (TextView) convertView.findViewById(R.id.price);
                TextView categoryTextView = (TextView) convertView.findViewById(R.id.category);
                TextView tagTextView = (TextView) convertView.findViewById(R.id.tag);
                usageTextView.setLayoutParams(viewWeight1);
                priceTextView.setLayoutParams(viewWeight2);
                categoryTextView.setLayoutParams(viewWeight2);
                tagTextView.setLayoutParams(viewWeight2);

                TextView dateTextView = convertView.findViewById(R.id.listDate);
                dateTextView.setText(accountbookVo.getAccountbookRegDate());

                usageTextView.setText(accountbookVo.getAccountbookUsage());

                categoryTextView.setText(accountbookVo.getCategoryName());

                tagTextView.setText(accountbookVo.getTagName());
                if (accountbookVo.getAccountbookIncome() == 0) {
                    priceTextView.setText(String.format("%,d", accountbookVo.getAccountbookSpend()) + "원");
                } else {
                    priceTextView.setText(String.format("%,d", accountbookVo.getAccountbookIncome()) + "원");
                }
                break;
            case 1:
                TextView usageTextView1 = (TextView) convertView.findViewById(R.id.usage);
                TextView priceTextView1 = (TextView) convertView.findViewById(R.id.price);
                TextView categoryTextView1 = (TextView) convertView.findViewById(R.id.category);
                TextView tagTextView1 = (TextView) convertView.findViewById(R.id.tag);
                usageTextView1.setLayoutParams(viewWeight1);
                priceTextView1.setLayoutParams(viewWeight2);
                categoryTextView1.setLayoutParams(viewWeight2);
                tagTextView1.setLayoutParams(viewWeight2);


                usageTextView1.setText(accountbookVo.getAccountbookUsage());

                categoryTextView1.setText(accountbookVo.getCategoryName());

                tagTextView1.setText(accountbookVo.getTagName());
                if (accountbookVo.getAccountbookIncome() == 0) {
                    priceTextView1.setText(String.format("%,d", accountbookVo.getAccountbookSpend()) + "원");
                } else {
                    priceTextView1.setText(String.format("%,d", accountbookVo.getAccountbookIncome()) + "원");
                }
                break;
        }
        return convertView;
    }

}
