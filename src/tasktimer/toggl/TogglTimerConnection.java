package tasktimer.toggl;

import com.intellij.util.io.HttpRequests;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import com.intellij.util.Base64;
import tasktimer.api.TaskTimer;
import tasktimer.api.TimerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLConnection;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

/**
 * Created by drupality on 5/29/16.
 */
public class TogglTimerConnection implements TimerConnection {

    private String token;

    private Exception exception;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    @Nullable
    public TaskTimer connect() {
        String token = this.getToken();

        try {
            Object response = HttpRequests.request("https://www.toggl.com/api/v8/me")
                    .accept("application/json")
                    .tuner(new HttpRequests.ConnectionTuner() {
                        @Override
                        public void tune(@NotNull URLConnection connection) throws IOException {
                            String credentials = Base64.encode(new String(token + ":api_token").getBytes());
                            connection.setRequestProperty("Authorization", "Basic " + credentials);

                        }
                    })
                    .connect(new HttpRequests.RequestProcessor<Object>() {
                        @Override
                        public Object process(@NotNull HttpRequests.Request request) throws IOException {
                            BufferedReader reader = request.getReader();
                            StringBuilder jsonBuilder = new StringBuilder();
                            String line;
                            while (null != (line = reader.readLine())) {
                                jsonBuilder.append(line);
                            }
                            return JSONValue.parse(jsonBuilder.toString());
                        }
                    });

            TogglTaskTimer timer = new TogglTaskTimer();
            timer.setResponse(response);
            return timer;
        } catch (IOException e) {
            this.setException(e);
            return null;
        }
    }
}
