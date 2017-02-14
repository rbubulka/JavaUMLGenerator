import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField white;
	private JTextField black;
	private JTextField pattern;
	private JTextField JarLocation;
	private JTextField Output;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 633, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JCheckBox c1 = new JCheckBox("PublicClass");
		c1.setBounds(8, 22, 113, 25);
		frame.getContentPane().add(c1);

		JCheckBox c2 = new JCheckBox("ProtectedClass");
		c2.setBounds(125, 22, 113, 25);
		frame.getContentPane().add(c2);

		JCheckBox c9 = new JCheckBox("Method Return Dependency");
		c9.setBounds(8, 165, 190, 25);
		frame.getContentPane().add(c9);

		JCheckBox c3 = new JCheckBox("PublicField");
		c3.setBounds(8, 69, 113, 25);
		frame.getContentPane().add(c3);

		JCheckBox c4 = new JCheckBox("ProtectedField");
		c4.setBounds(125, 69, 113, 25);
		frame.getContentPane().add(c4);

		JCheckBox c5 = new JCheckBox("privateField");
		c5.setBounds(264, 69, 113, 25);
		frame.getContentPane().add(c5);

		JCheckBox c6 = new JCheckBox("PublicMethod");
		c6.setBounds(8, 125, 113, 25);
		frame.getContentPane().add(c6);

		JCheckBox c7 = new JCheckBox("ProtectedMedthod");
		c7.setBounds(125, 125, 131, 25);
		frame.getContentPane().add(c7);

		JCheckBox c8 = new JCheckBox("privateMethod");
		c8.setBounds(264, 125, 113, 25);
		frame.getContentPane().add(c8);

		JCheckBox c10 = new JCheckBox("Method Instruction Dependency");
		c10.setBounds(219, 165, 207, 25);
		frame.getContentPane().add(c10);

		JCheckBox c12 = new JCheckBox("recursive");
		c12.setBounds(8, 206, 113, 25);
		frame.getContentPane().add(c12);

		JCheckBox c11 = new JCheckBox("Field Dependency");
		c11.setBounds(441, 165, 166, 25);
		frame.getContentPane().add(c11);

		white = new JTextField();
		white.setToolTipText("WhiteList");
		white.setBounds(8, 258, 116, 22);
		frame.getContentPane().add(white);
		white.setColumns(10);

		black = new JTextField();
		black.setToolTipText("BlackList");
		black.setColumns(10);
		black.setBounds(8, 317, 116, 22);
		frame.getContentPane().add(black);

		JCheckBox c13 = new JCheckBox("lambda");
		c13.setBounds(125, 206, 113, 25);
		frame.getContentPane().add(c13);

		pattern = new JTextField();
		pattern.setToolTipText("Pattern");
		pattern.setColumns(10);
		pattern.setBounds(8, 367, 116, 22);
		frame.getContentPane().add(pattern);

		JarLocation = new JTextField();
		JarLocation.setToolTipText("Jar location");
		JarLocation.setColumns(10);
		JarLocation.setBounds(154, 258, 116, 22);
		frame.getContentPane().add(JarLocation);

		Output = new JTextField();
		Output.setToolTipText("OutPut Folder");
		Output.setColumns(10);
		Output.setBounds(154, 317, 116, 22);
		frame.getContentPane().add(Output);

		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				File file = new File(Output.getText() + "p.properties");
				try {
					file.createNewFile();
					FileWriter writer2 = new FileWriter(file);
					BufferedWriter writer=new BufferedWriter(writer2);
					writer.write("GVM=true");
					writer.newLine();
					writer.write("fieldDependency=true");
					writer.newLine();
					if (c1.isSelected()) {
						writer.write("publicClass=true");
					} else {
						writer.write("publicClass=false");
					}
					writer.newLine();
					if (c2.isSelected()) {
						writer.write("protectedClass=true");
					} else {
						writer.write("protectedClass=false");
					}
					writer.newLine();
					if (c3.isSelected()) {
						writer.write("publicField=true");
					} else {
						writer.write("publicField=false");
					}
					writer.newLine();
					if (c4.isSelected()) {
						writer.write("protectedField=true");
					} else {
						writer.write("protectedField=false");
					}
					writer.newLine();
					if (c5.isSelected()) {
						writer.write("privateField=true");
					} else {
						writer.write("privateField=false");
					}
					writer.newLine();
					if (c6.isSelected()) {
						writer.write("publicMethod=true");
					} else {
						writer.write("publicMethod=false");
					}
					writer.newLine();
					if (c7.isSelected()) {
						writer.write("protectedMethod=true");
					} else {
						writer.write("protectedMethod=false");
					}
					writer.newLine();
					if (c8.isSelected()) {
						writer.write("privateMethod=true");
					} else {
						writer.write("privateMethod=false");
					}
					writer.newLine();
					if (c9.isSelected()) {
						writer.write("MRD=true");
					} else {
						writer.write("MRD=false");
					}
					writer.newLine();
					if (c10.isSelected()) {
						writer.write("MID=true");
					} else {
						writer.write("MID=false");
					}
					writer.newLine();
					if (c11.isSelected()) {
						writer.write("FD=true");
					} else {
						writer.write("FD=false");
					}
					writer.newLine();
					if (c12.isSelected()) {
						writer.write("recursive=true");
					} else {
						writer.write("recursive=false");
					}
					writer.newLine();
					if (c13.isSelected()) {
						writer.write("lambda=true");
					} else {
						writer.write("lambda=false");
					}
					writer.newLine();
					writer.write("whitelist=" + white.getText() + "");
					writer.newLine();
					writer.write("blacklist=" + black.getText() + "");
					writer.newLine();
					writer.write("pattern=" + pattern.getText() + "");
					writer.newLine();

					writer.close();
					File file2 = new File(Output.getText() + "b.bat");
					
						file2.createNewFile();
						FileWriter batr = new FileWriter(file2);
						BufferedWriter writebat=new BufferedWriter(batr);
						writebat.write("java -jar "+JarLocation.getText()+" "+"-o="+Output.getText()+"d.dot"+" "+"-i="+Output.getText()+"p.properties");
					writebat.newLine();
					writebat.write("dot "+Output.getText()+"d.dot"+" -Tpng -o "+Output.getText()+"p.png");
					writebat.newLine();
					writebat.write(Output.getText()+"p.png");
					
					
				writebat.newLine();		
				writebat.write("PAUSE");
				writebat.close();
				String[] command = {"cmd.exe", "/C", "Start", Output.getText()+"b.bat"};
	            Process p =  Runtime.getRuntime().exec(command); 
				} catch (IOException e1) {
				}

			}
		});
		btnRun.setBounds(357, 316, 97, 25);
		frame.getContentPane().add(btnRun);

		JLabel lblWhitelist = new JLabel("WhiteList");
		lblWhitelist.setBounds(8, 239, 56, 16);
		frame.getContentPane().add(lblWhitelist);

		JLabel lblBlacklist = new JLabel("BlackList");
		lblBlacklist.setBounds(8, 302, 56, 16);
		frame.getContentPane().add(lblBlacklist);

		JLabel lblPattern = new JLabel("Pattern");
		lblPattern.setBounds(8, 352, 56, 16);
		frame.getContentPane().add(lblPattern);

		JLabel lblJarLocation = new JLabel("Jar Location");
		lblJarLocation.setBounds(154, 240, 84, 16);
		frame.getContentPane().add(lblJarLocation);

		JLabel lblOutputFolder = new JLabel("OutPut Folder");
		lblOutputFolder.setBounds(154, 302, 84, 16);
		frame.getContentPane().add(lblOutputFolder);
	}
}
