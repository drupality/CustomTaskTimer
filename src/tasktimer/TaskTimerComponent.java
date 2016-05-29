package tasktimer;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.tasks.LocalTask;
import com.intellij.tasks.TaskListener;
import org.jetbrains.annotations.NotNull;

/**
 * Created by drupality on 5/17/16.
 */
public class TaskTimerComponent implements ProjectComponent, TaskListener {
    @Override
    public void projectOpened() {

    }

    @Override
    public void projectClosed() {

    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "Task Timer";
    }

    @Override
    public void taskDeactivated(LocalTask localTask) {

    }

    @Override
    public void taskActivated(LocalTask localTask) {

    }

    @Override
    public void taskAdded(LocalTask localTask) {

    }

    @Override
    public void taskRemoved(LocalTask localTask) {

    }
}
