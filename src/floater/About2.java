package floater;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;


public class About2 extends JFrame {

    JScrollPane pane;
    JButton close;
    JLabel label;
    JTextArea textArea;

    int windowWidth = 50;
    Font f = new Font("Candara",Font.BOLD,16);
    JPanel p;
    java.util.Timer animTimer = new java.util.Timer();
    JMenuBar mb;
    int windowMaxLength=200;
    JPanel controlPanel;

    public About2 () {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){}

        //SCREEN SIZE
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        //SCROLL PANE
        pane = new JScrollPane(textArea);
        //pane.setBackground(new Color(255, 0, 0));

        setUndecorated(true);
        //ONLY ONE BUTTON
        close = new JButton("x");
        close.setFont(f);
        close.addActionListener(e -> {
            System.exit(0);// disable later
            dispose();
        });

        //LOAD SOME IMAGES
        try {
            //close=new JButton(new ImageIcon(ImageIO.read(new File("res/icons/close.png"))));
            label = new JLabel(new ImageIcon(ImageIO.read(new File("res/logo.png"))));
        }catch (IOException e){
            e.printStackTrace();
        }


        //HOVER OPTIONS
        close.setToolTipText("Close");
        label.setToolTipText("A Mahmudul Hasan Masum Production");

        //ADDING TEXT TO TEXTLABEL
        textArea = new JTextArea(
                "Floater_fb 1.02\n" +
                        "Completed: December 27, 2015\n" +
                        "Powered by Java WebEngine\n" +
                        "(c) Being copyrigted under Bangladesh Copyright Office; All rights \n" +
                        "reserved by Md Mahmudul Hasan\n"
        );

        // creating menu bar
        mb=new JMenuBar();
        mb.setLayout(new BorderLayout());
        mb.setFont(f);

        // Title Bar
        p=new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridBagLayout());//FlowLayout()); //GridLayout(1, 2));
        p.add(close);
        p.add(label);

        // PANEL
        controlPanel = new JPanel(new GridBagLayout());//http://stackoverflow.com/questions/
        // 5555355/creating-a-text-field-that-dynamically-resizes-its-width
        GridBagConstraints c = new GridBagConstraints();
        c.weightx=1.0;
        c.fill=GridBagConstraints.BOTH;
        controlPanel.setBounds(0, 100, 500,500);
        //.setBackground(new Color(59, 89, 152));

        //Menue Bar
        mb.add(p,BorderLayout.WEST);

        //JFRAME
        setJMenuBar(mb);
        setSize(200, 67);
        setVisible(true);
        setAlwaysOnTop(true);
        setOpacity(.9f);
        setLayout(new BorderLayout());
        //setLocation((width - 557)/2 , (height - 370)/2);
        setLocation(width/2-50, height/2);
        add(controlPanel, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        try{
            setIconImage(ImageIO.read(new File("res/icons/fbicon.png")));
        }catch (IOException e){
            e.printStackTrace();
        }

        // Expand first
        TimerTask ttaskWidth = new TimerTask() {

            int i = 0;

            @Override
            public void run() {
                if (i < 120) {
                    setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
                    setSize(getWidth() + 3, getHeight());
                    setLocation((int)(getLocationOnScreen().getX() - 1.5), getLocationOnScreen().y);
                    windowWidth = getWidth();
                    i++;
                    //System.out.println(getWidth());
                    if (false)
                        System.out.println();
                    } else {
                        this.cancel();
                    }
                }
            };
        animTimer.scheduleAtFixedRate(ttaskWidth, 500, 10);

            TimerTask ttaskWidth2 = new TimerTask() {

                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {
                        setSize(getWidth(), getHeight() + 3);
                        setLocation(getLocationOnScreen().x, (int)(getLocationOnScreen().getY() - 1.5));
                        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
                        if (getLocationOnScreen().y + getHeight() > height - 31)
                            System.out.println();
                    } else {
                        this.cancel();
                    }
                    i++;
                }
            };
        animTimer.scheduleAtFixedRate(ttaskWidth2, 500, 10);
        windowMaxLength = getHeight();
    }

    public static void main(String[] args) {
        new About();
    }

}