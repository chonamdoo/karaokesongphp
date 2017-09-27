package kr.ds.karaokesongphp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import kr.ds.handler.WebHandler;


public class DownWebViewActivity extends BaseActivity {
	private WebHandler mSavedata;
	private Bundle mBundle;
	private String mobliehomepage;
	private WebView WebHomepage;
	private LinearLayout ProgressArea;
	private Toolbar mToolbar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			mSavedata = (WebHandler) savedInstanceState.getParcelable("data");
			mobliehomepage = mSavedata.getmUrl();
		}else{
			mBundle = getIntent().getExtras();
			mSavedata = (WebHandler) mBundle.getParcelable("data");
			mobliehomepage = mSavedata.getmUrl();
		}
		
		setContentView(R.layout.activity_web);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		if (mToolbar != null) {
	    	mToolbar.setTitle("정보");
	    	setSupportActionBar(mToolbar);
	    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    }
		WebHomepage = (WebView) findViewById(R.id.web_homepage);
		ProgressArea = (LinearLayout) findViewById(R.id.progress_area);
		try {
			if(mobliehomepage != null){
				if(!mobliehomepage.matches("")){
					getWebView(mobliehomepage);
					finish();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("data", mSavedata);
	}
	
	public void getWebView(final String url) {
		final Handler handler = new Handler();
		Runnable doInit = new Runnable() {
			public void run() {
				WebHomepage.getSettings().setLoadWithOverviewMode(true);
				WebHomepage.getSettings().setUseWideViewPort(true);
				WebHomepage.setVerticalScrollbarOverlay(true);
				WebHomepage.getSettings().setSupportZoom(true);
				WebHomepage.getSettings().setBuiltInZoomControls(true);
				WebHomepage.getSettings().setJavaScriptEnabled(true); 
				WebHomepage.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
				WebHomepage.getSettings().setSaveFormData(true);
				WebHomepage.getSettings().setSavePassword(true);
				WebHomepage.loadUrl(url);
				WebHomepage.setWebViewClient(new WebViewClients());
				
				WebHomepage.setWebChromeClient(new WebChromeClient() {
					@Override
					public boolean onJsAlert(WebView view, String url,
							String message, final JsResult result) {
						new AlertDialog.Builder(DownWebViewActivity.this)
								.setMessage(message)
								.setPositiveButton(android.R.string.ok,
										new AlertDialog.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												result.confirm();
											}
										}).setCancelable(false).create().show();
						return true;

					};
				});
				WebHomepage.setDownloadListener(new DownloadListener() {
				    @Override
				    public void onDownloadStart(String url, String userAgent,
				            String contentDisposition, String mimetype,
				            long contentLength) {

				        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
				    }
				});
			}
		};
		ProgressArea.setVisibility(View.VISIBLE);
		handler.postDelayed(doInit, 0);
	}
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

	private class WebViewClients extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if(url.startsWith("market:")){
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
				return true;
			}else{
				view.loadUrl(url);
			}
			return true;
		}
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			ProgressArea.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			ProgressArea.setVisibility(View.GONE);
		}
	}
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (KeyCode == KeyEvent.KEYCODE_BACK) {
				if(WebHomepage.canGoBack()){
					WebHomepage.goBack();
				}else{
					finish();
				}
				return true;
			}
		}
		return super.onKeyDown(KeyCode, event);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
            finish();
            return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
