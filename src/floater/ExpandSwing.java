package floater;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;


public class ExpandSwing extends JFrame {

    public static ArrayList<ExpandSwing> wlist = new ArrayList<>();
    public WebView webView = null;
    public WebEngine engine = null;
    JButton close, min, rld;
    JLabel label;
    JToggleButton top, opac;
    int windowWidth = 200;
    Font f = new Font("Candara",Font.BOLD,16);
    JPanel p;
    java.util.Timer animTimer = new java.util.Timer();
    JMenuBar mb;
    int pX,pY;
    int windowMaxLength=200;
    //FBFloat widged;
    JFXPanel jfxPanel = null;
    String url;
    double zoom = .9;
    JPanel controlPanel;
    //public static final String r = "\u267b"; //unicode for relode

    public ExpandSwing(boolean left, String url, int len, int wid) {

        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){}

        if (wid > 150) zoom = .75;
        if(wid < 70) zoom = .8;

        wlist.add(this);

        this.url = url;

     //%%%%%%%%%%%%%%%%%%%%%%%
        setUndecorated(true); //Borderless: uncomment when program finishes
     //%%%%%%%%%%%%%%%%%%%%%%%

        //BUTTON        //http://www.tutorialspoint.com/swing/swing_jbutton.htm

        close = new JButton("x");
        top = new JToggleButton("T");
        min = new JButton("-");
        //rld = new JButton(r);
        rld = new JButton("R");
        try {
            //close=new JButton(new ImageIcon(ImageIO.read(new File("res/icons/close.png"))));
            opac = new JToggleButton("O");
            label = new JLabel(new ImageIcon(ImageIO.read(new File("res/logo.png"))));
        }catch (IOException e){
            e.printStackTrace();
        }

        //label.setFont(f);
        min.setFont(f);
        rld.setFont(f);
        close.setFont(f);
        top.setFont(f);
        opac.setFont(f);

        //HOVER OPTIONS
        close.setToolTipText("Close");
        min.setToolTipText("Minimize");
        rld.setToolTipText("Reload");
        top.setToolTipText("Set On Top");
        opac.setToolTipText("Transparent");
        label.setToolTipText("A Mahmudul Hasan Masum Production");

        //close.setBounds (10, 5, 10, 24);

        rld.addActionListener(e -> {
            //webView.getEngine().reload();
            reload();
        });
        min.addActionListener(e -> {
            setVisible(false);
        });
        close.addActionListener(e -> {
            wlist.remove(this);
            dispose();

        });
        top.addActionListener(
            e -> {
                JToggleButton but = (JToggleButton)e.getSource();
                if (but.isSelected()){
                    setAlwaysOnTop(true);
                }
                else{
                    setAlwaysOnTop(false); // check the opacity button
                }
            }
        );
        opac.addActionListener(
            e -> {
                JToggleButton but = (JToggleButton)e.getSource();
                if (but.isSelected()){
                    setAlwaysOnTop(true);
                    setOpacity(.35f);
                }
                else{
                    setAlwaysOnTop(false); // check the top button
                    setOpacity(1.0f);
                }
            }
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
        p.add(min);
        p.add(rld);
        p.add(top);
        p.add(opac);
        p.add(label);

        // PANEL
        controlPanel = new JPanel(new GridBagLayout());//http://stackoverflow.com/questions/
        // 5555355/creating-a-text-field-that-dynamically-resizes-its-width
        GridBagConstraints c = new GridBagConstraints();
        c.weightx=1.0;
        c.fill=GridBagConstraints.BOTH;
        controlPanel.setBounds(0, 100, 500,500);

        //Menue Bar
        mb.add(p,BorderLayout.WEST);
        mb.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me)
            {
                pX=me.getX();
                pY=me.getY();
            }
            @Override
            public void mouseEntered(MouseEvent evt) {
                setOpacity(1.0f);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if(opac.isSelected()) {
                    setOpacity(.35f);
                }
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
            @Override
            public void mouseEntered(MouseEvent evt) {
                setOpacity(1.0f);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if(opac.isSelected()) {
                    setOpacity(.35f);
                }
            }
        });

        label.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me)
            {
                setLocation(getLocation().x+me.getX()-pX,getLocation().y+me.getY()-pY);
            }
        });

        //JFRAME
        setJMenuBar(mb);
        setSize(200, 67);
        setVisible(true);
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        try{
            setIconImage(ImageIO.read(new File("res/icons/fbicon.png")));
        }catch (IOException e){
            e.printStackTrace();
        }

        //SREEN SIZE
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        jfxPanel = new JFXPanel();
        jfxPanel.setSize(100, 200);
        add(jfxPanel);
        Platform.runLater(() -> {
            webView = new WebView();
            //webView.setZoom(0.8);
            jfxPanel.setScene(new Scene(webView));
        });

        //WINDOW POSITIONING ON SCREEN
        //getLocationOnScreen();
        //setLocation(500, 0);


        //GO TO FACEBOOK
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webView.setZoom(zoom);
                engine = webView.getEngine();
                engine.load(url);
                System.out.println("Title while loading: " + engine.getTitle());
            }
        });


        //MOUSE HOVER OPTION
        jfxPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
              setOpacity(1.0f);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
              if(opac.isSelected()) {
                  setOpacity(.35f);
              }
            }
            }
        );

        // Expand first
        TimerTask ttaskWidth = new TimerTask() {


            int i = 0;

            @Override
            public void run() {
                if (i < wid) {
                    setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
                    setSize(getWidth() + 3, getHeight());
                    windowWidth = getWidth();
                    //System.out.println(getWidth());
                    if (left)
                        setLocation(getLocationOnScreen().x - 3, getLocationOnScreen().y);
                } else {
                    this.cancel();
                }
                i++;
            }
        };
        animTimer.scheduleAtFixedRate(ttaskWidth, 500, 10);
        //System.out.println(getWidth());


       // if (windowWidth > 200 && getHeight() < windowMaxLength) {
            TimerTask ttaskWidth2 = new TimerTask() {

                int i = 0;

                @Override
                public void run() {
                    if (i < len) {
                        setSize(getWidth(), getHeight() + 3);
                        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
                        if (getLocationOnScreen().y + getHeight() > height - 31)
                            setLocation(getLocationOnScreen().x, getLocationOnScreen().y - 3);
                    } else {
                        this.cancel();
                    }
                    i++;
                }
            };
        animTimer.scheduleAtFixedRate(ttaskWidth2, 500, 10);
        windowMaxLength = getHeight();
    }

    public void reload(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.reload();
                //engine.load(engine.getLocation());
            }
        });
    }
}