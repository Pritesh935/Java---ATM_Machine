import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ATMGUI extends JFrame implements ActionListener {

    // ATM Data
    private int pin = 1234;
    private double balance = 5000;

    private ArrayList<String> history = new ArrayList<>();

    // GUI Components
    JLabel titleLabel, pinLabel, amountLabel, resultLabel;
    JTextField amountField;
    JPasswordField pinField;
    JTextArea historyArea;

    JButton loginBtn, balanceBtn, depositBtn,
            withdrawBtn, pinBtn, statementBtn, exitBtn;

    boolean loggedIn = false;

    // Constructor
    ATMGUI() {

        setTitle("Advanced Java ATM");
        setSize(500, 700);
        setLayout(null);
        getContentPane().setBackground(new Color(223, 246, 255));

        // Title
        titleLabel = new JLabel("ATM Machine");
        titleLabel.setBounds(150, 20, 250, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 53, 102));
        add(titleLabel);

        // PIN Label
        pinLabel = new JLabel("Enter PIN");
        pinLabel.setBounds(50, 90, 100, 30);
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(pinLabel);

        // PIN Field
        pinField = new JPasswordField();
        pinField.setBounds(180, 90, 200, 30);
        add(pinField);

        // Amount Label
        amountLabel = new JLabel("Enter Amount");
        amountLabel.setBounds(50, 140, 120, 30);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(amountLabel);

        // Amount Field
        amountField = new JTextField();
        amountField.setBounds(180, 140, 200, 30);
        add(amountField);

        // Result Label
        resultLabel = new JLabel("Welcome!");
        resultLabel.setBounds(150, 190, 250, 30);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(Color.GREEN);
        add(resultLabel);

        // History Area
        historyArea = new JTextArea();
        historyArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBounds(50, 240, 380, 150);
        add(scrollPane);

        // Buttons
        loginBtn = createButton("Login", 50, 420, Color.BLUE);
        balanceBtn = createButton("Check Balance", 250, 420, Color.GREEN);
        depositBtn = createButton("Deposit", 50, 480, Color.ORANGE);
        withdrawBtn = createButton("Withdraw", 250, 480, Color.RED);
        pinBtn = createButton("Change PIN", 50, 540, new Color(128, 0, 128));
        statementBtn = createButton("Mini Statement", 250, 540, new Color(139, 69, 19));
        exitBtn = createButton("Exit", 150, 600, Color.BLACK);

        // Disable Buttons Before Login
        balanceBtn.setEnabled(false);
        depositBtn.setEnabled(false);
        withdrawBtn.setEnabled(false);
        pinBtn.setEnabled(false);
        statementBtn.setEnabled(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Create Button Method
    JButton createButton(String text, int x, int y, Color color) {

        JButton btn = new JButton(text);

        btn.setBounds(x, y, 150, 40);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));

        btn.addActionListener(this);

        add(btn);

        return btn;
    }

    // Update History
    void updateHistory() {

        historyArea.setText("");

        for (String item : history) {
            historyArea.append(item + "\n");
        }
    }

    // Enable Buttons
    void enableButtons() {

        balanceBtn.setEnabled(true);
        depositBtn.setEnabled(true);
        withdrawBtn.setEnabled(true);
        pinBtn.setEnabled(true);
        statementBtn.setEnabled(true);
    }

    // Button Actions
    public void actionPerformed(ActionEvent e) {

        // Login
        if (e.getSource() == loginBtn) {

            try {

                int enteredPin = Integer.parseInt(pinField.getText());

                if (enteredPin == pin) {

                    loggedIn = true;

                    resultLabel.setText("Login Successful!");
                    resultLabel.setForeground(Color.GREEN);

                    enableButtons();

                    JOptionPane.showMessageDialog(this,
                            "Login Successful");

                } else {

                    resultLabel.setText("Incorrect PIN");
                    resultLabel.setForeground(Color.RED);

                    JOptionPane.showMessageDialog(this,
                            "Incorrect PIN");
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,
                        "PIN must be numeric");
            }
        }

        // Check Balance
        if (e.getSource() == balanceBtn && loggedIn) {

            history.add("Checked Balance: $" + balance);

            resultLabel.setText("Current Balance: $" + balance);
            resultLabel.setForeground(Color.BLUE);

            updateHistory();
        }

        // Deposit
        if (e.getSource() == depositBtn && loggedIn) {

            try {

                double amount =
                        Double.parseDouble(amountField.getText());

                if (amount > 0) {

                    balance += amount;

                    history.add("Deposited: $" + amount);

                    resultLabel.setText(
                            "Deposited $" + amount);

                    resultLabel.setForeground(Color.GREEN);

                    updateHistory();

                    amountField.setText("");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Invalid Amount");
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,
                        "Enter valid amount");
            }
        }

        // Withdraw
        if (e.getSource() == withdrawBtn && loggedIn) {

            try {

                double amount =
                        Double.parseDouble(amountField.getText());

                if (amount > 0 && amount <= balance) {

                    balance -= amount;

                    history.add("Withdrawn: $" + amount);

                    resultLabel.setText(
                            "Withdrawn $" + amount);

                    resultLabel.setForeground(Color.GREEN);

                    updateHistory();

                    amountField.setText("");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Insufficient Funds");
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,
                        "Enter valid amount");
            }
        }

        // Change PIN
        if (e.getSource() == pinBtn && loggedIn) {

            try {

                int oldPin = Integer.parseInt(
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Old PIN"
                        )
                );

                int newPin = Integer.parseInt(
                        JOptionPane.showInputDialog(
                                this,
                                "Enter New PIN"
                        )
                );

                if (oldPin == pin) {

                    pin = newPin;

                    history.add("PIN Changed");

                    updateHistory();

                    JOptionPane.showMessageDialog(this,
                            "PIN Changed Successfully");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Incorrect Old PIN");
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,
                        "Invalid PIN");
            }
        }

        // Mini Statement
        if (e.getSource() == statementBtn && loggedIn) {

            if (history.size() == 0) {

                JOptionPane.showMessageDialog(this,
                        "No Transactions Available");

            } else {

                String statement = "";

                int start =
                        Math.max(history.size() - 5, 0);

                for (int i = start; i < history.size(); i++) {

                    statement += history.get(i) + "\n";
                }

                JOptionPane.showMessageDialog(this,
                        statement,
                        "Mini Statement",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Exit
        if (e.getSource() == exitBtn) {

            System.exit(0);
        }
    }

    // Main Method
    public static void main(String[] args) {

        new ATMGUI();
    }
}