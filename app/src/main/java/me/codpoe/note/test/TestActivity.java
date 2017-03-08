package me.codpoe.note.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codpoe.note.R;
import me.codpoe.note.widget.MarkdownView;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.markdown_view)
    MarkdownView mMarkdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);

//        mMarkdownView.post(new Runnable() {
//            @Override
//            public void run() {
//                mMarkdownView.parse("# 123\n\n- 12\n\n`test`\n\n- 213\n\n```\npublic void set(){12313123133123213133333333333333333;}\n```\n1\n2\n\n3\n\n![](http://ww1.sinaimg.cn/large/a45cbeeajw1fagclb3gdsj2076056dgf.jpg)\n\n![](http://ww4.sinaimg.cn/large/a45cbeeajw1fagclxtrr6j20sg0lcqaj.jpg)" +
//                        "\n\n1\n\n2\n\n3\n\n4\n\n6\n\n7\n\n6\n\n8\n4\n4\n\n\n- qwe\n\n wqeqe\n- 123\n- 213\n\n 21331231");
//            }
//        });
    }

}
