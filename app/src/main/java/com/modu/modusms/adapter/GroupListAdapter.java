package com.modu.modusms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.modu.modusms.R;
import com.modu.modusms.vo.ModuGroupVo;

import java.util.List;

public class GroupListAdapter extends BaseAdapter {

    private List<ModuGroupVo> groupList;

    public GroupListAdapter() {
    }

    public GroupListAdapter(List<ModuGroupVo> groupList) {
        this.groupList = groupList;
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("groupList의 getView 들어옴");
        final Context context = parent.getContext();

        ModuGroupVo groupVo = (ModuGroupVo) getItem(position);
        System.out.println("내용체크 : " + groupVo);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_list_item_form, parent, false);
        }
        ImageView groupImg = convertView.findViewById(R.id.groupImg);
        groupImg.setImageBitmap(new ImageLoader().
                getBitmapImg(groupVo.getGroupImg()));
        TextView groupName = convertView.findViewById(R.id.groupName);
        groupName.setText(groupVo.getGroupName());
        TextView groupDesc = convertView.findViewById(R.id.groupDescription);
        groupDesc.setText(groupVo.getGroupExplain());

        return convertView;
    }

}
