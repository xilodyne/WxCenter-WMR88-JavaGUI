package xilodyne.wxcenter.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

public class DataDisplayPanel extends JPanel{

	private static final long serialVersionUID = 5806892936204839579L;
	private static final int SIZE = 256;
	    private BufferedImage image;
	    
	    private JTextField textOutdoorData;
	 //   private JTextField outdoorTempDecimal;
	 //   private JTextField textField;

	    public DataDisplayPanel(String string) {
	    	setLayout(new BorderLayout(0, 0));
	    	
	   // 	textField = new JTextField();
	   // 	add(textField, BorderLayout.CENTER);
	    //	textField.setColumns(10);
	       // super(string);
	      //  image = createImage(super.getText());
	    	image = createImage(string);
	    }

	   
	    public void setText(String text) {
	//		textOutdoorData = new JLabel("\u25B2");
	    	textOutdoorData = new JTextField("\u25B2");
			textOutdoorData.setForeground(Color.GRAY);
			textOutdoorData.setFont(new Font("Arial", Font.PLAIN, 50));
		//	lblOutdoorData.setBounds(52, 204, 59, 50);
			//add(lblOutdoorData);
			add(textOutdoorData,BorderLayout.CENTER );
	      //  super.setText(text);
	      //  image = createImage(super.getText());
	    	image = createImage(text);
	    	
	//		outdoorTempDecimal = new JTextField("\u00B7");
	//		outdoorTempDecimal.setForeground(Color.WHITE);
	//		outdoorTempDecimal.setFont(new Font("Tahoma", Font.PLAIN, 60));
	//		outdoorTempDecimal.setBounds(251, 116, 62, 97);
	//		add(outdoorTempDecimal, BorderLayout.EAST);
	//		image = createImage()
	        repaint();
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(image.getWidth() / 2, image.getHeight() / 2);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	    }

	    private BufferedImage createImage(String label) {
	        Font font = new Font(Font.SERIF, Font.PLAIN, SIZE);
	        FontRenderContext frc = new FontRenderContext(null, true, true);
	        TextLayout layout = new TextLayout(label, font, frc);
	        Rectangle r = layout.getPixelBounds(null, 0, 0);
	        System.out.println(r);
	        BufferedImage bi = new BufferedImage(
	            r.width + 1, r.height + 1, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = (Graphics2D) bi.getGraphics();
	        g2d.setRenderingHint(
	            RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setColor(getBackground());
	        g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
	        g2d.setColor(getForeground());
	        layout.draw(g2d, 0, -r.y);
	        g2d.dispose();
	        return bi;
	    }

	/*    public static void display() {
	        JFrame f = new JFrame("LayoutTest");
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.add(new DataDisplayPanel("Sample"));
	        f.pack();
	        f.setLocationRelativeTo(null);
	        f.setVisible(true);
	    }
	    */

}
