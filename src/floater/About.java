package floater;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class About extends JFrame {

    JButton close;
    JLabel label;
    Font f = new Font("Candara",Font.BOLD,16);
    JPanel p;
    JPanel controlPanel;
    JMenuBar mb;
    int w = 565, h = 520;
    JFXPanel fxpanel;
    int pX,pY;

    public About () {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }

        setUndecorated(true);

        //ONLY ONE BUTTON
        close = new JButton("x");
        close.setFont(f);
        close.addActionListener(e -> {
            dispose();
        });

        //LOAD SOME IMAGES
        try {
            label = new JLabel(new ImageIcon(ImageIO.read(new File("res/logo.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ADDING FXML FILE
        fxpanel = new JFXPanel();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
            Scene scene = new Scene(root, w, h);
            fxpanel.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }

        //SCREEN SIZE
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //HOVER OPTIONS
        close.setToolTipText("Close");
        label.setToolTipText("A Mahmudul Hasan Masum Production");

        // creating menu bar
        mb = new JMenuBar();
        mb.setLayout(new BorderLayout());
        mb.setFont(f);

        // Title Bar
        p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridBagLayout());
        p.add(close);
        p.add(label);

        // PANEL
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;

        //Menue Bar
        mb.add(p, BorderLayout.WEST);

        //JFRAME
        setJMenuBar(mb);
        setSize(w, h);
        add(fxpanel);
        setVisible(true);
        setAlwaysOnTop(true);
        //setOpacity(.9f);
        setLayout(new BorderLayout());
        setLocation((width - w)/2 , (height - h)/2);
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

        //MOUSE OPTIONS
        mb.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me)
            {
                pX=me.getX();
                pY=me.getY();
            }
        });

        mb.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me)
            {
                setLocation(getLocation().x+me.getX()-pX,getLocation().y+me.getY()-pY);
            }
        });

        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me)
            {
                pX=me.getX();
                pY=me.getY();
            }
        });

        label.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me)
            {
                setLocation(getLocation().x+me.getX()-pX,getLocation().y+me.getY()-pY);
            }
        });
        fxpanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me)
            {
                pX=me.getX();
                pY=me.getY();
            }
        });

        fxpanel.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me)
            {
                setLocation(getLocation().x+me.getX()-pX,getLocation().y+me.getY()-pY);
            }
        });

        try {
            setIconImage(ImageIO.read(new File("res/icons/fbicon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}