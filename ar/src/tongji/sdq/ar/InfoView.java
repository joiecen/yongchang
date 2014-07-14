/***Designed by ShiDanqing.
 ***Tongji University***/
package tongji.sdq.ar;

import android.R.string;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class InfoView extends WebView {
    private MainApplication myApplication;
    public String newUrlString, lastUrlString;

    public InfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.loadUrl(Constant.homePageUrl);
        refreshWebThread.start();
    }

    Thread refreshWebThread = new Thread(new Runnable() {
        public void run() {
            while (true) {
                myApplication = (MainApplication) getContext().getApplicationContext();
                newUrlString = myApplication.getInfoUrl();
                if (newUrlString == null) {
                    newUrlString = Constant.homePageUrl;
                    lastUrlString = Constant.homePageUrl;
                }
                //Log.i("newUrl", newUrlString);
                if (!newUrlString.equals(lastUrlString)) {
                    InfoView.this.loadUrl(newUrlString);
                    lastUrlString = newUrlString;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


}