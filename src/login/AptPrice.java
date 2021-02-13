package login;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import client.Client;
import parsing.ExcelPs;

public class AptPrice {
    private JDialog aptPriceDlg;
    private JFrame aptPriceFrame;
    private JButton sidoSelectButton;
    private JButton filterSearchButton;
    private GridBagConstraints gridBagConstraints;
    private String curSelectedItem;
    private Client client;
    private JCalendar calendar;
    public String curSelectedDate;
    public String input = null;
    public String sidoName = null;
    public String cateGory = null; 
    public ExcelPs ps;

    public AptPrice(Client client) {
        this.client = client;
        aptPriceFrame = new JFrame();
        aptPriceDlg = new JDialog(aptPriceFrame, "아파트 가격", Dialog.ModalityType.APPLICATION_MODAL);
        aptPriceDlg.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        sidoSelectButton = new JButton();
        sidoSelectButton.setText("시도를 선택하세요!");
        sidoSelectButton.setContentAreaFilled(false);
        sidoSelectButton.setPreferredSize(new Dimension(200, 60));
        sidoSelectButton.addActionListener(e -> {
            sidoDongSearch sidoDongSearch = new sidoDongSearch();
        });
        filterSearchButton = new JButton();
        filterSearchButton.setText("필터");
        filterSearchButton.setPreferredSize(new Dimension(60, 60));

        filterSearchButton.addActionListener(e -> {
            if (curSelectedItem != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                curSelectedDate = simpleDateFormat.format(calendar.getDate());
                new SelectPage();
            } else {
                JOptionPane.showMessageDialog(null, "시/도와 동을 먼저 입력해주세요.", "오류", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        aptPriceDlg.getContentPane().add(sidoSelectButton, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        aptPriceDlg.getContentPane().add(filterSearchButton, gridBagConstraints);
        calendar = new JCalendar();
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        YearMonth yearMonthNow = YearMonth.now(zoneId);
        YearMonth yearMonthPrevious = yearMonthNow.minusMonths(1);
        LocalDate lastOfMonth = yearMonthPrevious.atEndOfMonth();
        calendar.setMaxSelectableDate(java.sql.Date.valueOf(lastOfMonth));
        calendar.setDate(java.sql.Date.valueOf(lastOfMonth));
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        aptPriceDlg.getContentPane().add(calendar, gridBagConstraints);
        aptPriceDlg.pack();
        aptPriceDlg.setResizable(false);
        aptPriceDlg.setVisible(true);
        aptPriceDlg.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                aptPriceDlg.dispose();
                aptPriceDlg.setVisible(false);
            }
        });
    }

    public class sidoDongSearch {
        private JDialog sidoDlg;
        private JFrame sidoFrame;
        private final String[] sidoList = {"서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"};
        private JPanel sidoPanel;
        private final String[] seoulJonro = {"종로구 청운동", "종로구 신교동"};
        private final String[] busan = {"부산진구 부전동", "부산진구 양정동"};
        private boolean check;

        //https://ko.wikipedia.org/wiki/%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD%EC%9D%98_%ED%96%89%EC%A0%95_%EA%B5%AC%EC%97%AD 를 보고 만듦
        public sidoDongSearch() {
            check = false;
            sidoFrame = new JFrame();
            sidoDlg = new JDialog(sidoFrame, "시도를 선택하세요", Dialog.ModalityType.APPLICATION_MODAL);
            sidoDlg.setLayout(new GridBagLayout());
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 4;
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            JLabel text = new JLabel("시도를 선택하세요!");
            sidoPanel = new JPanel();
            sidoPanel.setLayout(new GridBagLayout());
            sidoPanel.setBorder(BorderFactory.createTitledBorder("시/도"));
            sidoDlg.add(text, gridBagConstraints);
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            for (int i = 0; i < sidoList.length; i++) {
                if (i % 3 == 0) {
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy++;
                }
                JButton sido = new JButton(sidoList[i]);
                int finalI = i;
                sido.addActionListener(e -> {
                    curSelectedItem = sidoList[finalI];
                    sidoName = curSelectedItem;
                    text.setText(curSelectedItem + "  (읍,면,동)을 입력하세요!");
                    sidoPanel.removeAll();
                    sidoDlg.remove(sidoPanel);
                    sidoDlg.getContentPane().revalidate();
                    sidoDlg.getContentPane().repaint();
                    JTextField dongInput = new JTextField();
                    dongInput.setColumns(10);
                    GridBagConstraints gridBagConstraints = new GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    sidoDlg.getContentPane().add(dongInput, gridBagConstraints);
                    JButton search = new JButton();
                    search.setText("검색");//그려준 거에 이미지 돋보기 모양인데 그거 할려면 search.setIcon();
                    gridBagConstraints.gridx = 1;
                    sidoDlg.getContentPane().add(search, gridBagConstraints);
                    search.addActionListener(e1 -> {

                        input = dongInput.getText();
                        if (curSelectedItem.contains("서울") && !check) {
                            
                        }
                        if (curSelectedItem.contains("부산") && !check) {
                            
                        }
//                        if (!check) {
//                            JOptionPane.showMessageDialog(null, "동을 다시 확인해주세요", "오류", JOptionPane.INFORMATION_MESSAGE);
//                        } else {
                        	ps = new ExcelPs(input,sidoName);
                        	
                     //       System.out.println("목록에 있다");
                            sidoDlg.dispose();
                            sidoDlg.setVisible(false);
                            StringBuilder stringBuilder = new StringBuilder(curSelectedItem);
                            stringBuilder.append(" ").append(input);
                            curSelectedItem = stringBuilder.toString();
                            sidoSelectButton.setText(curSelectedItem);
                       // }
                    });
                });
                sido.setPreferredSize(new Dimension(140, 20));
                sidoPanel.add(sido, gridBagConstraints);
                gridBagConstraints.gridx++;
            }
            sidoDlg.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    sidoDlg.dispose();
                    sidoDlg.setVisible(false);
                }
            });
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridwidth = 1;
            sidoDlg.getContentPane().add(sidoPanel, gridBagConstraints);
            sidoDlg.pack();
            sidoDlg.setResizable(false);
            sidoDlg.setVisible(true);
            
        }
    }
    private class SelectPage {
        private JDialog selectDlg;
        private JFrame selectFrame;
        private JButton parsingAptButton;
        private JButton parsingOftButton;
        private JButton parsingRhtButton;
        private JButton parsingShtButton;
        private GridBagConstraints gridBagConstraints;

        private SelectPage() {
            selectFrame = new JFrame("");
            selectDlg = new JDialog(selectFrame, "기능을 선택하세요", Dialog.ModalityType.APPLICATION_MODAL);
            selectDlg.getContentPane().setLayout(new GridBagLayout());
            gridBagConstraints = new GridBagConstraints();
            parsingAptButton = new JButton("아파트");//아파트버튼
            parsingAptButton.setPreferredSize(new Dimension(100, 100));
            parsingOftButton = new JButton("오피스텔");
            parsingOftButton.setPreferredSize(new Dimension(100, 100));
            parsingRhtButton = new JButton("연립 다세대");
            parsingRhtButton.setPreferredSize(new Dimension(100, 100));
            parsingShtButton = new JButton("단독/다가구");
            parsingShtButton.setPreferredSize(new Dimension(100, 100));
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            selectDlg.getContentPane().add(parsingAptButton, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            selectDlg.getContentPane().add(parsingShtButton, gridBagConstraints);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            selectDlg.getContentPane().add(parsingRhtButton, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            selectDlg.getContentPane().add(parsingOftButton, gridBagConstraints);
            parsingAptButton.addActionListener(e -> {//아파트버튼 눌렀을때 이벤트 처리
                String data = "아파트 검색" + "\n" + "주소 : " + curSelectedItem + "\n" +
                        "날짜 : " + curSelectedDate +"\n" + ps.areaCode + "\n" + ps.areaName ;
                //data라는 String형 변수에 파싱한데이터를 저장
                //curSelectedItem 현재 선택된 항목 주소
                //curSelectedDate 현재 선택된  항목 날짜
                System.out.println(ps.areaCode); //지역코드 파싱한 데이터 출력(확인용)
                System.out.println(ps.areaName); //지역이름 파싱한 데이터 출력(확인용)
                client.send(data);//데이터를 클라이언트에 보낸다
            });
            parsingOftButton.addActionListener(e -> {//오피스텔 버튼 눌렀을때
                String data = "오피스텔 검색" + "\n" + "주소 : " + curSelectedItem + "\n" +
                        "날짜 : " + curSelectedDate +"\n" + ps.areaCode + "\n" + ps.areaName ;
                client.send(data);
            });
            parsingRhtButton.addActionListener(e -> {//연립다세대 버튼 눌렀을때
                String data = "연립다세대 검색" + "\n" + "주소 : " + curSelectedItem + "\n" +
                        "날짜 : " + curSelectedDate +"\n" + ps.areaCode + "\n" + ps.areaName ;
                client.send(data);
            });
            parsingShtButton.addActionListener(e -> {
                String data = "단독다가구 검색" + "\n" + "주소 : " + curSelectedItem + "\n" +
                        "날짜 : " + curSelectedDate +"\n" + ps.areaCode + "\n" + ps.areaName ;
                client.send(data);
            });
            selectDlg.pack();
            selectDlg.setResizable(false);
            selectDlg.setVisible(true);
        }
    }
    
}
