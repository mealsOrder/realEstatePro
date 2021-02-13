package login;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ShtResultFrame {
    private JDialog aptdlg;
    private JFrame aptframe;
    private StringBuilder[] resultList;

    private int curPage;
    private int totalPage;
    private int totalCount;
    private GridBagConstraints gridBagConstraints;
    private JButton prevButton;
    private JButton nextButton;

    public ShtResultFrame(String allReuslt) {
        aptframe = new JFrame();
        aptdlg = new JDialog(aptframe, "단독다가구 검색 결과", Dialog.ModalityType.APPLICATION_MODAL);
        aptdlg.getContentPane().setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();

        curPage = 1;
        String[] temp = allReuslt.split("\\n+");
        resultList = new StringBuilder[temp.length / 6];
        totalCount = resultList.length;
        if (resultList.length % 4 == 0) {
            totalPage = resultList.length / 4;
        } else {
            totalPage = resultList.length / 4 + 1;
        }
        for (int i = 1; i < resultList.length; i++) {
            resultList[i - 1] = new StringBuilder("<html>");
            for (int j = 0; j < 6; j++) {
                resultList[i - 1].append(temp[i * 6 + j]);
                resultList[i - 1].append("<br />");
            }
            resultList[i - 1].append("</html>");
        }
        prevButton = new JButton("<");
        nextButton = new JButton(">");
        prevButton.addActionListener(e -> {
            newPage(curPage - 1);
        });
        nextButton.addActionListener(e -> {
            newPage(curPage + 1);
        });
        newPage(1);
        aptdlg.setSize(450, 500); //사이즈
        aptdlg.setResizable(false);
        aptdlg.setVisible(true);
    }

    private void newPage(int pageNum) {
        curPage = pageNum;
        aptdlg.getContentPane().removeAll();

        JLabel totalCounting = new JLabel("총 결과 수" + String.valueOf(totalCount));
        JLabel totalPaging = new JLabel("총 페이지 수" + String.valueOf(totalPage));
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        aptdlg.getContentPane().add(totalCounting, gridBagConstraints);
        gridBagConstraints.gridx = 5;
        aptdlg.getContentPane().add(totalPaging, gridBagConstraints);
        JButton[] pages = new JButton[0];
        int counter = (pageNum - 1) / 4;
        if (curPage == 1)
            prevButton.setEnabled(false);
        else
            prevButton.setEnabled(true);
        if (curPage == totalPage)
            nextButton.setEnabled(false);
        else
            nextButton.setEnabled(true);

        if ((totalPage - 1) / 4 > (pageNum - 1) / 4) {
            pages = new JButton[4];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = new JButton(String.valueOf(1 + i + counter * 4));
                if (curPage == 1 + i + counter * 4)
                    pages[i].setEnabled(false);
                else
                    pages[i].setEnabled(true);
                int finalI = i;
                pages[i].addActionListener(e -> newPage(1 + finalI + counter * 4));
            }
        } else if ((totalPage - 1) / 4 == (pageNum - 1) / 4) {
            pages = new JButton[totalPage % 4];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = new JButton(String.valueOf(1 + i + counter * 4));
                if (curPage == 1 + i + counter * 4)
                    pages[i].setEnabled(false);
                else
                    pages[i].setEnabled(true);
                int finalI = i;
                pages[i].addActionListener(e -> newPage(1 + finalI + counter * 4));
            }
        }
        switch (pages.length) {
            case 0:
                //결과가 전혀 없을 경우 처리하고 싶으면 여기를 건드리면 됨
                break;
            case 1:
                gridBagConstraints.gridy = 9;
                gridBagConstraints.gridx = 1;
                aptdlg.getContentPane().add(pages[0], gridBagConstraints);
                break;
            case 2:
                gridBagConstraints.gridy = 9;
                gridBagConstraints.gridx = 1;
                aptdlg.getContentPane().add(pages[0], gridBagConstraints);
                gridBagConstraints.gridx = 2;
                aptdlg.getContentPane().add(pages[1], gridBagConstraints);
                break;
            case 3:
                gridBagConstraints.gridy = 9;
                gridBagConstraints.gridx = 1;
                aptdlg.getContentPane().add(pages[0], gridBagConstraints);
                gridBagConstraints.gridx = 2;
                aptdlg.getContentPane().add(pages[1], gridBagConstraints);
                gridBagConstraints.gridx = 3;
                aptdlg.getContentPane().add(pages[2], gridBagConstraints);
                break;
            case 4:
                gridBagConstraints.gridy = 9;
                gridBagConstraints.gridx = 1;
                aptdlg.getContentPane().add(pages[0], gridBagConstraints);
                gridBagConstraints.gridx = 2;
                aptdlg.getContentPane().add(pages[1], gridBagConstraints);
                gridBagConstraints.gridx = 3;
                aptdlg.getContentPane().add(pages[2], gridBagConstraints);
                gridBagConstraints.gridx = 4;
                aptdlg.getContentPane().add(pages[3], gridBagConstraints);
                break;
            default:
                //심각한 버그가 발생해서 페이지가 -이거나 5이상일 경우
                break;
        }
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridy = 1;
        JLabel[] result = new JLabel[4];
        JPanel[] groupBox = new JPanel[4];
        for (int i = 0; i < result.length; i++) {
            if (totalCount <= i + (curPage - 1) * 4) {
                break;
            }
            if (resultList[i + (curPage - 1) * 4] != null) {
                groupBox[i] = new JPanel();
                groupBox[i].setBorder(BorderFactory.createTitledBorder(""));
                result[i] = new JLabel(resultList[i + (curPage - 1) * 4].toString());
                result[i].setPreferredSize(new Dimension(400, 120)); //사이즈
                groupBox[i].add(result[i]);
            }
        }
        aptdlg.getContentPane().add(groupBox[0], gridBagConstraints);
        gridBagConstraints.gridy = 2;
        if (result[1] != null)
            aptdlg.getContentPane().add(groupBox[1], gridBagConstraints);

        gridBagConstraints.gridy = 3;
        if (result[2] != null)
            aptdlg.getContentPane().add(groupBox[2], gridBagConstraints);
        gridBagConstraints.gridy = 4;
        if (result[3] != null)
            aptdlg.getContentPane().add(groupBox[3], gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        aptdlg.getContentPane().add(prevButton, gridBagConstraints);
        gridBagConstraints.gridx = 5;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        aptdlg.getContentPane().add(nextButton, gridBagConstraints);
        aptdlg.getContentPane().revalidate();
        aptdlg.getContentPane().repaint();

    }
}
