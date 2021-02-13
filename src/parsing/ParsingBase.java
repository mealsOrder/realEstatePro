package parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

/**
 * API에 데이터를 요청하기 위해 Url을 만들고 종류별 파싱을 명령하는 클래스
 * @author 장윤재 
 * @since 2018년 12월 02일
 * 
 * 코드정리 및 주석추가
 * @author 장윤재
 * @since 2021년 2월 13일
 */
public abstract class ParsingBase {
	public int chk = 0;
	protected String reignCode; // 지역코드 (엑셀파싱해야함!)
	protected String dealMonth; // 계약월
	protected String area; // 법정동
	protected String cateGory = ""; // 종류
	protected String serviceKey = "qraPJGOOhKQsPxMhfBldnbPyXNDlkHr5lClWU2HYOR1Ww5CcLG4YBPq%2BeBcYUmcYEJr%2BvXZM3LKFDZmRQ73u3A%3D%3D"; // 서비스
																																	  // 키
	protected String priceList = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvc"; // URL짤라
																																		// 붙일때필요한
																																		// 스트링형
																																		// 변수
																																		// >>
																																		// 설명이
																																		// 애매해서
																																		// 일단
																																		// 이렇게
																																		// 설명해놓음
	protected String result = "";// 결과를 저장할 변수

	final Logger logger = Logger.getLogger(ParsingBase.class.getName());// logger를 사용하기 위해서 선언
	// URL 만들어주는 객체
	StringBuilder urlMaker = new StringBuilder("");// ""에 스트링형 객체 넣어줘야함
	StringBuilder readResults = new StringBuilder("");
	StringBuilder resultBuilding = new StringBuilder("\n");
	StringBuilder lastBuilding = new StringBuilder("\n");
	
	/**
	 * @brief URL을 만드는 함수
	 */
	public void setUrl() {
		urlMaker.append(priceList); // 시작 URL
		urlMaker.append(cateGory); // 아파트,단독/다가구,연립다세대의 종류를 선택
		urlMaker.append("?LAWD_CD=");
		urlMaker.append(reignCode); // int형으로 선언해준 지역코드을 string형으로 형 변환해준다.
		// --int형을 굳이 하지 말고 String하자!
		// 굳이 int형으로 받아서 String 변환할 이유가 안 보인다
		urlMaker.append("&DEAL_YMD=");
		urlMaker.append(dealMonth);
		urlMaker.append("&ServiceKey=");
		urlMaker.append(serviceKey);
		System.out.println(urlMaker);
	}

	/**
	 * @brief URL의 정보를 전부 받아서 공백을 없애고 저장하는 함수
	 */
	public void setReadResult() {
		try {
			URL url = new URL(urlMaker.toString());
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));//

			readResults.append(br.readLine()); // 기존 string배열로 반복문을 사용하여 받지 않고 stringBulider 객체를 사용하여 바로 추가하였음.
		} catch (MalformedURLException e) { // URL이 잘못되었을 경우 예외처리
			logger.warning("MalformedURLException");
		} catch (IOException e) { // 어떤 오류가 발생했을 경우 예외처리
			// TODO Auto-generated catch bloFck
			logger.warning("오류 발생");
		}
		// System.out.println(readResults);
	}

	public abstract void contents(NodeList nodelist);

	public void run() {
		setUrl();
		setReadResult();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			InputSource is = new InputSource(new StringReader(readResults.toString()));

			DocumentBuilder builder = factory.newDocumentBuilder(); //
			Document doc = builder.parse(is); //
			XPathFactory xpathFactory = XPathFactory.newInstance(); // xPath는 따로 공부해보자
			XPath xpath = xpathFactory.newXPath(); // 아직 이해단계
			XPathExpression expr = xpath.compile("//body/items/item");
			NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET); // 노드를 따라 가게 하는 애

			contents(nodeList);

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
	}

	// 결과 획득 함수
	public String getResult() {
		return result;
	}
}
