package tasktimer.api;

import com.intellij.tasks.LocalTask;

/**
 * Created by drupality on 5/29/16.
 */
public interface TaskTimer {

    public void setTask(LocalTask task);

    public void start();

    public void stop();

    public boolean isConnected();

}
