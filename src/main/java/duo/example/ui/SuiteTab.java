package duo.example.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SuiteTab extends JPanel {

    private final DateComponent dateComponent;
    private final FieldWithLabel keyField;
    private final FieldWithLabel headerField;

    public SuiteTab() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        dateComponent = new DateComponent();
        keyField = new FieldWithLabel("Secret key", "");
        headerField = new FieldWithLabel("Header key", "x-signature");

        add(dateComponent);
        add(Box.createVerticalStrut(15));
        add(keyField);
        add(Box.createVerticalStrut(15));
        add(headerField);
    }

    public DateComponent.DateMode getDateMode() {
        return dateComponent.getDateMode();
    }

    public String getManualDate() {
        return dateComponent.getEnteredDateText();
    }

    public String getReadDateHeaderKey() {
        return dateComponent.getDateReadHeaderKey();
    }

    public String getDateHeaderKey() {
        return dateComponent.getDateHeaderKeyText();
    }

    public String getSecretKey() {
        final String text = keyField.getFieldText();
        return text == null ? "" : text;
    }

    public String getSignatureHeaderKey() {
        final String text = headerField.getFieldText();
        return text == null ? "" : text;
    }
}
