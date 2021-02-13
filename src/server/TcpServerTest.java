package server;

public class TcpServerTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //TcpEchoServer server = new TcpEchoServer(); // TcpEchoServer 생성
        //server.go(); // TcpEchoServer 시작
        Server server = new Server();
        server.startServer();
    }

}
