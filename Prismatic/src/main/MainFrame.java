package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    private GamePanel panel;
    private GraphicsDevice graphicsDevice;

    public MainFrame() {
    	
    	
    	
        panel = new GamePanel();
        panel.setBackground(Color.BLACK);
        this.setLayout(null); // Use no layout manager to manually set the size and position of the panel
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();

        if (graphicsDevice.isFullScreenSupported()) {
            // Set the JFrame to fullscreen mode
            setUndecorated(true);
            graphicsDevice.setFullScreenWindow(this);
        } else {
            // Fallback to maximized mode
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
            setVisible(true);
        }
        

        // Add the panel and set its size
        this.add(panel);
        resizePanel();

        // Add key listener
        addKeyListener(new KeyChecker(panel));

        // Add component listener to handle resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizePanel();
            }
        });
    }

    private void resizePanel() {
        if (graphicsDevice.getFullScreenWindow() == this || getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            // Get the current size of the JFrame
        	Dimension frameSize = this.getSize();
            panel.setSize(frameSize.width, frameSize.height);
            panel.setLocation(0, 0);
            
            

            // Optionally revalidate and repaint
            panel.revalidate();
            panel.repaint();
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
