package xo;

import java.awt.Dimension;
import java.net.ServerSocket;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Eysemberth Abarca
 */
public class XO implements Runnable {

    private JFrame $body;
    private Painter $painter;
    
    private ServerSocket $serverSocket;
    
    /* Input setting server */
    private JLabel $jlIP, $jlPort;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XO $XO = new XO();
    }
    
    public XO() {
        System.out.println("Iniciando XO...");
        
        /* 2- draw jPanel for event listener */
        this.$painter = new Painter();
        this.$painter.setPreferredSize(new Dimension(506, 527));
        
        /* 1- init $body with jFrame */
        this.$body = new JFrame();
        this.$body.setTitle("XO Game by Eysemberth Abarca");
        this.$body.setContentPane(this.$painter);
        this.$body.setSize(506, 527);
        this.$body.setLocationRelativeTo(null);
        this.$body.setResizable(false);
        this.$body.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.$body.setVisible(true);
        
        /* 3- draw input's settings server */
        this.drawSettingServer();
    }

    @Override
    public void run() {
        
    }
    
    private void drawSettingServer() {
        this.$jlIP = new JLabel("IP:");
        this.$jlIP.setBounds(10, 10, 10 ,10);
        this.$body.add(this.$jlIP);
    } 
    
}
