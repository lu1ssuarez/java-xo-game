package xo;

import java.awt.Dimension;
import java.net.ServerSocket;
import javax.swing.JFrame;

/**
 *
 * @author Eysemberth Abarca
 */
public class XO implements Runnable {

    private JFrame $body;
    private Painter $painter;
    
    private ServerSocket $serverSocket;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XO $XO = new XO();
    }
    
    public XO() {
        System.out.println("Iniciando XO...");
        
        /* 2- draw jPanel for event listener */
        $painter = new Painter();
        $painter.setPreferredSize(new Dimension(506, 527));
        
        /* 1- init $body with jFrame */
        $body = new JFrame();
        $body.setTitle("XO Game by Eysemberth Abarca");
        $body.setContentPane($painter);
        $body.setSize(506, 527);
        $body.setLocationRelativeTo(null);
        $body.setResizable(false);
        $body.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        $body.setVisible(true);
    }

    @Override
    public void run() {
        
    }
    
}
