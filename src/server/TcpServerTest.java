package server;
/**
 * TCP서버 실행 클래스.
 * 
 * Copyright 2021. 장윤재(MealsOrder). All rights reserved.
 * 
 * @author "MealsOrder"
 * @version
 * @since 2021-02-13
 *
 */

public class TcpServerTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //TcpEchoServer server = new TcpEchoServer(); // TcpEchoServer 생성
        //server.go(); // TcpEchoServer 시작
        Server server = new Server();
        server.startServer();
    }

}
