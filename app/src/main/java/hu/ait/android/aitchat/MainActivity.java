package hu.ait.android.aitchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TABLE_MESSAGES = "messages";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_MESSAGE = "message";
    @Bind(R.id.etMessage)
    EditText etMessage;
    @Bind(R.id.btnCreate)
    Button btnCreate;
    @Bind(R.id.btnRefresh)
    Button btnRefresh;
    @Bind(R.id.tvMessages)
    TextView tvMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

       /* ParseObject parseObject = new ParseObject("messages");
        parseObject.put("username", "Johnny");
        parseObject.put("messages", "AIT demo message");
        parseObject.saveInBackground(); */
    }

    @OnClick(R.id.btnCreate)
    public void sendMessage() {
        ParseObject message = new ParseObject(TABLE_MESSAGES);

        message.put(KEY_USERNAME, ParseUser.getCurrentUser().getUsername());

        message.put(KEY_MESSAGE, etMessage.getText().toString());

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();

                    ParsePush push = new ParsePush();
                    push.setChannel("AITChat");

                    push.setMessage(ParseUser.getCurrentUser().getUsername()+": "+etMessage.getText().toString());
                    push.sendInBackground();
                    etMessage.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Message sent failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.btnRefresh)
    public void refreshMessage() {
        ParseQuery<ParseObject> pq = ParseQuery.getQuery(TABLE_MESSAGES);
        pq.setLimit(500);
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects,
                             ParseException e) {
                StringBuilder sb = new StringBuilder();
                for (ParseObject po : parseObjects) {
                    sb.append(po.getString(KEY_USERNAME) + ": " +
                            po.getString(KEY_MESSAGE) + "\n");
                }

                tvMessages.setText(sb.toString());
            }
        });
    }
}
