package floater;

import com.sun.awt.AWTUtilities;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class FBFloat extends JFrame{

    int posX = 0, posY = 0;
    JButton closeBtn;
    PreLoad node = null;
    FBFloat widged;
    int width;
    int height;

    //public static ArrayList<ExpandSwing> window = new ArrayList<>();
    public FBFloat()
    {
        setLocationRelativeTo(null); //comment it later
        widged = this;
        node = new PreLoad(widged);
        //SREEN SIZE
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth();
        height = (int)screenSize.getHeight();

        // CLOSE BUTTON
        closeBtn = new JButton();
        closeBtn.addActionListener(e -> System.exit(0));
        closeBtn.setBounds(87, 61, 18,18);
        closeBtn.setOpaque(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setToolTipText("Close Floater :'(");

        //  MOUSE LISTENERS
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setOpacity(1.0f);
            }

            @Override
            public void mouseExited(MouseEvent evt) {

                setOpacity(.3f);
            }
        });

        //THE MAIN FRAME
        setUndecorated(true);
        setAlwaysOnTop(true);
        setOpacity(.35f);
        setSize(200, 200); // https://www.youtube.com/watch?v=IFIlr6cpX64

        //LOAD THE PAGE EVEN BEFORE YOU LOG IN
        //node = new PreLoad(); //Create this when the other windows are minimized

        try {
            closeBtn.setIcon(new ImageIcon(ImageIO.read(new File("res/icons/cross.png"))));
            BufferedImage img = ImageIO.read(new File("res/fb1.png"));
            BufferedImage img2 = ImageIO.read(new File("res/fb2.png"));
            BufferedImage img3 = ImageIO.read(new File("res/icons/fbicon.png"));
            ImageIcon icon = new ImageIcon(img);
            ImageIcon icon2 = new ImageIcon(img2);
            JLabel label = new JLabel(icon);

            //setContentPane(new JLabel(icon));

            //====================
            // Mouse hover Option
            //====================

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    node.reload();
                    setOpacity(1.0f);
                    node.setOpacity(1.0f); // <===
                }
                @Override
                public void mouseExited(MouseEvent evt) {
                    setOpacity(.35f);
                    node.setOpacity(.35f);
                }
                @Override
                public void mousePressed(MouseEvent e)
                {
                    posX=e.getX();
                    posY=e.getY();
                    label.setIcon(icon2);
                    System.out.println("mouse pressed");
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    label.setIcon(icon);
                }

                @Override
                public void mouseClicked(MouseEvent e){
                    if(e.getClickCount()==2){
                        // your code here
                        newWindow("https://m.facebook.com/",150, 80);
                        if (!node.isVisible()) node.setVisible(true);
                    }
                    else if (e.getClickCount()==1){
                        if (node.isVisible()) node.setVisible(false);
                        else {
                            node.setVisible(true);
                            setOpacity(1.0f);
                        }
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent evt) {
                    setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
                    node.setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY); // locating preload
                }
            });

            //=====================
            setContentPane(label);
            add(closeBtn);
            setIconImage(img3);

        }catch (IOException e){
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //addKeyListener(keyListener)

        Shape shape = new Ellipse2D.Float(61, 61, 79, 79);
        AWTUtilities.setWindowShape(this, shape);
    }

    public static void main(String[] args) {
        new FBFloat();
    }

    public void newWindow(String url, int len, int wid){
        int scrX = getLocationOnScreen().x; //getX();//e.getXOnScreen();
        int scrY = getLocationOnScreen().y; //getY();//e.getYOnScreen();
        boolean left = scrX> width - 586;

        System.out.println("The mouse is clicked.");
        ExpandSwing expandSwing = new ExpandSwing(left, url, len, wid);
        //window.add(expandSwing);
        expandSwing.setLocation(
                left ? scrX - 160: scrX+160, // ? here
                scrY+65
        );
    }
}
