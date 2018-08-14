package com.modu.modusms.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.modu.modusms.R;
import com.modu.modusms.task.UserGetInfoTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    UserGetInfoTask userGetInfoTask = new UserGetInfoTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = findViewById(R.id.userEmail);
                EditText passwordText = findViewById(R.id.userPassword);
                String userEmail = String.valueOf(emailText.getText());
                String userPassword = String.valueOf(passwordText.getText());

                Map<String,String> map = new HashMap<>();
                map.put("userEmail",userEmail);
                map.put("userPassword",userPassword);

                try {
                    String checkStatus = userGetInfoTask.execute(map).get();
                    if (checkStatus.equals("0")){
                        Toast.makeText(MainActivity.this, "가입된 이메일이 없거나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,GroupSelectMain.class);
                        intent.putExtra("userNo",checkStatus);
                        startActivity(intent);
                        finish();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
