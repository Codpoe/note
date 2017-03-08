package me.codpoe.note.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Refer to https://github.com/mittsuu/MarkedView-for-Android
 * Created by Codpoe on 2016/12/5.
 */

public final class MarkdownView extends WebView {

    private static final String TAG = MarkdownView.class.getSimpleName();
    private static final String IMAGE_PATTERN = "!\\[(.*)\\]\\((.*)\\)";

    private String mText;
    private String mCode;
    private boolean mCodeScrollable = true;

    public MarkdownView(Context context) {
        this(context, null);
    }

    public MarkdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarkdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowUniversalAccessFromFileURLs(true);
        getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                evaluateJavascript(mCode, null);
            }
        });
    }

    private String img2Base64(String text) {
        Pattern ptn = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = ptn.matcher(text);
        if (!matcher.find()) {
            return text;
        }

        // the url is a network url
        String imgUrl = matcher.group(2);
        if (isNetUrl(imgUrl)) {
            return text;
        }

        // the local url is not an image url
        String base64Type = getBase64Type(imgUrl);
        if(base64Type.equals("")){
            return text;
        }

        // the url is a local image url
        File file = new File(imgUrl);
        byte[] bytes = new byte[(int) file.length()];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (Exception e) {
            Log.e("MarkdownView", e.getMessage());
        }

        return text.replace(imgUrl, base64Type + Base64.encodeToString(bytes, Base64.NO_WRAP));
    }

    private boolean isNetUrl(String text) {
        return text.startsWith("http://") || text.startsWith("https://");
    }

    private String getBase64Type(String text) {
        if (text.endsWith(".png")) {
            return "data:image/png;base64,";
        } else if (text.endsWith(".jpg") || text.endsWith(".jpeg")) {
            return "data:image/jpg;base64,";
        } else if (text.endsWith(".gif")) {
            return "data:image/gif;base64,";
        } else {
            return "";
        }
    }

    public void parse(String text, String theme) {
        mText = img2Base64(text).replace("\n", "\\\\n").replace("\"", "\\\"").replace("'", "\\\'").replace("\r", "");
        mCode = String.format(
                "javascript:parse('%s', '%s', %b)",
                mText,
                theme,
                mCodeScrollable
        );
        loadUrl("file:///android_asset/html/markdown.html");
    }

    public void setCodeScrollable(boolean b) {
        mCodeScrollable = b;
    }

    public void changeTheme(String theme) {
        mCode = String.format(
                "javascript:parse('%s', '%s', %b)",
                mText,
                theme,
                mCodeScrollable
        );
        loadUrl("file:///android_asset/html/markdown.html");
    }

}
