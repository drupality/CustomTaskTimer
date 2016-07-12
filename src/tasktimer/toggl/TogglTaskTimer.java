package tasktimer.toggl;

import ch.simas.jtoggl.JToggl;
import com.intellij.tasks.LocalTask;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import tasktimer.api.TaskTimer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by drupality on 5/29/16.
 */
public class TogglTaskTimer implements TaskTimer {

    private JSONObject response;

    private ArrayList<Project> projects;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = (JSONObject) response;
        this.projects = new ArrayList<Project>();
        JSONArray projects = (JSONArray) ((JSONObject) this.response.get("data")).get("projects");

        if (projects == null) {
            return;
        }

        for(Object item:projects) {
            Project project = new Project();
            JSONObject json = (JSONObject) item;
            project.setId((int) json.get("id"));
            project.setName((String) json.get("name"));
            this.projects.add(project);
        }
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


    public ArrayList<Project> getProjects() {
        return projects;
    }

    public String getProjectName() {
        return "";
    }
}
