package Final;

	import javax.swing.*;
	import javax.swing.event.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.PrintWriter;
	import java.util.Scanner;
	import java.util.Vector;
	import javax.swing.table.DefaultTableModel;
	
	public class BankAccountGui extends JFrame {
		//create all the JOptionPane components 
		
		//Panels
		private JPanel dataPanel;
		private JPanel tablePanel;
		private JPanel buttonPanel;
		
		//Labels
		private JLabel accountNumber;
		private JLabel accountType;
		private JLabel accountMinimumBalance;
		private JLabel accountCurrentBalance;
		
		//Text Fields
		private JTextField txtAccountNum;
		private JTextField txtAccountType;
		private JTextField txtAccountMin;
		private JTextField txtAccountCurr;
		
		//Buttons
		private JButton btnClear;
		private JButton btnSave;
		private JButton btnCalculateInterest;
		
		//Table and Scroll Pane
		private JTable dataTable;
		private JScrollPane scrollPane;
	
	//Constructor
	public BankAccountGui() throws FileNotFoundException {
		super("Ryan Healey BankAccount");
		setLocation(100, 400);
		setSize(950, 310);
		setLayout(null);
		setResizable(false);

		//Initialize the components and add them to the JFrame
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 2));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.setBackground(Color.orange);
		
		tablePanel = new JPanel();
		
		//Button labels and color
		btnClear = new JButton("Clear");
		btnClear.setBackground(Color.blue);
		btnClear.setForeground(Color.white);
		btnSave = new JButton("Save");
		btnSave.setBackground(Color.blue);
		btnSave.setForeground(Color.white);
		btnCalculateInterest = new JButton("Calc. Interest");
		btnCalculateInterest.setBackground(Color.blue);
		btnCalculateInterest.setForeground(Color.white);
		
		//Text fields size
		txtAccountNum = new JTextField(50);
		txtAccountType = new JTextField(50);
		txtAccountMin = new JTextField(50);
		txtAccountCurr = new JTextField(50);
		
		//Labels
		accountNumber = new JLabel("Account Number");
		accountType = new JLabel("Account Type");
		accountMinimumBalance = new JLabel("Minimum Balance");
		accountCurrentBalance = new JLabel("Current Balance");
		
		//Add components to JFrame
		dataPanel.add(accountNumber);
		dataPanel.add(txtAccountNum);
		
		dataPanel.add(accountType);
		dataPanel.add(txtAccountType);
		
		dataPanel.add(accountCurrentBalance);
		dataPanel.add(txtAccountCurr);
		
		dataPanel.add(accountMinimumBalance);
		dataPanel.add(txtAccountMin);
	
		dataPanel.setBounds(20, 20, 400, 150);
		dataPanel.setBackground(Color.orange);
		add(dataPanel);
		
		buttonPanel.add(btnSave);
		buttonPanel.add(btnClear);
		buttonPanel.add(btnCalculateInterest);
	
		buttonPanel.setBounds(20, 200, 400, 50);
		buttonPanel.setBackground(Color.orange);
		add(buttonPanel); 

		//Label the data table columns
		String column[] = { "Account Num", "AccType", "CurrBalance",
		"MinBalance" };
		DefaultTableModel model = new DefaultTableModel(column,0);
		dataTable = new JTable(model);
		
		
		scrollPane = new JScrollPane(dataTable);
		scrollPane.setBounds(20, 20, 350, 100);
		
		tablePanel.add(scrollPane);
		tablePanel.setBounds(440, 20, 500, 150);
		tablePanel.setBackground(Color.white);
		add(tablePanel);
		
		//Clear Button Function
		btnClear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//Clear all of the text fields
			txtAccountNum.setText("");
			txtAccountType.setText("");
			txtAccountCurr.setText("");
			txtAccountMin.setText("");
			}
		});
		//Save Button Function
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String accountType = txtAccountType.getText();
					double balance = Double.parseDouble(txtAccountCurr.getText());
			        double minBalance = Double.parseDouble(txtAccountMin.getText());
			        double newBalance = calculateInterest(accountType, balance, minBalance);
			    //Output the variables in Dialog Message
				String outputDisplay = String.format("AccountNo.: %s%n AccountType: %s%n "
						+ "currBalance: %s%n minimumBalance %s%n",
						txtAccountNum.getText(),
						txtAccountType.getText(),
						newBalance,
						txtAccountMin.getText());
				
				JOptionPane.showMessageDialog(null, outputDisplay);
				//Add the variables to the JTable
				model.addRow(new Object[]
						{ txtAccountNum.getText(),
						txtAccountType.getText(),
						newBalance,
						txtAccountMin.getText() });
						txtAccountNum.setText("");
						txtAccountType.setText("");
						txtAccountCurr.setText("");
						txtAccountMin.setText("");
						writeToFile(dataTable);
				}catch (FileNotFoundException e1){
					e1.printStackTrace();
					
				}
				
				}
			});
		//Calculate Interest Button Function
		btnCalculateInterest.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	//Get arguments
		        String accountType = txtAccountType.getText();
		        double balance = Double.parseDouble(txtAccountCurr.getText());
		        double minBalance = Double.parseDouble(txtAccountMin.getText());
		        //Use calculateInterest method
		        double newBalance = calculateInterest(accountType, balance, minBalance);
		        JOptionPane.showMessageDialog(null, "Current Balance After Interest:" + newBalance);
		        
		    }
		});
	
		readFromFile(dataTable);
	}
	
	//Writes the data to the account.txt file
	public void writeToFile(JTable dataTable) throws FileNotFoundException {
		File newFile = new File("C:\\Users\\ryanh\\eclipse-workspace\\javaprogramming\\src\\Final\\account.txt");
				PrintWriter outFile = new PrintWriter(newFile);
				//Loops through the JTable for the values
				for (int row = 0; row < dataTable.getRowCount(); row++) {
					for (int col = 0; col < dataTable.getColumnCount(); col++) {
						outFile.print(dataTable.getValueAt(row, col));
						outFile.print(",");
					}
					outFile.println(" ");
				}
				outFile.close();
		
	}
	//Reads the data from the account.txt file
	public void readFromFile(JTable dataTable) throws FileNotFoundException {
		File file = new File("C:\\Users\\ryanh\\eclipse-workspace\\javaprogramming\\src\\Final\\account.txt");
		Scanner readFile = new Scanner(file);
		String fileData[];
		Vector<Object> rowData;
		
		//While loop to loop through file and place into JTable
		while(readFile.hasNext() ) {
			String line = readFile.nextLine();
			
			fileData = line.split(",");
			
			rowData = new Vector<Object>();
			rowData.add(fileData[0]);
			rowData.add(fileData[1]);
			rowData.add(Double.parseDouble(fileData[2]));
			rowData.add(Double.parseDouble(fileData[3]));
			
			DefaultTableModel model = (DefaultTableModel)dataTable.getModel();
			model.addRow(rowData);
			
	
			
		}
	}
	public static double calculateInterest(String accountType, double balance, double minBalance) {
	    double interest = 0.0;
	    double serviceCharge = 0.0;
	    
	    //Add service charge based on account type
	    if (balance < minBalance) {
	        if (accountType == ("S") || accountType == ("s")) {
	            serviceCharge = 10.0;
	        } else if (accountType == ("C")|| accountType == ("c")) {
	            serviceCharge = 25.0;
	        }
	        balance -= serviceCharge;
	    }
	    
	    //Calculates interest based on account type
	    if (accountType == ("S")|| accountType == ("s")) {
	        interest = balance * 0.04;
	    } else if (accountType == ("C")|| accountType == ("c")) {
	        if (balance >= minBalance && balance <= minBalance + 5000) {
	            interest = balance * 0.03;
	        } else if (balance > minBalance + 5000) {
	            interest = balance * 0.05;
	        }
	    }
	    
	    //Adds the interest to the current balance
	    balance += interest;
	    
	    return balance;
	}

}