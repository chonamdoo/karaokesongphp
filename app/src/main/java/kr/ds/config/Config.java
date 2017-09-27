package kr.ds.config;

import android.content.Context;

public class Config {
	public Context mContext;
	
	public static String URL = "http://parsingds.cafe24.com/app/";
	public static String URL_XML = "karaokesongphp/";
	public static String URL_LIST = "list.php";
	public static String URL_SEARCH = "search.php";
	public static String URL_BOOKMARK = "bookmark.php";
	public static String URL_CHANNEL = "channel.php";
	public static String URL_RECOM = "recom.php";
	public static String URL_LOG = "log.php";
	public static String URL_DOWN = "/link/down.php";

	public static String URL_MAIN1 = "main_1.php";
	public static String URL_MAIN2 = "main_2.php";
	public static String URL_MAIN3 = "main_3.php";
	public static String URL_MAIN4 = "main_4.php";

	public static String URL_SEARCH_LOG = "search_log.php";



	public static String URL_GCM = "gcm/gcm.php";
	public static String URL_SEND_CHECK = "gcm/gcm_send_check.php";
	public static String URL_SEND_UPDATE = "gcm/gcm_send_update.php";


	//gcm
	public static final String ANDROID_ID = "android_id";
	public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
	public static final String REGISTRATION_COMPLETE = "registrationComplete";
	public static final String TOKEN = "token";
	//gcmend

	public static final String PREFERENCE_NEW = "2017-06-15-01";
	public static final String YOUTUBE_AUTO_PLAY = "youtube_auto_play";
	public static final String YOUTUBE_PLAY_END_RECORD_END = "youtube_play_end_record_end";
	public static final String YOUTUBE_PLAY_RECORD = "youtube_play_record";
	public static final String RECORD_REFRASH = "record_refrash";


	public final static String PREFERENCE_LOCATION =  "PREFERENCE_LOCATION";//위치동의
	public final static String PREFERENCE_PREV_LAT =  "PREFERENCE_PREV_LAT";
	public final static String PREFERENCE_PREV_LON =  "PREFERENCE_PREV_LON";

	public final static int MARKET = 1;
	public final static int TSTORE = 2;
	public final static int TYPE = MARKET;

	public static String APP_DOWN_URL_MARKET = "kr.ds.karaokesongphp";
	public static String APP_DOWN_TITLE = "Hát Karaoke";

	public static final String GCM_INTENT_FILLTER = "kr.ds.GCM_INTENT_FILLTER";
}
