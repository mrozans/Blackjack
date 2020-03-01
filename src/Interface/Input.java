package Interface;

import javax.swing.*;

public class Input
{
    private static JTextField TextField;

    public Input(JFrame frame)
    {
        TextField = addtextfield(500, 370, 100, 20, frame);
        TextField.setVisible(false);
    }

    private JTextField addtextfield(int x, int y, int width, int height, JFrame frame)
    {
        JTextField tf = new JTextField();
        tf.setBounds(x,y,width, height);
        frame.add(tf);
        return tf;
    }

    public static int getnumber()
    {
        String text = TextField.getText();
        try
        {
            return Integer.parseInt(text);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }

    }

    public static String getname()
    {
        return TextField.getText();
    }

    public static void setTextField()
    {
        TextField.setVisible(true);
    }

    public static void hideTextField()
    {
        TextField.setVisible(false);
    }
}
