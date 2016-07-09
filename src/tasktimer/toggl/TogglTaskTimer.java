package tasktimer.toggl;

import ch.simas.jtoggl.JToggl;
import com.intellij.tasks.LocalTask;
import tasktimer.api.TaskTimer;

/**
 * Created by drupality on 5/29/16.
 */
public class TogglTaskTimer implements TaskTimer {

    private boolean isConnected = false;

    private Object response;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public void setTask(LocalTask task) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }
    

    public String getProjectName() {
        return "";
    }
}
