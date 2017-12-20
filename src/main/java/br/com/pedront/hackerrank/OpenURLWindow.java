package br.com.pedront.hackerrank;

import java.util.function.Consumer;

import javax.swing.*;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 19/12/17 14:06
 */
public class OpenURLWindow {

    private static final String WINDOW_TITLE = "HackerHank: Problem URL";

    private static final String URL_LABEL = "URL";

    private static final String OPEN_BUTTON_CAPTION = "Open";

    private JFrame frame;

    private JTextField urlTextField;

    private JButton button;

    public OpenURLWindow(Consumer<String> urlConsumer) {
        initComponents();
        addListeners(urlConsumer);
        buildForm();
    }

    private void initComponents() {
        this.frame = new JFrame(WINDOW_TITLE);
        this.urlTextField = new JTextField();
        this.button = new JButton(OPEN_BUTTON_CAPTION);
    }

    private void addListeners(final Consumer<String> urlConsumer) {
        this.button.addActionListener(e -> {
            urlConsumer.accept(urlTextField.getText());

            frame.setVisible(false);
        });
    }

    public void buildForm() {
        FormLayout layout = new FormLayout(
                "3dlu, right:pref, 3dlu, pref, 60dlu, 3dlu", // columns
                "3dlu, pref, 6dlu, pref, 3dlu"); // rows

        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        builder.addLabel(URL_LABEL, cc.xy(2, 2));
        builder.add(urlTextField, cc.xyw(4, 2, 2));
        builder.add(button, cc.xy(4, 4));

        frame.add(builder.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }
}
