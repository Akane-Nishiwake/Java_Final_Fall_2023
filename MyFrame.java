package cop2805;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;

public class MyFrame extends JFrame 
{
	
	//private class members
	 private JTextField lineNumber;
	 private DefaultListModel<String> listModel = new DefaultListModel<String>();
	 private GroupLayout layout;
	 private JPanel panel;
	 private JLabel prompt;
	 private JLabel response;
	 private JList<String> Lines;
	 private JButton confirmButton;
	 private JRadioButton hamlet;
	 private JRadioButton macbeth;
	 private JRadioButton merchantOfVenice;
	 private ButtonGroup buttonGroup;
	//constructor
	public MyFrame()
	{
		super();
		init(); //calling the init on construction of the class.
	}
	//initialization
	private void init()
	{
		int frameWidth = 600;
		int frameHeight = 300;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Line Searcher");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) (screenSize.getWidth() /2) - frameWidth, (int) (screenSize.getHeight()/2) - frameHeight , frameWidth, frameHeight);
		this.setSize(frameWidth, frameHeight);
		
		//creating frame layout
		layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		//creating panel
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT)); //making sure that the attached components are aligned
		panel.setBackground(Color.WHITE); 
		panel.setBounds(100,100, 300, 300);
		//prompt components needed
		prompt = new JLabel("Line to Search for: ");
		lineNumber = new JTextField(100);
		//response components needed
		response = new JLabel("Lines Found: ");	
		Lines = new JList<String>(listModel);
		//radio button creations
		hamlet = new JRadioButton("Hamelt");
		macbeth = new JRadioButton("Macbeth");
		merchantOfVenice = new JRadioButton("Merchant of Venice");
		buttonGroup = new ButtonGroup();
		buttonGroup.add(hamlet);
		buttonGroup.add(macbeth);
		buttonGroup.add(merchantOfVenice);
		//set default radio button
		hamlet.setSelected(true);
		//Connect button creation
		confirmButton = new JButton("Connect");
		//listening if the button was pressed
		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmConnection();
			}
		});
		//attach the lines to the Panel
		panel.add(Lines);

		
		settingLayouts();
		//setting default frame along with where it will appear
	}
	
	private void settingLayouts()
	{
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING) //stacking
						.addComponent(prompt)
						.addGap(50)
						.addComponent(response)
						.addGap(50)
						.addComponent(confirmButton)
						.addComponent(hamlet)
						)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING) //stacking
						.addComponent(lineNumber)
						.addComponent(panel)
						.addComponent(macbeth)
						)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(merchantOfVenice)
						)
				);
		//setting the vertical layout of the group layout
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE) //aligning
						.addComponent(prompt)
						.addGap(50)
						.addComponent(lineNumber)
						)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE) //aligning
						.addComponent(response)
						.addGap(50)
						.addComponent(panel)
						)
				.addGap(25)
				.addComponent(confirmButton)
				.addGap(25)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE) //aligning
						.addComponent(hamlet)
						.addComponent(macbeth)
						.addComponent(merchantOfVenice)
						)
				);
	}
	
	private void confirmConnection()
	{
		//1. Clear the JList in the GUI
		listModel.clear();
		//2. Read the string from the text field
		String lineNum = lineNumber.getText() + "\n";
		//grabbing the selected radio button and setting the output text
		String radio ="";
		if(hamlet.isSelected())
			radio = "Hamlet\n";
		if(macbeth.isSelected())
			radio = "Macbeth\n";
		if(merchantOfVenice.isSelected())
			radio = "Merchant of Venice\n";
		//3. Open a socket to the server
		try 
		{
			Socket connection = new Socket("127.0.0.1",1236);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			OutputStream output = connection.getOutputStream();
			output.write(radio.getBytes());
			//4. Send the string to the server
			output.write(lineNum.getBytes());
			//5. Read the results from the server
			String serverResponse  = "";
			while(serverResponse != null)
			{
				serverResponse = reader.readLine();
				listModel.addElement(serverResponse);
			}
			//6. Close the socket to the server
			if(!connection.isClosed())
				connection.close();
		} 
		catch (IOException e1) 
		{
			listModel.addElement("ERROR: SHUTDOWN ACTIVATED");
			e1.printStackTrace();
		}
	}
}
