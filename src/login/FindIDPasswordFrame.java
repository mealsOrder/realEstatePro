package login;

import client.Client;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FindIDPasswordFrame {
	private JFrame findIDPasswordFrame;
	private JDialog findIDPasswordDlg;
	private JButton findIDButton;
	private JButton findPasswordButton;
	private JPanel groupBox;
	private JPanel groupBox2;
	private JPanel groupBox3;
	private JLabel findByEmailsName;
	private JTextField findByEmailsNameField;
	private JLabel findByEmailsBirth;
	private JDateChooser findByEmailsBirthChooser;
	private JLabel findByEmailsEmail;
	private JTextField findByEmailsEmailTextField;
	private JButton findByEmailButton;
	private JLabel findByPhoneNumberName;
	private JTextField findByPhoneNumberNameField;
	private JLabel findByPhoneNumberBirth;
	private JDateChooser findByPhoneNumberBirthChooser;
	private JLabel findByPhoneNumberPhoneNumber;
	private JTextField findByPhoneNumberPhoneNumberField;
	private JButton findByPhoneNumberButton;
	private GridBagConstraints gridBagConstraints;
	private Client client;

	// Textfield의 setColumn(int)는 텍스트 입력 칸의 길이를 설정하는 것 그것말곤 앞의 소스 주석 참고
	public FindIDPasswordFrame(Client client) {
		this.client = client;
		findIDPasswordFrame = new JFrame();
		findIDPasswordDlg = new JDialog(findIDPasswordFrame, "아이디 비밀번호 찾기!!", Dialog.ModalityType.APPLICATION_MODAL);
		;
		findIDPasswordDlg.setLayout(new GridBagLayout());
		findIDButton = new JButton();
		findPasswordButton = new JButton();
		groupBox = new JPanel();
		groupBox2 = new JPanel();
		findByEmailsName = new JLabel();
		findByEmailsNameField = new JTextField();
		findByEmailsBirth = new JLabel();
		findByEmailsBirthChooser = new JDateChooser();
		findByEmailsEmail = new JLabel();
		findByEmailsEmailTextField = new JTextField();
		findByEmailButton = new JButton();
		findByPhoneNumberName = new JLabel();
		findByPhoneNumberNameField = new JTextField();
		findByPhoneNumberBirthChooser = new JDateChooser();
		findByPhoneNumberPhoneNumber = new JLabel();
		findByPhoneNumberPhoneNumberField = new JTextField();
		findByPhoneNumberButton = new JButton();
		findByPhoneNumberBirth = new JLabel();
		gridBagConstraints = new GridBagConstraints();
		groupBox3 = new JPanel();
		groupBox3.setLayout(new GridBagLayout());
		findIDPasswordDlg.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				findIDPasswordDlg.dispose();
				findIDPasswordDlg.setVisible(false);
			}
		});

	}

	public void run() {

		findIDButton.setText("아이디 찾기");
		findPasswordButton.setText("비밀번호 찾기");
		findIDButton.setBorderPainted(false);// 버튼을
		findIDButton.setFocusPainted(false);// label처럼
		findIDButton.setContentAreaFilled(false);// 보이게
		findIDButton.setOpaque(false);// 하기 위해 하는 작업
		findPasswordButton.setBorderPainted(false);// 버튼을
		findPasswordButton.setFocusPainted(false);// label처럼
		findPasswordButton.setContentAreaFilled(false);// 보이게
		findPasswordButton.setOpaque(false);// 하기 위해 하는 작업
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		findIDPasswordDlg.getContentPane().add(findIDButton, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		findIDPasswordDlg.getContentPane().add(findPasswordButton, gridBagConstraints);
		idbutton();
		findIDButton.addActionListener(e -> { // 아이디 찾기 버튼 눌렀을때 이벤트 처리기

			idbutton();
		});
		findPasswordButton.addActionListener(e -> { // 비밀번호 찾기 눌렀을때 이벤트 처리기

			passwordbutton();
		});
		findIDPasswordDlg.pack();
		findIDPasswordDlg.setResizable(false);
		findIDPasswordDlg.setVisible(true);

	}

	// 아이디 찾기 버튼 구현
	private void idbutton() {
		groupBox3.removeAll();
		findIDPasswordDlg.remove(groupBox3);
		// 이메일을 입력하면 아이디를 찾아준다.
		groupBox.setBorder(BorderFactory.createTitledBorder("이메일로 찾기"));
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		findIDPasswordDlg.getContentPane().add(groupBox, gridBagConstraints);
		groupBox.setLayout(new GridBagLayout());
		// 휴대폰번호를 입력하면 아이디를 찾아준다
		groupBox2.setBorder(BorderFactory.createTitledBorder("휴대폰번호로 찾기"));
		groupBox2.setLayout(new GridBagLayout());
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		findIDPasswordDlg.getContentPane().add(groupBox2, gridBagConstraints);
		findByEmailsName.setText("이름 : ");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		groupBox.add(findByEmailsName, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		findByEmailsNameField.setColumns(10);
		groupBox.add(findByEmailsNameField, gridBagConstraints);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		findByEmailsBirth.setText("생년월일 : ");
		groupBox.add(findByEmailsBirth, gridBagConstraints);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -24);
		findByEmailsBirthChooser.setDate(cal.getTime());
		findByEmailsBirthChooser.setMaxSelectableDate(new Date());
		gridBagConstraints.gridx = 1;
		groupBox.add(findByEmailsBirthChooser, gridBagConstraints);
		findByEmailsEmail.setText("이메일 : ");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		groupBox.add(findByEmailsEmail, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		findByEmailsEmailTextField.setColumns(10);
		groupBox.add(findByEmailsEmailTextField, gridBagConstraints);
		findByEmailButton.setText("찾기!");
		findByEmailButton.addActionListener(e -> {
			StringBuilder stringBuilder = new StringBuilder("이메일로 아이디 찾기");
			stringBuilder.append("\n");
			stringBuilder.append("이름 : ").append(findByEmailsNameField.getText()).append("\n");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년MM월dd일");
			String birthString = simpleDateFormat.format(findByEmailsBirthChooser.getDate());
			stringBuilder.append("생년월일 : ").append(birthString).append("\n");
			stringBuilder.append("이메일 : ").append(findByEmailsEmailTextField.getText()).append("\n");
			client.send(stringBuilder.toString());
		});
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 0;
		groupBox.add(findByEmailButton, gridBagConstraints);
		gridBagConstraints.gridwidth = 1;
		findByPhoneNumberName.setText("이름 : ");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		groupBox2.add(findByPhoneNumberName, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		findByPhoneNumberNameField.setColumns(10);
		groupBox2.add(findByPhoneNumberNameField, gridBagConstraints);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		findByPhoneNumberBirth.setText("생년월일 : ");
		groupBox2.add(findByPhoneNumberBirth, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		groupBox2.add(findByPhoneNumberBirthChooser, gridBagConstraints);
		findByPhoneNumberBirthChooser.setDate(cal.getTime());
		findByPhoneNumberBirthChooser.setMaxSelectableDate(new Date());
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 0;
		findByPhoneNumberPhoneNumber.setText("휴대폰 번호 : ");
		groupBox2.add(findByPhoneNumberPhoneNumber, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		findByPhoneNumberPhoneNumberField.setColumns(10);
		groupBox2.add(findByPhoneNumberPhoneNumberField, gridBagConstraints);
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		findByPhoneNumberButton.setText("찾기");
		findByPhoneNumberButton.addActionListener(e -> {
			StringBuilder stringBuilder = new StringBuilder("휴대폰 번호로 아이디 찾기");
			stringBuilder.append("\n");
			stringBuilder.append("이름 : ").append(findByPhoneNumberNameField.getText()).append("\n");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년MM월dd일");
			String birthString = simpleDateFormat.format(findByPhoneNumberBirthChooser.getDate());
			stringBuilder.append("생년월일 : ").append(birthString).append("\n");
			stringBuilder.append("휴대폰 번호 : ").append(findByPhoneNumberPhoneNumberField.getText()).append("\n");
			client.send(stringBuilder.toString());
		});
		groupBox2.add(findByPhoneNumberButton, gridBagConstraints);
		findIDPasswordDlg.getContentPane().revalidate();
		findIDPasswordDlg.getContentPane().repaint();

	}

	// 비밀번호 찾기 구현
	private void passwordbutton() {
		groupBox.removeAll();
		groupBox2.removeAll();
		findIDPasswordDlg.remove(groupBox);
		findIDPasswordDlg.remove(groupBox2);
		findIDPasswordDlg.getContentPane().revalidate();
		findIDPasswordDlg.getContentPane().repaint();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		findIDPasswordDlg.getContentPane().add(groupBox3, gridBagConstraints);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		JLabel id = new JLabel("아이디 : ");
		groupBox3.add(id, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		JTextField idText = new JTextField();
		idText.setColumns(10);
		groupBox3.add(idText, gridBagConstraints);
		JLabel name = new JLabel("이름");
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		groupBox3.add(name, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		JTextField nameText = new JTextField();
		nameText.setColumns(10);
		groupBox3.add(nameText, gridBagConstraints);
		JLabel birth = new JLabel("생년월일");
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 0;
		groupBox3.add(birth, gridBagConstraints);
		JDateChooser birthChooser = new JDateChooser();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -24);
		birthChooser.setDate(cal.getTime());
		birthChooser.setMaxSelectableDate(new Date());
		gridBagConstraints.gridx = 1;
		groupBox3.add(birthChooser, gridBagConstraints);
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 2;
		JButton findButton = new JButton("찾기!");
		groupBox3.add(findButton, gridBagConstraints);
		findButton.addActionListener(e -> {
			StringBuilder stringBuilder = new StringBuilder("비밀번호 찾기").append("\n");
			stringBuilder.append("아이디 : ").append(idText.getText()).append("\n");
			stringBuilder.append("이름 : ").append(nameText.getText()).append("\n");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년MM월dd일");
			String birthString = simpleDateFormat.format(birthChooser.getDate());
			stringBuilder.append("생년월일 : ").append(birthString).append("\n");
			client.send(stringBuilder.toString());
		});
		findIDPasswordDlg.getContentPane().revalidate();
		findIDPasswordDlg.getContentPane().repaint();
	}

	public void disposefindIDpasswordFrame() {
		findIDPasswordDlg.dispose();
		findIDPasswordDlg.setVisible(false);
	}

	public void showmessage(String message) {
		JOptionPane.showMessageDialog(null, message, "응답", JOptionPane.INFORMATION_MESSAGE);
	}
}
