package kr.ds.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016-04-19.
 */
public class DsDateUtils {

    private static DsDateUtils keyBoardUtils = null;

    public DsDateUtils(){

    }
    public static DsDateUtils getInstance(){
        if(keyBoardUtils == null){
            synchronized (DsDateUtils.class){
                if(keyBoardUtils == null){
                    keyBoardUtils = new DsDateUtils();
                }
            }
        }
        return keyBoardUtils;
    }

    public String getDateDay(String date, String dateType) throws Exception {
        String day = "" ;
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
        Date nDate = dateFormat.parse(date) ;

        Calendar cal = Calendar.getInstance() ;
        cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0,4)));
        cal.set(Calendar.MONTH, Integer.parseInt(date.substring(5,7))-1);
        cal.set(Calendar.DATE, Integer.parseInt(date.substring(8,10)));
        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

        switch(dayNum){
            case 1:
                day = "일";
                break ;
            case 2:
                day = "월";
                break ;
            case 3:
                day = "화";
                break ;
            case 4:
                day = "수";
                break ;
            case 5:
                day = "목";
                break ;
            case 6:
                day = "금";
                break ;
            case 7:
                day = "토";
                break ;
        }
        return new SimpleDateFormat("yy. mm. dd").format(nDate)+"("+day+")";
    }

    public Calendar getSunDayDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal;
    }
    public Calendar getPrevSunDayDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day);
        cal.add(Calendar.DATE, -7);
        cal.get(cal.DAY_OF_WEEK);//상수셋업
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal;
    }
    public Calendar getFrontSunDayDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day);
        cal.add(Calendar.DATE, 7);
        cal.get(cal.DAY_OF_WEEK);//상수셋업
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal;
    }

    public Calendar getSaturDayDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return cal;
    }
    public Calendar getPrevSaturDayDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day);
        cal.add(Calendar.DATE, -7);
        cal.get(cal.DAY_OF_WEEK);//상수셋업
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return cal;
    }
    public Calendar getFrontSaturDayDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day);
        cal.add(Calendar.DATE, 7);
        cal.get(cal.DAY_OF_WEEK);//상수셋업
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return cal;
    }







    public String SetSunDayDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        int inYear = 0;
        int inMonth = 0;
        int inDay = 0;

        inYear = cal.get(cal.YEAR);
        inMonth = cal.get(cal.MONTH)+1;
        inDay = cal.get(cal.DAY_OF_MONTH);

        return Integer.toString(inYear)+"."+Integer.toString(inMonth)+"."+Integer.toString(inDay);
    }




    public String SetChangeSunDayDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        cal.get(cal.DAY_OF_WEEK);//상수셋업
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        int inYear = 0;
        int inMonth = 0;
        int inDay = 0;

        inYear = cal.get(cal.YEAR);
        inMonth = cal.get(cal.MONTH)+1;
        inDay = cal.get(cal.DAY_OF_MONTH);

        return Integer.toString(inYear)+"."+Integer.toString(inMonth)+"."+Integer.toString(inDay);
    }
    public String SetSaturDayDate(){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        int inYear = 0;
        int inMonth = 0;
        int inDay = 0;

        inYear = cal.get(cal.YEAR);
        inMonth = cal.get(cal.MONTH)+1;
        inDay = cal.get(cal.DAY_OF_MONTH);

        return Integer.toString(inYear)+"."+Integer.toString(inMonth)+"."+Integer.toString(inDay);
    }




    public String SetChangeSaturDayDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        cal.get(cal.DAY_OF_WEEK);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        int inYear = 0;
        int inMonth = 0;
        int inDay = 0;

        inYear = cal.get(cal.YEAR);
        inMonth = cal.get(cal.MONTH)+1;
        inDay = cal.get(cal.DAY_OF_MONTH);

        return Integer.toString(inYear)+"."+Integer.toString(inMonth)+"."+Integer.toString(inDay);
    }

    public int getYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.YEAR);
    }

    public int getMonth(){
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.MONTH)+1;
    }

    public int getDay(){
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.DAY_OF_WEEK);
    }



}
