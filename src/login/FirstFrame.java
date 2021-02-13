package login;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//방찾기 버튼 프레임
public class FirstFrame {
    private JFrame mainFrame;
    private JButton searchButton;
    public FirstFrame(Client client){
        mainFrame = new JFrame(""); //메인프레임
        mainFrame.setLayout(null);	//메인프레인 레이아웃설정
        searchButton = new JButton("방 찾기");//방찾기 버튼
        mainFrame.setSize(new Dimension(500,500));//메인프레인 크기설정
        searchButton.addActionListener(e -> { //버튼 이벤트 처리기
            AptPrice aptPrice = new AptPrice(client);//아파트 매매정보를 가져옴
        });
        mainFrame.add(searchButton);  //메인프레임에 검색버튼 추가
        searchButton.setBounds(150,150,175,175);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.addWindowListener(new WindowAdapter() {// 창 오른쪽 상단 X버튼 눌렀을 경우 처리
            public void windowClosing(WindowEvent e) {
                System.exit(0); //종료
            }
        });
    }
}
