package com.modu.modusms.task;

import android.os.AsyncTask;

import java.util.Map;

public class AccountbookSaveTask extends AsyncTask<Map<String, String>, Integer, String> {

    public static String ip = "121.138.83.18"; // 자신의 IP주소를 쓰시면 됩니다.

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

        // Http 요청 준비 작업
        String groupNo = String.valueOf(maps[0].get("groupNo"));
        System.out.println("save두인 들어옴: "+groupNo);
        HttpClient.Builder http = new HttpClient.Builder
                ("POST", "http://" + ip + ":8088/Modu/accountbook/"+groupNo+"/saveAccountbookForAndroid"); //포트번호,서블릿주소
        // Parameter 를 전송한다.
        http.addAllParameters(maps[0]);

        //Http 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();
        System.out.println("응답 상태코드 : "+statusCode);
        // 응답 본문 가져오기
        String body = post.getBody();

        System.out.println("리턴은 어디서받나요...."+body);
        return body;
    }

    @Override
    protected void onPostExecute(String result) { //서블릿으로부터 값을 받을 함수

        System.out.println("그럼 여긴 뭐하는거지;;"+result);

    }
}
