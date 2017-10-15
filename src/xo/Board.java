package xo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Board extends javax.swing.JFrame {

    /* Socket Server */
    private ServerSocket $serverSocket;
    private ObjectOutputStream $dataOutputStreamServer;
    private ObjectInputStream $dataInputStreamServer;

    /* Socket Player */
    private Socket $socketPlayer;
    private ObjectOutputStream $dataOutputStreamPlayer;
    private ObjectInputStream $dataInputStreamPlayer;

    /* Input setting server */
    private String $ip = "";
    private int $port = 3000;
    private String $ipPort;

    private String $name;
    private String $namePlayer;

    /**
     * Creates new form Board
     */
    public Board() {
        setNameUser();
        setConfig();

        initComponents();

        setTitle("Hola " + $name + ", Bienvenido a XO Game");
        setSize(506, 527);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setNameUser() {
        try {
            $name = JOptionPane.showInputDialog("Ingrese su nombre de usuario:", $name);

            if ($name.isEmpty()) {
                setNameUser();
            }
        } catch (Exception exception) {
            setNameUser();
        }
    }

    private void setConfig() {
        Object[] $typeServerButtons = {"Conectar", "Crear servidor", "Cerrar"};
        int $typeServer = JOptionPane.showOptionDialog(null, "Deseas conectarte a un servidor o crear uno nuevo.", "Hola " + $name, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, $typeServerButtons, "Cerrar");

        switch ($typeServer) {
            case JOptionPane.CANCEL_OPTION:
                System.exit(0);
                break;
            case JOptionPane.YES_OPTION:
                $ip = "";
                setConfigServer();
                connectSocket();

                break;
            case JOptionPane.NO_OPTION:
                $ip = getIpLocal();
                setConfigServer();
                initServerSocket();

                break;
            default:
                System.exit(0);
                break;
        }
    }

    private String getIpLocal() {
        String $ip;

        try {
            InetAddress $address = InetAddress.getLocalHost();
            $ip = $address.getHostAddress();
        } catch (UnknownHostException $exception) {
            $ip = "127.0.0.1";
        }

        return $ip;
    }

    private void setConfigServer() {
        setIpServer();
        setPortServer();

        $ipPort = $ip + ":" + $port;
    }

    private void setIpServer() {
        $ip = "";

        if ($ip.isEmpty() || ($ip.equals("127.0.0.1") || $ip.equals("127.0.1.1"))) {
            try {
                $ip = JOptionPane.showInputDialog("Ingrese la ip del servidor:", $ip);

                if ($ip.isEmpty() || ($ip.equals("127.0.0.1") || $ip.equals("127.0.1.1"))) {
                    setIpServer();
                }
            } catch (NullPointerException $exception) {
                setIpServer();
            }
        }
    }

    private void setPortServer() {
        try {
            $port = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el puerto a conectar:", $port));

            if ($port == 0) {
                setPortServer();
            }
        } catch (NumberFormatException $exception) {
            setPortServer();
        }
    }

    /* Socket Player */
    private boolean connectSocket() {
        try {
            $socketPlayer = new Socket($ip, $port);

            $dataOutputStreamPlayer = new ObjectOutputStream($socketPlayer.getOutputStream());
            $dataOutputStreamPlayer.writeObject($name);

            JOptionPane.showMessageDialog(null, "Espere mientras se acepta unirse al juego");

            $dataInputStreamServer = new ObjectInputStream($socketPlayer.getInputStream());
            String $accepted = (String) $dataInputStreamServer.readObject();

            if ($accepted.equalsIgnoreCase("NO")) {
                $socketPlayer.close();
                JOptionPane.showMessageDialog(null, "Se ha denegado tu solicitud de unirte al juego", null, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } catch (IOException $io) {
            JOptionPane.showMessageDialog(null, "No se puede conectar a la dirección: " + $ipPort, null, JOptionPane.ERROR_MESSAGE);
            setConfig();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(XO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    /* Socket Server */
    private void initServerSocket() {
        try {
            $serverSocket = new ServerSocket($port, 8, InetAddress.getByName($ip));
            JOptionPane.showMessageDialog(null, "Se ha creado el servidor [" + $ipPort + "] correctamente \n"
                    + "Esperando conexion...");

            Socket $socketPlayer = $serverSocket.accept();

            $dataInputStreamPlayer = new ObjectInputStream($socketPlayer.getInputStream());
            $namePlayer = (String) $dataInputStreamPlayer.readObject();

            Object[] $optionButtons = {"Si", "No"};
            int $optionConnect = JOptionPane.showOptionDialog(null, $namePlayer + " desea jugar contigo, estas de acuerdo?", "Conexion entrante", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, $optionButtons, "No");

            switch ($optionConnect) {
                case JOptionPane.YES_OPTION:
                    $dataOutputStreamServer = new ObjectOutputStream($socketPlayer.getOutputStream());
                    $dataOutputStreamServer.writeObject("YES");
                    break;
                case JOptionPane.NO_OPTION:
                    $dataOutputStreamServer = new ObjectOutputStream($socketPlayer.getOutputStream());
                    $dataOutputStreamServer.writeObject("NO");
                    $socketPlayer.close();
                    break;
                default:
                    $dataOutputStreamServer = new ObjectOutputStream($socketPlayer.getOutputStream());
                    $dataOutputStreamServer.writeObject("NO");
                    $socketPlayer.close();
                    break;
            }
        } catch (IOException $exception) {
            JOptionPane.showMessageDialog(null, "No se puede crear el servidor", null, JOptionPane.ERROR_MESSAGE);
            setConfig();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(XO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        $icon9 = new javax.swing.JLabel();
        $icon8 = new javax.swing.JLabel();
        $icon7 = new javax.swing.JLabel();
        $icon4 = new javax.swing.JLabel();
        $icon5 = new javax.swing.JLabel();
        $icon6 = new javax.swing.JLabel();
        $icon3 = new javax.swing.JLabel();
        $icon1 = new javax.swing.JLabel();
        $icon2 = new javax.swing.JLabel();
        $bgBoard = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        $icon9.setText("icon9");

        $icon8.setText("icon8");

        $icon7.setText("icon7");

        $icon4.setText("icon4");

        $icon5.setText("icon5");

        $icon6.setText("icon6");

        $icon3.setText("icon3");

        $icon1.setText("icon1");

        $icon2.setText("icon2");

        $bgBoard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/xo/image/board.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent($icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent($icon2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent($icon3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent($icon7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent($icon4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent($bgBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent($icon5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent($icon6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent($icon8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent($icon9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 118, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent($icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent($icon2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent($icon3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent($icon5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent($icon6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent($icon8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent($icon9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addComponent($icon7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent($icon4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent($bgBoard)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Board().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel $bgBoard;
    private javax.swing.JLabel $icon1;
    private javax.swing.JLabel $icon2;
    private javax.swing.JLabel $icon3;
    private javax.swing.JLabel $icon4;
    private javax.swing.JLabel $icon5;
    private javax.swing.JLabel $icon6;
    private javax.swing.JLabel $icon7;
    private javax.swing.JLabel $icon8;
    private javax.swing.JLabel $icon9;
    // End of variables declaration//GEN-END:variables
}
