package hu.ait.android.aitchat;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;

/**
 * Created by joe on 11/9/15.
 */
public class AITChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //gotten from settings->keys use the first two keys (Application Key and Client ID)
        //Parse.initialize(this, "YtmkjB1NPi27SehelKhrREQiSfjKbR4RUmrLhvC4", "N7xvElWDOnALC1qXsSriwNESfEE4IaWKPNY5UqIR");
        Parse.initialize(this,
                "RNIdjPNSQtMM9kQTIk9YfuRc2tZpSJD03REzUaks",
                "7VDdgWQbeHcgBoQMibkuV7FlRPlGzLp2zhzIMihT");

        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("AITChat");

        ParseObject parseObject = new ParseObject("messages");
        parseObject.put("username", "Johny");
        parseObject.put("message", "AIT demo message");
        parseObject.saveInBackground();

    }
}
