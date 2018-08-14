package com.modu.modusms.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.modu.modusms.R;
import com.modu.modusms.adapter.GroupListAdapter;
import com.modu.modusms.task.UserGetGroupListTask;
import com.modu.modusms.vo.ModuGroupVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GroupSelectMain extends AppCompatActivity {

    String userNo = "";
    List<ModuGroupVo> groupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_select_main);

        Intent intent = getIntent();
        userNo = intent.getStringExtra("userNo");
        System.out.println("userNo 가져 온건가? : " + userNo);
        Map<String, String> map = new HashMap<>();
        map.put("userNo", userNo);
        UserGetGroupListTask userGetGroupListTask = new UserGetGroupListTask();
        ListView groupListView = findViewById(R.id.groupList);
        try {
            String result = userGetGroupListTask.execute(map).get();
            Gson gson = new Gson();

            groupList = gson.fromJson(result, new TypeToken<List<ModuGroupVo>>() {
            }.getType());
            System.out.println("나오나 좀 보자 : " + groupList);

            GroupListAdapter groupListAdapter = new GroupListAdapter(groupList);
            groupListView.setAdapter(groupListAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ListView listView = findViewById(R.id.groupList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GroupSelectMain.this, groupList.get(position).getGroupName()+"클릭", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(GroupSelectMain.this,AccountbookActivity.class);
                intent1.putExtra("groupNo",String.valueOf(groupList.get(position).getGroupNo()));
                intent1.putExtra("userNo",userNo);
                System.out.println("그룹번호 : "+groupList.get(position).getGroupNo()+" / 유저번호 : "+userNo);
                startActivity(intent1);
            }
        });

    }
}
