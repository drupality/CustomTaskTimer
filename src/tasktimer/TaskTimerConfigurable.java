package tasktimer;

import ch.simas.jtoggl.JToggl;
import ch.simas.jtoggl.User;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.popup.BalloonPopupBuilderImpl;
import com.intellij.util.Base64;
import com.intellij.util.concurrency.*;
import com.intellij.util.concurrency.SwingWorker;
import com.intellij.util.io.HttpRequests;
import com.intellij.util.io.RequestBuilder;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.http.protocol.HTTP;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tasktimer.api.TimerConnection;
import tasktimer.toggl.TogglTaskTimer;
import tasktimer.toggl.TogglTimerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLConnection;

/**
 * Created by drupality on 5/17/16.
 */
public class TaskTimerConfigurable implements Configurable {

    private JPanel togglSection;
    private JTextField token;

    @Nls
    @Override
    public String getDisplayName() {
        return "Task Timer Settings";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "Please specify time tracking providers if available";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        JPanel form = new JPanel(new VerticalLayout(10));
        JPanel statusSection = new JPanel(new VerticalLayout(10));
        JLabel statusText = new JLabel("");
        statusSection.add(statusText);
        togglSection = new JPanel(new HorizontalLayout(5));
        JLabel label = new JLabel("Toggl API Token");
        JButton connectToggl = new JButton("Fetch projects");
        connectToggl.setEnabled(false);
        token = new JTextField();
        token.setColumns(20);
        token.setText("de93f242dc2445d8c0ac9187167e0b76");
        label.setLabelFor(token);

        token.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if (((JTextField) e.getComponent()).getText().isEmpty()) {
                    connectToggl.setEnabled(false);
                } else {
                    connectToggl.setEnabled(true);
                }
            }
        });

        connectToggl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String apiToken = token.getText();
                if (apiToken.isEmpty()) {
                    statusText.setForeground(Color.RED);
                    statusText.setText("Please provide correct Toggl API token");
                    return;
                }

                TogglTimerConnection togglConnection = new TogglTimerConnection();
                togglConnection.setToken(apiToken);

                TimerConnectionSwingWorker checkTokenWorker = new TimerConnectionSwingWorker();
                checkTokenWorker.setTimerConnection(togglConnection);
                checkTokenWorker.start();

                if (checkTokenWorker.get() == null) {
                    System.out.println(((TogglTimerConnection) checkTokenWorker.getTimerConnection()).getException().getMessage());
                    connectToggl.setEnabled(false);
                    token.requestFocus();
                }

                TogglTaskTimer togglTimer = (TogglTaskTimer) checkTokenWorker.get();

                if (togglTimer == null) {
                    statusText.setForeground(Color.RED);
                    statusText.setText("Connection failed. Please provide correct Toggl API token");
                } else {
                    statusText.setForeground(Color.GREEN);
                    statusText.setText("Connection successful");
                }
            }
        });

        togglSection.add(label);
        togglSection.add(token);
        togglSection.add(connectToggl);
        form.add(statusSection);
        form.add(togglSection);

        return form;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }
}
