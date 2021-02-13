package login;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame {
	private JDialog loginDlg;
	private JFrame loginFrame;// 화면을 만든다
	private JLabel jLabel;// 로그인 텍스트용
	private JLabel id;// id 텍스트용
	private JLabel password;// password 텍스트용
	private JTextField idTextField;// id 입력칸
	private JPasswordField passwordField;// password 입력칸
	private JButton loginButton;// 로그인버튼
	private JButton register;// 회원가입 버튼인데 setBorderPainted(false)하면 걍 버튼이 아니고 텍스트인 것 처럼 보임
	private JButton findIDPassword;// id 비번 찾기 윗줄과 동일한 원리
	private GridBagConstraints gridConstraints;// grid 배치를 할 것이다
	private Client client;
	private RegisterFrame rf;
	private FindIDPasswordFrame findIDPasswordFrame;

	public LoginFrame(Client client) { // 생성자니까 초기화만 했다.
		this.client = client;
		loginFrame = new JFrame("로그인 화면");
		loginDlg = new JDialog(loginFrame, "로그인 화면", Dialog.ModalityType.APPLICATION_MODAL);
		jLabel = new JLabel();
		id = new JLabel();
		password = new JLabel();
		idTextField = new JTextField();
		passwordField = new JPasswordField();
		loginButton = new JButton();
		register = new JButton();
		findIDPassword = new JButton();
		gridConstraints = new GridBagConstraints();
	}

	public void runLoginFrame() {

		loginFrame.getContentPane().setLayout(new GridBagLayout());
		jLabel.setText("로그인");// setText는 파라미터에 있는 문자열로 세팅한다는 뜻
		jLabel.setFont(new Font("바탕", Font.BOLD, 15)); // 폰트를 설정하려면 이런 식으로 하면 된다. JLabel. 시스템 기본 지원 폰트는 다 된다고 보면 된다
		gridConstraints.gridx = 1; // 로그인이란 글자를 왼쪽에 넣고 싶으면 0 중앙 1 오른쪽 2
		gridConstraints.gridy = 0; // 그리드니까 격자라고 생가하면 된다. 0,0 좌표에 넣겠다는 뜻 그래서 엑셀 켜서 엑셀에서 미리 그려서 하면 편하다
		loginFrame.add(jLabel, gridConstraints);
		id.setText("ID");
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 1;
		loginFrame.add(id, gridConstraints); // add(넣을 거 , grid)
		password.setText("password");
		gridConstraints.gridy = 2;
		loginFrame.add(password, gridConstraints);
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 1;
		idTextField.setColumns(5);// 칸 크기 지정하는 거
		loginFrame.add(idTextField, gridConstraints);
		gridConstraints.gridy = 2;
		passwordField.setColumns(5);
		loginFrame.add(passwordField, gridConstraints);
		loginButton.setText("로그인!");
		gridConstraints.gridx = 3;
		gridConstraints.gridy = 1;
		loginFrame.add(loginButton, gridConstraints);

		register.setBorderPainted(false);// 버튼을
		register.setFocusPainted(false);// label처럼
		register.setContentAreaFilled(false);// 보이게
		register.setOpaque(false);// 하기 위해 하는 작업
		register.setText("회원가입");
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 3;
		loginFrame.add(register, gridConstraints);
		findIDPassword.setBorderPainted(false);
		findIDPassword.setFocusPainted(false);
		findIDPassword.setContentAreaFilled(false);
		findIDPassword.setOpaque(false);
		findIDPassword.setText("ID/비밀번호 찾기");
		gridConstraints.gridx = 1;
		loginFrame.add(findIDPassword, gridConstraints);
		loginFrame.setResizable(false);// 창 크기를 사용자가 조작하는 걸 막겠다는 뜻
		loginFrame.pack();// 화면 크기를 현재 들어간 애들에 맞게 조작하고 빈 부분을 없앤다.
		loginFrame.setVisible(true);
		loginFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);// 창 오른쪽 상단 X버튼 눌렀을 경우 처리
			}
		});
		findIDPassword.addActionListener(e -> {
			// 아이디 비번 찾기 눌렀을 때 처리할 코드
			findIDPasswordFrame = new FindIDPasswordFrame(client);
			findIDPasswordFrame.run();
		});
		register.addActionListener(e -> {
			// 회원가입 눌렀을 때 처리할 코드
			rf = new RegisterFrame(client);
			rf.run();
		});
		loginButton.addActionListener(e -> {
			String ID = idTextField.getText();
			String password = String.valueOf(passwordField.getPassword());// getPassword()메소드는 char[] 즉 캐릭터 배열 형태로
			StringBuilder stringBuilder = new StringBuilder("로그인");
			stringBuilder.append("\n");
			stringBuilder.append(ID).append("\n");
			stringBuilder.append(password);
			client.send(stringBuilder.toString());// 보내는 형식 로그인\n아이디\n비밀번호
			// 반환을 한다 그걸 쓰기 편하게 할려고 String으로 바꾸는 거
			// ID와 password에 idTextField, passwordField에 있는 문자열을 받아서 넣는다
			// 로그인 버튼 눌렀을 때 처리할 코드
		});
	}

	public void disposeLoginFrame() {
		JOptionPane.showMessageDialog(null, "회원님 환영합니다~", "로그인 성공!", JOptionPane.INFORMATION_MESSAGE);
		loginDlg.dispose();
		loginDlg.setVisible(false);
	}

	public void loginFailed() {
		JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다. 아이디와 비밀번호를 다시 확인해주세요", "로그인 실패",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void findIDframedispose() {

		findIDPasswordFrame.disposefindIDpasswordFrame();

	}

	public void findIDframeShowMessage(String message) {
		findIDPasswordFrame.showmessage(message);
	}
}
