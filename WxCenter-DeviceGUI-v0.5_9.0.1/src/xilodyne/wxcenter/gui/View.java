package xilodyne.wxcenter.gui;

import javax.swing.JFrame;
import javax.swing.JRootPane;

//import eu.hansolo.steelseries.gauges.Radial2Top;
//import eu.hansolo.steelseries.gauges.RadialBargraph;

public class View {

	public JFrame frame;
	public JPanelMainPage mainPanel;

	// run from the application
	public View(final Model model) {
		mainPanel = new JPanelMainPage(model);

		frame = new JFrame(model.title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1024, 768);
		frame.setContentPane(mainPanel);
	}

	// run from the applet
	// public View(final Model model, JRootPane contentPane) {
	// mainPanel = new JPanelMainPage(model);
	// contentPane.setContentPane(mainPanel);
	// }

	// run from the applet
	public View(final Model model, JRootPane contentPane) {
		mainPanel = new JPanelMainPage(model);
		JFrame frame = new JFrame("FrameDemo");
		frame = new JFrame(model.title);
		frame.setBounds(100, 100, 1024, 768);
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
/*	public ViewXX(final Model model, JRootPane contentPane) {
		//mainPanel = new JPanelMainPage(model);
		DataDisplayPanel displayPanel = new DataDisplayPanel("test");
        JFrame f = new JFrame("LayoutTest");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add( displayPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        for (int i =0; i<1000000000; i++) {}
*/
   
	
	

}
