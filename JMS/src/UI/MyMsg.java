package UI;

import java.io.Serializable;

public class MyMsg implements Serializable {
    private static final long serialVersionUID = 1L; // You can generate a unique value for your application.

    private String text;

    public MyMsg() {
        // Default constructor
    }

    public MyMsg(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}






