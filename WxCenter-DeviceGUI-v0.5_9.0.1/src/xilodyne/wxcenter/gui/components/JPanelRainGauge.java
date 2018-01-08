package xilodyne.wxcenter.gui.components;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.GridLayout;

public class JPanelRainGauge extends JPanel {

	private static final long serialVersionUID = 1L;
	JLabel[] labels;
	public JPanelRainGauge() {
		setLayout(new GridLayout(0, 1, 0, 0));
		
		labels = new JLabel[7];
	       Border border = BorderFactory.createLineBorder(Color.BLACK, 2);  

			setBackground(Color.DARK_GRAY);


		
		for (int i=0; i<7 ; i++ ) {
			labels[i] = new JLabel();
			labels[i].setBackground(Color.BLACK);
			labels[i].setOpaque(true);
			labels[i].setBorder(border);
			this.add(labels[i]);
		}
		
	
		
		
	}
	
	public void setRainMM(float value) {
		for (int i=6; i>value ; i-- ) {
			labels[i].setBackground(Color.BLUE);
		}
	}


}
