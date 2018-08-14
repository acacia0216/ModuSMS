package com.modu.modusms.task;

import android.os.AsyncTask;

import java.util.Map;

public class UserGetGroupListTask extends AsyncTask<Map<String, String>, Integer, String> {

    public static String ip = "222.106.22.118"; // 자신의 IP주소를 쓰시면 됩니다.

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

        // Http 요청 준비 작업
        System.out.println("getGroupList 두인 들어옴: ");
        HttpClient.Builder http = new HttpClient.Builder
                ("POST", "http://" + ip + ":8088/Modu/getGroupListForAndroid"); //포트번호,서블릿주소
        // Parameter 를 전송한다.
        http.addAllParameters(maps[0]);

        //Http 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();
        System.out.println("응답 상태코드 : " + statusCode);
        // 응답 본문 가져오기
        String body = post.getBody();

        System.out.println("리턴은 어디서받나요...." + body);
        return body;
    }

    @Override
    protected void onPostExecute(String result) { //서블릿으로부터 값을 받을 함수
        System.out.println("그룹리스트 가져온 결과 : " + result);
//        UserGroupListVo userNoGroupListVo;
//
//        Gson gson = new Gson();
//        userNoGroupListVo = gson.fromJson(result, UserGroupListVo.class);
//
//        String userNo = userNoGroupListVo.getUserNo();
//
//        List<ModuGroupVo> groupList = userNoGroupListVo.getGroupList();
//        System.out.println("유저번호 : " + userNo);
//        System.out.println("그룹리스트 : " + groupList);

    }
}


