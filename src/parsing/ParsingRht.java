package parsing;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author 장윤재
 * 연립다세대 매매거래 정보를 분류하는 클래스
 * @since 2018년 12월 02일
 */
public class ParsingRht extends ParsingBase {
    public ParsingRht(String reignCode, String dealMonth, String area) {
        cateGory = "RHTrade";
        this.reignCode = reignCode;//reignCode랑 dealMonth를 int에서 String으로 변경했음
        this.dealMonth = dealMonth;
        this.area = area;
        run();
    }

    public void contents(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList child = nodeList.item(i).getChildNodes();
            // resultBuilding.setLength(0); // StringBulider 를 초기화
            for (int j = 0; j < child.getLength(); j++) { // items 으로 둘러쌓인 부분을 반복
                Node node = child.item(j);

                if (node.getNodeName().contains("거래금액")) { // item 으로 둘러쌓인 부분을 반복
                    // contains 와 equal 사용 고려
                    String temp = node.getTextContent();
                    temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수(공백문제 해결)
                    resultBuilding.append("거래금액 : ");
                    resultBuilding.append(temp);
                    resultBuilding.append("만원");
                    resultBuilding.append("\n");// 줄바꿈

                } else if (node.getNodeName().contains("건축년도")) { // item 으로 둘러쌓인 부분을 반복
                    // contains 와 equal 사용 고려
                    String temp = node.getTextContent();
                    temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
                    resultBuilding.append("건축년도 : ");
                    resultBuilding.append(temp);
                    resultBuilding.append("년");
                    resultBuilding.append("\n");// 줄바꿈

                } else if (node.getNodeName().contains("대지권면적")) { // item 으로 둘러쌓인 부분을 반복
                    // contains 와 equal 사용 고려
                    String temp = node.getTextContent();
                    temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
                    resultBuilding.append("평수 : ");
                    double area = (Double.parseDouble(temp) * 0.3025f); // String을 double로 변환 후 제곱미터를 평수로 변환
                    double resarea = Double.parseDouble(String.format("%.2f", area)); // 소숫점 2번째까지만 출력하도록 형식 변환
                    resultBuilding.append(resarea + "평");
                    resultBuilding.append("\n");// 줄바꿈
                } else if (node.getNodeName().contains("법정동")) { // item 으로 둘러쌓인 부분을 반복

                    String temp = node.getTextContent();
                    temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
                    resultBuilding.append("지역구 : ");
                    resultBuilding.append(temp);
                    resultBuilding.append("\n");// 줄바꿈

                } else if (node.getNodeName().contains("연립다세대")) { // item 으로 둘러쌓인 부분을 반복
                    String temp = node.getTextContent();
                    temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
                    if(temp.equals(area)) {
                    	chk=1;
                    }
                    resultBuilding.append("이름 : ");
                    resultBuilding.append(temp);
                    resultBuilding.append("\n");// 줄바꿈

                } else if (node.getNodeName().contains("전용면적")) { // item 으로 둘러쌓인 부분을 반복
                    // contains 와 equal 사용 고려
                    String temp = node.getTextContent();
                    temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
                    resultBuilding.append("전용면적 : ");
                    resultBuilding.append("평수 : ");
                    double area = (Double.parseDouble(temp) * 0.3025f); // String을 double로 변환 후 제곱미터를 평수로 변환
                    double resarea = Double.parseDouble(String.format("%.2f", area)); // 소숫점 2번째까지만 출력하도록 형식 변환
                    resultBuilding.append(resarea + "평");
                    resultBuilding.append("\n");// 줄바꿈
                } else if (node.getNodeName().contains("지번")) { // item 으로 둘러쌓인 부분을 반복
                    String temp = node.getTextContent();
                    temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
                    resultBuilding.append("지번 : ");
                    resultBuilding.append(temp);
                    resultBuilding.append("번지");
                    resultBuilding.append("\n");// 줄바꿈

                } else if (node.getNodeName().contains("층")) { // item 으로 둘러쌓인 부분을 반복
                    String temp = node.getTextContent();
                    temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
                    resultBuilding.append("층수 : ");
                    resultBuilding.append(temp);
                    resultBuilding.append("층");
                    resultBuilding.append("\n");// 줄바꿈

                }
                
            }
            if(chk == 1) {
            	result += resultBuilding.toString();
            	chk=0;
            }
        }
    }
}
