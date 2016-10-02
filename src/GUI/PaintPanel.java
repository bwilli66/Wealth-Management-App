/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author BradWilliams1
 */
public class PaintPanel extends JComponent {
    
    private int width, height;
    private double percent;
    private Color bg, fill;
    
    public PaintPanel(Color b, Color f, int w, int h){
        percent = 0;
        this.width = w;
        this.height = h;
        this.bg = b;
        this.fill = f;
    }
    
    public void setPercent(double p){
        this.percent = p;
        this.repaint();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(bg);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(fill);
        g2d.fillRect(0, 0, (int)(width*percent), height);
        
    }
}
