package server;
import java.io.DataInputStream;
import parsing.CategoryParsing;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 클라이언트가 보내온 단어를 수신하고 다시 보내주는 서버 클래스.
 * @author 
 */
public class TcpEchoServer {
	private int serverPort = 8888;//서버에서 사용하는 포트번호
	private int count=0;//서버 종료 카운터
	/**
	 * 에코 서버 메인 메소드 : 수신한 입력 스트림을 그대로 출력 스트림을 통하여 보내준다.
	 * 
	 */
	
	final Logger logger = Logger.getLogger(TcpEchoServer.class.getName());
	public void go() {
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSock = new ServerSocket(serverPort); //서버 소켓 생성
			logger.info("서버 소켓 생성!");
			while(count<30) { // 클라이언트가 30번 연결되면 서버 종료
				Socket clientSock = serverSock.accept(); // 클라이언트 연결 요청 허용 
				count+=1; // 클라이언트가 연결될 때마다 카운트 증가
				logger.info("클라이언트 IP주소 : " + clientSock.getInetAddress().getHostAddress() + " 포트 번호 : " + clientSock.getPort() + "\n");
				
				InputStream is = clientSock.getInputStream();		 // InputStream, DataInputStream 가져오기 	
				DataInputStream dis = new DataInputStream(is);
				CategoryParsing s = new CategoryParsing(26410,201806,dis.readUTF().toString());
				ArrayList<StringBuilder>DataList = new ArrayList<StringBuilder>();
				for(StringBuilder a : DataList) {
					System.out.println(a);
				}
				dis.close(); 
				is.close();
				clientSock.close(); // 클라이언트와 연결 종료
				logger.info("----- 클라이언트 소켓 종료 -----\n\n");
				//System.out.println("----- 클라이언트 소켓 종료 -----\n\n");
			}
		}catch (IOException ex) {
			Logger.getLogger(TcpEchoServer.class.getName()).log(Level.SEVERE,"예외 발생",ex);
		}finally {
			logger.info("----- 서버 소켓 종료 -----\n\n");
		}
	}
}