package it.unibo.oop.reactivegui03;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;
/**
 * Second example of reactive GUI.
 */
public final class AnotherConcurrentGUI extends JFrame {
    final JPanel panel = new JPanel();
    final JLabel l = new JLabel();
    final JButton up = new JButton();
    final JButton down = new JButton();
    final JButton stop = new JButton();

    public AnotherConcurrentGUI(){
        super();
        final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int)d.getWidth()/3, (int)d.getHeight()/3);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        up.setText("up");
        down.setText("down");
        stop.setText("stop");
        
        panel.add(l);
        panel.add(up, BorderLayout.NORTH);
        panel.add(down, BorderLayout.NORTH);
        panel.add(stop, BorderLayout.NORTH);

        this.setContentPane(panel);
        
        l.setText("0");
        final Agent agent = new Agent();
        new Thread(agent).start();

        up.addActionListener( (e) -> agent.upCounting());
        stop.addActionListener( (e) -> agent.stopCounting() );
        down.addActionListener( (e) -> agent.decreaseCounting());

        new Thread(() -> {
            try {
                Thread.sleep(10000);
                agent.stopCounting();
            } catch ( InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private class Agent implements Runnable{
        private volatile boolean stop = false;
        private boolean increase = true;
        private int counter = 0;
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(!this.stop){
                try{
                    SwingUtilities.invokeAndWait(() -> l.setText(Integer.toString( counter)));
                    
                    if(this.increase) 
                        this.counter++;
                    else 
                        this.counter--;
                    Thread.sleep(100);
                } catch( Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex);
                }
            }
        }
        public void stopCounting() {
            this.stop = true;
            AnotherConcurrentGUI.this.stop.setEnabled(false);
            down.setEnabled(false);
            up.setEnabled(false);
        }
        public void upCounting() {
            this.increase = true;
        }
        public void decreaseCounting() {
            this.increase = false;
        }
    }
}
