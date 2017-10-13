package xo;

import javax.swing.JFrame;

/**
 *
 * @author Eysemberth Abarca
 */
public class XO implements Runnable {

    private JFrame $body;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XO $XO = new XO();
    }
    
    public XO() {
        System.out.println("Iniciando XO...");
        
        /* 1- init $body with jFrame */
        $body = new JFrame();
        $body.setTitle("XO Game by Eysemberth Abarca");
        $body.setSize(506, 527);
        $body.setLocationRelativeTo(null);
        $body.setResizable(false);
        $body.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        $body.setVisible(true);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("run");
        }
    }
    
}
