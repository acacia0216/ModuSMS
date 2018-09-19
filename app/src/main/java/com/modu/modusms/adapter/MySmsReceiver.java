package com.modu.modusms.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.modu.modusms.R;
import com.modu.modusms.task.AccountbookSaveTask;
import com.modu.modusms.vo.AccountbookCategoryVo;
import com.modu.modusms.vo.AccountbookVo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySmsReceiver extends BroadcastReceiver {
    String content = "";
    String del[] = {
            /*농협 예외*/
            "\\[Web발신\\]\\s농협[^\\d\\s]*",
            "\\[web발신\\]\\s농협[^\\d\\s]*",
            /*우리카드 예외*/
            "우리(카드)?\\(\\d{4}\\)",
            /*KB국민카드 예외*/
            "\\s사용$",
            "승인", "일시불", "출금", "해외", "Web발신", "web발신","잔액","카드번호","누적","지급",
            /*할부*/
            "\\D?\\d{1,3}개월\\D?",
            /*날짜*/
            "\\d\\d\\/\\d\\d",
            /*시간*/
            "\\d\\d:\\d\\d",
            "(\\d+,)?(\\d+,)?\\d+원?",
            /*이용자명*/
            "[가-힣\\*]{2,5}님|[가-힣\\*]{0,3}([가-힣]\\*|\\*[가-힣])[가-힣\\*]{0,3}",
            /*카드사명*/
            "[^\\d\\s]+카드\\S?|[^\\d\\s]+체크\\S?",
            /*카드번호*/
            "\\(?[\\d\\*\\-]*\\*[\\d\\*\\-]*\\)?|\\([\\d\\*]{4}\\)",
            /*누적액,잔액,지급가능액*/
            "(누적|잔액|지급)[^\\d\\s]*\\s?[\\d,]*\\d원?",
            "\\]",
            "\\[",
            "KB\\s",
            /*허용문자*/
            "[^가-힣A-Za-z\\d,.\\s]/g"
    };

    @Override
    public void onReceive(Context context, Intent intent) {

//        String price = "\\d+(?=원)";
//        String date = "/\\d\\d\\/\\d\\d/";

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.v("asdf", "onReceive 들어옴");
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

            Toast.makeText(context, "문자메시지가 도착했습니다.", Toast.LENGTH_SHORT).show();

            // SMS 메시지를 파싱합니다.
            Bundle bundle = intent.getExtras();
            if (bundle != null) { // 수신된 내용이 있으면
                // 실제 메세지는 Object타입의 배열에 PDU 형식으로 저장됨

                // 문자 메시지는 pdus란 종류 값으로 들어있음
                Object[] pdus = (Object[]) bundle.get("pdus");

                SmsMessage[] msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    // PDU 포맷으로 되어 있는 메시지를 복원합니다.
                    msgs[i] = SmsMessage
                            .createFromPdu((byte[]) pdus[i]);

                    String sender = msgs[i].getOriginatingAddress();
                    content = msgs[i].getMessageBody().toString();

                    Log.i("sender", sender);
                    Log.i("content", content);

                    if (sender.equals("01000000000")) {
                        int startIdx = content.indexOf("[");
                        int endIdx = content.indexOf("]");
                        String authNumber = content.substring(startIdx + 1, endIdx);
                        Log.i("authNumber", authNumber);
                    }

                }
            }
        }
        String date = "";
        String money = "";
        String usage = "";

//        롯데카드 예외처리
        String temp = content;
        if(temp.contains("롯데 ")){
            content = temp.replaceAll("롯데 ","롯데카드 ");
        }
//        통화표시 없을 때
//        if (temp.contains(",")){
//            String comma[] = temp.split(",");
//            String commaDel = comma[comma.length-1].substring(0,4);
//
//            commaDel = commaDel.trim();
//            if (commaDel.length()<4){
//                changeDel += "원 ";
//            }
//        }

        Pattern moneyPattern = Pattern.compile("(\\d+,)?(\\d+,)?\\d+원?");
        Pattern datePattern = Pattern.compile("\\d\\d\\/\\d\\d");
        Matcher moneyMatcher = moneyPattern.matcher(content);
        Matcher dateMatcher = datePattern.matcher(content);
        while (true) {
            if (moneyMatcher.find()) {
                money = moneyMatcher.group();
            }
            if (dateMatcher.find()) {
                date = dateMatcher.group();
            }
            if (!moneyMatcher.find() && !dateMatcher.find()) {
                break;
            }
        }
        usage = content;
        for (int i = 0; i < del.length; i++) {
            usage = usage.replaceAll(del[i], "");
        }
        if (!money.contains("원")){
            money+="원";
        }
        System.out.println("금액 어떤결과가 나오나요 : " + money);
        System.out.println("날짜 어떤결과가 나오나요 : " + date);
        usage = usage.trim();
        System.out.println("사용내역 어떤결과가 나오나요 : " + usage);

//        현재 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH)+1;
        int day = calendar.get(calendar.DATE);

        String m = String.valueOf(month);
        if (m.length() < 2){
            m = "0"+m;
        }

        String currentDate = String.valueOf(year)+"년 "+m+"월 "+day+"일";
        System.out.println(currentDate);

//        금액 int형으로 바꾸기
        money = money.replaceAll(",","");
        money = money.replaceAll("원","");
        String price = String.valueOf(money);

        Map<String, String> params = new HashMap<>();
        params.put("usage", usage);
        params.put("spend", price);
        params.put("category", "0");
        params.put("groupNo", "3");
        params.put("date", currentDate);
        params.put("spendFlag", "spend");

        System.out.println("usage : "+usage);
        System.out.println("price : "+price);

//        일반문자 제외하기
        if(date != "" && price != "") {
            AccountbookSaveTask accountbookSaveTask = new AccountbookSaveTask();
            accountbookSaveTask.execute(params);
        }

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
