package floater;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.parser.Element;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;


public class PreLoad extends JFrame {

    JFrame frame;
    FBFloat widged;
    JFXPanel jfxPanel = null;
    public WebView webView = null;
    Scene scene = null;
    boolean entered = false;

    public PreLoad(FBFloat widged) {

        this.widged = widged;
        frame = this;

        //SETTING LOCATION ON SCREEN
        setLocation(widged.getLocation());//.x , widged.getLocation().y - 50);//not working

        //BUTTTONS
        JButton message = new JButton();
        JButton friend = new JButton();
        JButton notifications = new JButton();
        JButton about = new JButton();
        JButton web = new JButton();
        JButton tabs = new JButton();

        about.setBounds(0, 0, 31, 35);
        friend.setBounds(31, 0, 31, 35);
        message.setBounds(62, 0, 31, 35);
        notifications.setBounds(93, 0, 31, 35);
        web.setBounds(124, 0, 31, 35);
        tabs.setBounds(155, 0, 31, 35);

        about.setIcon(new ImageIcon("res/icons/about.png"));
        web.setIcon(new ImageIcon("res/icons/web.png"));
        tabs.setIcon(new ImageIcon("res/icons/tabs.png"));

        buttonTrans(about);
        buttonTrans(friend);
        buttonTrans(message);
        buttonTrans(notifications);
        buttonTrans(web);
        buttonTrans(tabs);

        about.setToolTipText("About Floater");
        friend.setToolTipText("Friend Requests");
        message.setToolTipText("Messages");
        notifications.setToolTipText("Notifications");
        web.setToolTipText("Facebook Web");
        tabs.setToolTipText("All Open Tabs");

        add(about);
        add(friend);
        add(message);
        add(notifications);
        add(web);
        add(tabs);
        try{
            setIconImage(ImageIO.read(new File("res/icons/fbicon.png")));
        }catch (IOException e){
            e.printStackTrace();
        }

        //BUTTON ACTIONS
        about.addActionListener(e -> {
            new About();
        });
        friend.addActionListener(e -> {
            widged.newWindow("https://m.facebook.com/friends/center/requests/",150 ,80);
        });
        message.addActionListener(e -> {
            widged.newWindow("https://m.facebook.com/messages/?refid=48", 100, 25);
        });
        notifications.addActionListener(e -> {
            widged.newWindow("https://m.facebook.com/notifications.php", 150, 80);
        });
        web.addActionListener(e -> {
            widged.newWindow("https://www.facebook.com/", 120, 185);
        });
        tabs.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        JPopupMenu popup = new JPopupMenu();
                        for (int i =0; i < ExpandSwing.wlist.size(); i++) {
                            int j = i;
                            popup.add(new JMenuItem(new AbstractAction(ExpandSwing.wlist.get(i).engine.getTitle()) {
                                public void actionPerformed(ActionEvent e) {
                                    //Add code of your own wish!
                                    ExpandSwing.wlist.get(j).setVisible(true);
                                    //JOptionPane.showMessageDialog(frame, "Option "+ j+" selected");
                                    //System.out.println("this is the number: " + j);
                                }
                            }));
                        }
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
        );

        //PANEL
        jfxPanel = new JFXPanel();
        jfxPanel.setSize(100, 200);
        jfxPanel.setEnabled(false); //MAKES A ROCK SOLID PANEL

        //FRAME
        setAlwaysOnTop(true);
        setSize(200, 35);
        setUndecorated(true);
        //setOpacity(.3f);
        add(jfxPanel);
        //setVisible(true);
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));


        //GO TO FACEBOOK
        Platform.runLater(() -> {
            webView = new WebView();
            webView.setZoom(0.8);
            jfxPanel.setScene(new Scene(webView));
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webView.getEngine().load("https://m.facebook.com/");
                //title = webView.getEngine().getTitle();
                //System.out.println(title);
            }
        });

    }

    void buttonTrans(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        //  MOUSE LISTENERS
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                entered = true;
                setOpacity(1.0f);
                widged.setOpacity(1.0f);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setOpacity(.3f);
                widged.setOpacity(.35f);
                //TIMER
                /*new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                setOpacity(.3f);
                                widged.setOpacity(.35f);
                            }
                        },
                        1000
                );*/
            }
        });
    }
    public void reload(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webView.getEngine().load("https://m.facebook.com/");
            }
        });
    }
}
