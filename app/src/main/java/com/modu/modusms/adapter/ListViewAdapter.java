package com.modu.modusms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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

    public ListViewAdapter(List<AccountbookVo> listViewItemList, List<AccountbookCategoryVo> categoryList) {
        this.listViewItemList = listViewItemList;
        this.categoryList = categoryList;
    }

//    public ListViewAdapter(Context context, ArrayList<AccountbookVo> listViewItemList) {
//        /*this.context = context;*/
//        this.listViewItemList = listViewItemList;
//    }

    public ListViewAdapter() {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("getView 들어옴");
        final Context context = parent.getContext();

        AccountbookVo accountbookVo = (AccountbookVo) getItem(position);
        System.out.println("아무것도 안오나? " + accountbookVo);


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_item_form, parent, false);
        }
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////             Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
//           convertView = inflater.from(context).inflate(R.layout.listview_item_form, null);
//            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_form, null);
//        }


        LinearLayout.LayoutParams viewWeight1 = new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.MATCH_PARENT, 3);
        LinearLayout.LayoutParams viewWeight2 = new LinearLayout.LayoutParams(80, LinearLayout.LayoutParams.MATCH_PARENT, 3);
        TextView usageTextView = (TextView) convertView.findViewById(R.id.usage);
        usageTextView.setLayoutParams(viewWeight1);
        TextView priceTextView = (TextView) convertView.findViewById(R.id.price);
        priceTextView.setLayoutParams(viewWeight2);
        TextView categoryTextView = (TextView) convertView.findViewById(R.id.category);
        categoryTextView.setLayoutParams(viewWeight2);
        TextView tagTextView = (TextView) convertView.findViewById(R.id.tag);
        tagTextView.setLayoutParams(viewWeight2);


//        AccountbookVo accountbookVo = listViewItemList.get(position);

        System.out.println("아무것도 안오나? " + accountbookVo.getAccountbookUsage());

        usageTextView.setText(accountbookVo.getAccountbookUsage());

        for (AccountbookCategoryVo tmp : categoryList) {
            if (tmp.getCategoryNo() == accountbookVo.getCategoryNo()) {
                categoryTextView.setText(tmp.getCategoryName());
            }
        }

        tagTextView.setText(accountbookVo.getTagName());
        if (accountbookVo.getAccountbookIncome() == 0) {
            priceTextView.setText(String.format("%,d", accountbookVo.getAccountbookSpend()) + "원");
        } else {
            priceTextView.setText(String.format("%,d", accountbookVo.getAccountbookIncome()) + "원");
        }
        System.out.println("뭐가 들어있냐 : " + usageTextView.getText());

        return convertView;
    }

//    public void addItem(AccountbookVo accountbookVo) {
//        AccountbookVo item = new AccountbookVo();
//        item.setAccountbookUsage(accountbookVo.getAccountbookUsage());
//        item.setCategoryName(accountbookVo.getCategoryName());
//        item.setTagName(accountbookVo.getTagName());
//        if (accountbookVo.getAccountbookIncome() == 0) {
//            item.setAccountbookIncome(accountbookVo.getAccountbookSpend());
//        } else {
//            item.setAccountbookIncome(accountbookVo.getAccountbookIncome());
//        }
//        listViewItemList.add(item);
//    }

}
