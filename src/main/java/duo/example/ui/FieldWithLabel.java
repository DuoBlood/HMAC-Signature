package duo.example.ui;

import javax.swing.*;
import java.awt.*;

public class FieldWithLabel extends JComponent {

    private final JTextField field;

    public FieldWithLabel(final String labelText, final String fieldText) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        final Box box = Box.createVerticalBox();
        final JLabel label = new JLabel(labelText);
        label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        field = new JTextField(fieldText);
        field.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        setFieldHeight(field);
        box.add(label);
        box.add(Box.createVerticalStrut(5));
        box.add(field);
        add(box);
    }

    private void setFieldHeight(final JComponent field) {
        final Dimension d = field.getMaximumSize();
        d.setSize(d.getWidth(), field.getPreferredSize().getHeight());
        field.setMaximumSize(d);
    }

    public String getFieldText() {
        return field.getText();
    }
}
