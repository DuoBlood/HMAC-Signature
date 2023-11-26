package duo.example.ui;

import javax.swing.*;

public class DateComponent extends JComponent {

    public enum DateMode {
        AUTO, MANUAL, HEADER
    }

    private final FieldWithLabel headerKeyField;
    private final FieldWithLabel manualDateField;
    private final FieldWithLabel readHeaderField;

    private DateMode selectedDateMode;

    public DateComponent() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        final Box box = Box.createVerticalBox();
        final Box radioButtonsPanel = Box.createHorizontalBox();
        radioButtonsPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        final ButtonGroup group = new ButtonGroup();

        final JRadioButton auto = new JRadioButton("Auto");
        auto.addActionListener(e -> selectDateMode(DateMode.AUTO));
        group.add(auto);
        final JRadioButton manual = new JRadioButton("Manual");
        manual.addActionListener(e -> selectDateMode(DateMode.MANUAL));
        group.add(manual);
        final JRadioButton header = new JRadioButton("From header");
        header.addActionListener(e -> selectDateMode(DateMode.HEADER));
        group.add(header);
        radioButtonsPanel.add(auto);
        radioButtonsPanel.add(manual);
        radioButtonsPanel.add(header);

        box.add(new JLabel("Date type"));
        box.add(Box.createVerticalStrut(5));
        box.add(radioButtonsPanel);
        box.add(Box.createVerticalStrut(10));
        headerKeyField = new FieldWithLabel("Date header key", "x-date");
        manualDateField = new FieldWithLabel("Date", "");
        readHeaderField = new FieldWithLabel("Date header key (read from)", "x-date");
        box.add(manualDateField);
        box.add(headerKeyField);
        box.add(readHeaderField);

        manual.setSelected(true);
        selectDateMode(DateMode.MANUAL);

        add(box);
    }

    private void selectDateMode(DateMode mode) {
        if (selectedDateMode == mode) return;
        System.out.println(mode);
        selectedDateMode = mode;
        headerKeyField.setVisible(selectedDateMode != DateMode.HEADER);
        manualDateField.setVisible(selectedDateMode == DateMode.MANUAL);
        readHeaderField.setVisible(selectedDateMode == DateMode.HEADER);
    }

    public DateMode getDateMode() {
        return selectedDateMode;
    }

    public String getEnteredDateText() {
        return manualDateField.getFieldText();
    }

    public String getDateReadHeaderKey() {
        return readHeaderField.getFieldText();
    }

    public String getDateHeaderKeyText() {
        return headerKeyField.getFieldText();
    }

}
