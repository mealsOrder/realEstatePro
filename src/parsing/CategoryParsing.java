package parsing;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CategoryParsing {
    private int reignCode; // 지역코드 (엑셀파싱해야함!)
    private int dealMonth; // 계약월
    private String cateGory;

    public CategoryParsing(int reignCode, int dealMonth, String cateGory) {
        this.reignCode = reignCode;
        this.dealMonth = dealMonth;
        this.cateGory = cateGory;
    }

    final Logger logger = Logger.getLogger(CategoryParsing.class.getName());// logger를 사용하기 위해서 선언
    ArrayList<StringBuilder> DataList = new ArrayList<StringBuilder>(); //종류별 매매거래가를 저장하기 위한 ArrayList 선언
	/*
	public ArrayList<StringBuilder> run() throws IOException  {
		LoadApi lap = new LoadApi(reignCode, dealMonth, cateGory); //예제로 넣어놓음.
		lap.setUrl();
		lap.setReadResult();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			InputSource is = new InputSource(new StringReader(lap.readResults.toString()));

			DocumentBuilder builder = factory.newDocumentBuilder(); //
			Document doc = builder.parse(is); //
			XPathFactory xpathFactory = XPathFactory.newInstance(); // xPath는 따로 공부해보자
			XPath xpath = xpathFactory.newXPath(); // 아직 이해단계
			XPathExpression expr = xpath.compile("//body/items/item");
			NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET); // 노드를 따라 가게 하는 애
			

			for (int i = 0; i < nodeList.getLength(); i++) {
				NodeList child = nodeList.item(i).getChildNodes();
				StringBuilder resultBuilding = new StringBuilder("\n"); // 파싱결과를 콘솔에 넣기위해 만들어준 객체
				resultBuilding.setLength(0); // StringBulider 를 초기화
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

					}

					else if (node.getNodeName().contains("건축년도")) { // item 으로 둘러쌓인 부분을 반복
						// contains 와 equal 사용 고려
						String temp = node.getTextContent();
						temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
						resultBuilding.append("건축년도 : ");
						resultBuilding.append(temp);
						resultBuilding.append("년");
						resultBuilding.append("\n");// 줄바꿈

					}

					else if (node.getNodeName().contains("법정동")) { // item 으로 둘러쌓인 부분을 반복

						String temp = node.getTextContent();
						temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
						resultBuilding.append("지역구 : ");
						resultBuilding.append(temp);
						resultBuilding.append("\n");// 줄바꿈

					}

					else if (node.getNodeName().contains("아파트")) { // item 으로 둘러쌓인 부분을 반복
						String temp = node.getTextContent();
						temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
						resultBuilding.append("이름 : ");
						resultBuilding.append(temp);
						resultBuilding.append(" 아파트");
						resultBuilding.append("\n");// 줄바꿈

					}

					else if (node.getNodeName().contains("전용면적")) { // item 으로 둘러쌓인 부분을 반복
						// contains 와 equal 사용 고려
						String temp = node.getTextContent();
						temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
						resultBuilding.append("평수 : ");
						double area = (Double.parseDouble(temp) * 0.3025f); // String을 double로 변환 후 제곱미터를 평수로 변환
						double resarea = Double.parseDouble(String.format("%.2f", area)); // 소숫점 2번째까지만 출력하도록 형식 변환
						resultBuilding.append(resarea + "평");
						resultBuilding.append("\n");// 줄바꿈
					}
					else if (node.getNodeName().contains("지번")) { // item 으로 둘러쌓인 부분을 반복
						String temp = node.getTextContent();
						temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
						resultBuilding.append("지번 : ");
						resultBuilding.append(temp);
						resultBuilding.append("번지");
						resultBuilding.append("\n");// 줄바꿈

					}
					else if (node.getNodeName().contains("층")) { // item 으로 둘러쌓인 부분을 반복
						String temp = node.getTextContent();
						temp = temp.trim(); // 문자열의 앞 뒤 공백 제거 함수
						resultBuilding.append("층수 : ");
						resultBuilding.append(temp);
						resultBuilding.append("층");
						resultBuilding.append("\n");// 줄바꿈

					}					
				}
				resultBuilding.append("\n"); // 줄바꿈
				DataList.add(resultBuilding); //ArrayList에 resultBuilding의 내용을 저장.
			}
		} catch (UnsupportedEncodingException e) {
			logger.warning("UnsupportedEncodingException");
		} catch (IOException e) {
			logger.warning("IOException");
		} catch (XPathExpressionException e) {
			logger.warning("XPathExpressionException");
		} catch (ParserConfigurationException e) {
			logger.warning("ParserConfigurationException");
		} catch (SAXException e) {
			logger.warning("SAXException");
		}
		for(StringBuilder a : DataList) {
			if(a.toString().contains("양정동")) {
			System.out.println(a);
			}
		}
		return DataList; //아파트 정보를 리턴
	}
	*/
}