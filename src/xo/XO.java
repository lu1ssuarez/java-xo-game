package xo;

import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Eysemberth Abarca
 */
public class XO extends JFrame implements Runnable {

    private Painter $painter;

    private ServerSocket $serverSocket;
    private Socket $socket;

    /* Input setting server */
    private String $ip = "127.0.0.1";
    private int $port = 3000;

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
        this.setTitle("XO Game by Eysemberth Abarca");
        this.setContentPane(this.$painter);
        this.setSize(506, 527);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        /* 3- input's settings server */
        this.setConfigServer();

        /* 4- check socket connection */
        if (this.connectSocket()) {

        }
    }

    @Override
    public void run() {

    }

    private void setConfigServer() {
        this.setIpServer();
        this.setPortServer();

        System.out.println("Conectando al servidor: " + this.$ip + ":" + this.$port);
    }

    private void setIpServer() {
        try {
            this.$ip = JOptionPane.showInputDialog("Ingrese la ip del servidor:", this.$ip);

            if (this.$ip.isEmpty()) {
                this.setIpServer();
            }
        } catch (Exception exception) {
            this.setIpServer();
        }
    }

    private void setPortServer() {
        try {
            this.$port = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el puerto a conectar:", this.$port));

            if (this.$port == 0) {
                this.setPortServer();
            }
        } catch (Exception exception) {
            this.setPortServer();
        }
    }

    /* Socket */
    private boolean connectSocket() {
        try {
            this.$socket = new Socket(this.$ip, this.$port);
        } catch (IOException $io) {

            return false;
        }

        return true;
    }
}
