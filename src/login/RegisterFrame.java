package login;

import client.Client;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class RegisterFrame {
    private JFrame registFrame;
    private JLabel registerText;
    private JLabel name;
    private JLabel birth;
    private JDateChooser birthday;
    private JLabel ID;
    private JLabel password;
    private JLabel passwordCheck;
    private JComboBox<String> phone;
    private JLabel rnqnstjs1;
    private JLabel rnqnstjs2;
    private JLabel email;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField nameField;
    private JTextField IDField;
    private JPasswordField passwordField;
    private JPasswordField passwordcheckField;
    private JTextField midllephonenumber;
    private JTextField endphonenumber;
    private JTextField emailField;
    private GridBagConstraints gridConstraints;
    private JDialog modal;
    private final String[] headPhoneNumberLists = {"010", "011", "012", "015", "016", "017", "018", "019"};
    private Client client;

    //https://ko.wikipedia.org/wiki/%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD%EC%9D%98_%EC%A0%84%ED%99%94%EB%B2%88%ED%98%B8_%EC%B2%B4%EA%B3%84#%EC%9D%B4%EB%8F%99%ED%86%B5%EC%8B%A0_%EB%B0%8F_%EB%B6%80%EA%B0%80%ED%86%B5%EC%8B%A0%EB%A7%9D_%EB%93%B1_(01X)
    public RegisterFrame(Client client) {
        this.client = client;
        registFrame = new JFrame();
        modal = new JDialog(registFrame, "회원가입", Dialog.ModalityType.APPLICATION_MODAL);//모달 형태의 대화상자 만드는 방법

        registerText = new JLabel();
        name = new JLabel();
        birth = new JLabel();
        birthday = new JDateChooser();
        ID = new JLabel();
        password = new JLabel();
        passwordCheck = new JLabel();
        phone = new JComboBox<>();
        rnqnstjs1 = new JLabel();
        rnqnstjs2 = new JLabel();
        email = new JLabel();
        okButton = new JButton();
        cancelButton = new JButton();
        nameField = new JTextField();
        IDField = new JTextField();
        passwordField = new JPasswordField();
        passwordcheckField = new JPasswordField();
        midllephonenumber = new JTextField();
        endphonenumber = new JTextField();
        emailField = new JTextField();
        gridConstraints = new GridBagConstraints();
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modal.dispose();
                modal.setVisible(false);
            }
        });

    }

    public void run() {
        registerText.setText("회원가입");
        modal.getContentPane().setLayout(new GridBagLayout());
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        modal.getContentPane().add(registerText, gridConstraints);
        name.setText("이름 : ");
        gridConstraints.gridy = 1;
        modal.getContentPane().add(name, gridConstraints);
        birth.setText("생년월일 : ");
        gridConstraints.gridy = 2;
        modal.getContentPane().add(birth, gridConstraints);
        birthday.setMaxSelectableDate(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -24);
        birthday.setDate(cal.getTime());
        gridConstraints.gridx = 1;
        modal.getContentPane().add(birthday, gridConstraints);
        gridConstraints.gridx = 3;
        ID.setText("아이디 : ");
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        modal.getContentPane().add(ID, gridConstraints);
        password.setText("PW : ");
        gridConstraints.gridy = 4;
        modal.getContentPane().add(password, gridConstraints);
        passwordCheck.setText("PW 확인 : ");
        gridConstraints.gridy = 5;
        modal.getContentPane().add(passwordCheck, gridConstraints);
        nameField.setColumns(5);
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        modal.getContentPane().add(nameField, gridConstraints);
        IDField.setColumns(5);
        gridConstraints.gridy = 3;
        modal.getContentPane().add(IDField, gridConstraints);
        passwordField.setColumns(5);
        gridConstraints.gridy = 4;
        modal.getContentPane().add(passwordField, gridConstraints);
        passwordcheckField.setColumns(5);
        gridConstraints.gridy = 5;
        modal.getContentPane().add(passwordcheckField, gridConstraints);
        for (String obj : headPhoneNumberLists) {
            phone.addItem(obj);
        }
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 6;
        modal.getContentPane().add(phone, gridConstraints);
        gridConstraints.gridx = 1;
        midllephonenumber.setColumns(5);
        modal.getContentPane().add(midllephonenumber, gridConstraints);
        gridConstraints.gridx = 2;
        endphonenumber.setColumns(5);
        modal.getContentPane().add(endphonenumber, gridConstraints);
        email.setText("이메일 : ");
        gridConstraints.gridy = 7;
        gridConstraints.gridx = 0;
        modal.getContentPane().add(email, gridConstraints);
        gridConstraints.gridx = 1;
        gridConstraints.gridwidth = 2;
        emailField.setColumns(10);
        modal.getContentPane().add(emailField, gridConstraints);
        gridConstraints.gridwidth = 1;
        okButton.setText("확인");
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 8;
        gridConstraints.gridwidth = 2;
        modal.getContentPane().add(okButton, gridConstraints);
        okButton.addActionListener(e -> {
            if (!midllephonenumber.getText().matches("^[0-9]{4}$")) {
                JOptionPane.showMessageDialog(null, "폰번호 중간번호 오류 다시 입력하세요", "오류", JOptionPane.INFORMATION_MESSAGE);
            } else if (!endphonenumber.getText().matches("^[0-9]{4}$")) {
                JOptionPane.showMessageDialog(null, "폰번호 끝번호 오류 다시 입력하세요", "오류", JOptionPane.INFORMATION_MESSAGE);
            } else if (!emailField.getText().matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$")) {
                JOptionPane.showMessageDialog(null, "이메일 형식 오류 다시 입력하세요", "오류", JOptionPane.INFORMATION_MESSAGE);
            } else if (String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordcheckField.getPassword()))) {
                //확인 버튼을 눌렀을 때 처리할 구문
                StringBuilder stringBuilder = new StringBuilder("회원가입");
                stringBuilder.append("\n");
                stringBuilder.append("이름 : ").append(nameField.getText()).append("\n");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년MM월dd일");
                String dateString = simpleDateFormat.format(birthday.getDate());
                stringBuilder.append("생년월일 : ").append(dateString).append("\n");
                stringBuilder.append("id : ").append(IDField.getText()).append("\n");
                stringBuilder.append("pw : ").append(String.valueOf(passwordField.getPassword())).append("\n");
                stringBuilder.append("휴대폰 번호").append(phone.getSelectedItem());
                stringBuilder.append("-").append(midllephonenumber.getText());
                stringBuilder.append("-").append(endphonenumber.getText()).append("\n");
                stringBuilder.append("email : ").append(emailField.getText()).append("\n");
                client.send(stringBuilder.toString());
                JOptionPane.showMessageDialog(null, "회원가입 성공! 로그인하세요~", "회원가입 성공!", JOptionPane.INFORMATION_MESSAGE);
                registFrame.dispose();
                registFrame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "비밀번호를 다시 확인하세요", "비밀번호 오류", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        modal.setResizable(false);
        modal.pack();
        modal.setVisible(true);

    }

}
