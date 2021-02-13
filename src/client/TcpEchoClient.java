package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 간단한 단어를 서버로 보내고 받는 에코 서비스를 수행하는 클라이언트 클래스
 * 
 * Copyright 2021. 장윤재(MealsOrder). All rights reserved.
 * 
 * @author "MealsOrder"
 * @version
 * @since 2021-02-13
 *
 */
public class TcpEchoClient {
	// 서버 주소 또는 이름
	private String server = null;
	
	// 서버의 포트 번호
	private int serverPort = 8888;

	final Logger logger = Logger.getLogger(TcpEchoClient.class.getName());

	/**
	 * 소켓 생성하여 서버로 word 전송하고 받아 보는 메인 루틴
	 * 
	 * @Methods go()
	 * @author "MealsOrder"
	 * @since 2021-02-13
	 * 
	 */
	public void go() {
		/** 보낼 단어 : 공백 없이 하나의 단어만 전송 가능 */
		String areaClient = "";
		try {
			Socket socket = new Socket(server, serverPort); // 클라이언트 소켓 생성
			logger.info("소켓 생성 성공");
			@SuppressWarnings("resource")
			java.util.Scanner sc = new java.util.Scanner(System.in);
			areaClient = sc.nextLine();
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(areaClient);
			dos.flush();
			dos.close();
			os.close();
			logger.info("송신 완료");
			socket.close();
		} catch (IOException ex) {
			Logger.getLogger(TcpEchoClient.class.getName()).log(Level.SEVERE, "go() 메소드 오류 발생", ex);
		}
	}
}