package tasktimer;

import com.intellij.util.concurrency.SwingWorker;
import tasktimer.api.TimerConnection;

/**
 * Created by drupality on 7/9/16.
 */
public class TimerConnectionSwingWorker extends SwingWorker {

    private TimerConnection timerConnection;

    public TimerConnection getTimerConnection() {
        return timerConnection;
    }

    public void setTimerConnection(TimerConnection timerConnection) {
        this.timerConnection = timerConnection;
    }


    @Override
    public Object construct() {
        return timerConnection.connect();
    }
}
