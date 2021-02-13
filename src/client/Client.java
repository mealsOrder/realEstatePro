package client;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import login.AptResultFrame;

import login.FirstFrame;
import login.LoginFrame;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import login.RhtResultFrame;
import login.ShtResultFrame;

public class Client {
    //private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private SocketChannel socketChannel;
    private String data;
    private LoginFrame loginFrame = new LoginFrame(this);

    public void startClient() {
        loginFrame.runLoginFrame();


        Thread thread = new Thread(() -> {
            try {
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(true);
                socketChannel.connect(new InetSocketAddress("localhost", 5000));
                //logger.info("[ 연결 완료 : {} ]", socketChannel.getRemoteAddress());
            } catch (IOException e) {
                //logger.error("[서버 통신 안 됨]");
                if (socketChannel.isConnected()) {
                    stopClient();
                    System.exit(0);
                }
                return;
            }
            receive();
        });
        thread.start();
    }

    public void stopClient() {
        try {
            //logger.info("연결 끊음");
            if (socketChannel != null && socketChannel.isOpen()) {
                //logger.warn("클라이언트를 종료한다~~~~");
                socketChannel.close();
                //logger.info("잘 종료됐다!");
            }
        } catch (IOException e) {
            //logger.warn("클라이언트를 멈추는 중 IOException이 발생했다!");
        	//test
        }

    }

    public void receive() {
        while (true) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(100000);
                int readByteCount = socketChannel.read(byteBuffer);
                if (readByteCount == -1) {
                    throw new IOException();
                }
                byteBuffer.flip();
                Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                data = charset.decode(byteBuffer).toString();
                //logger.info("클라이언트 받기 완료 : {}", data);
                if (data.contains("로그인")) {
                    if (data.equals("로그인 성공!")) {
                        loginFrame.disposeLoginFrame();
                        FirstFrame firstFrame = new FirstFrame(this);
                    } else if (data.equals("로그인 실패!")) {
                        loginFrame.loginFailed();
                    }
                }
                if (data.contains("클라이언트 종료")) {
                    stopClient();
                    break;
                }
                if (data.contains("이메일로 아이디")) {
                    if (data.contains("실패")) {
                        loginFrame.findIDframeShowMessage("아이디 찾기 실패");
                    } else {
                        loginFrame.findIDframeShowMessage("아이디는 " + data.split("아이디는 ")[1] + "입니다.");
                        loginFrame.findIDframedispose();
                    }
                }
                if (data.contains("휴대폰 번호로 아이디")) {
                    if (data.contains("실패")) {
                        loginFrame.findIDframeShowMessage("아이디 찾기 실패");
                    } else {
                        loginFrame.findIDframeShowMessage("아이디는 " + data.split("아이디는 ")[1] + "입니다.");
                        loginFrame.findIDframedispose();
                    }
                }
                if (data.contains("비밀번호")) {
                    if (data.contains("실패")) {
                        loginFrame.findIDframeShowMessage("비밀번호 찾기 실패");
                    } else {
                        loginFrame.findIDframeShowMessage("비밀번호는 " + data.split("비밀번호는 ")[1] + "입니다.");
                        loginFrame.findIDframedispose();
                    }
                }

                if(data.contains("아파트 가격 결과")){
                    System.out.println(data);
                    new AptResultFrame(data);
                }
                if(data.contains("오피스텔 가격 결과")){
                	System.out.println(data);
                    new AptResultFrame(data);
                }
                if(data.contains("연립다세대 가격 결과")){
                	System.out.println(data);
                    new RhtResultFrame(data);
                }
                if(data.contains("단독다가구 가격 결과")){
                	System.out.println(data);
                    new ShtResultFrame(data);
                }
            } catch (IOException e) {
                //logger.error("서버 통신 안 됨!!");
                stopClient();
                break;
            }
        }
    }


    public void send(String data) {
        System.out.println(data);
        Thread thread = new Thread(() -> {
            try {
                Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                ByteBuffer byteBuffer = charset.encode(data);
                socketChannel.write(byteBuffer);
                //logger.info("데이터 보내기 완료 : {}", data);
            } catch (IOException e) {
                //logger.error("서버 통신 안 됨");
                stopClient();
            }
        });
        thread.start();

    }
}
