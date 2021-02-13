package client;

import java.util.logging.Logger;

public class TcpClientTest {
    static final Logger logger = Logger.getLogger(TcpClientTest.class.getName());//static 함수로 logger 선언

    public static void main(String[] args) {

        //TcpEchoClient client = new TcpEchoClient();
        //client.go();
        //logger.info("클라이언트 수행 종료!");
        //System.out.println("클라이언트 수행 종료!");(수정 전코드)
        Client client = new Client();
        client.startClient();
    }
}
