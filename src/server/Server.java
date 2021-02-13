package server;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import parsing.ParsingApt;
import parsing.ParsingOft;
import parsing.ParsingRht;
import parsing.ParsingSht;


public class Server {
    private ExecutorService executorService;
    private ServerSocketChannel serverSocketChannel;
    private List<Client> connections = new LinkedList<>();
    //private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private Scanner databaseScanner;
    private FileWriter databaseWriter;
    private final String databasePath = "./src//server//database.txt";
    private File database = new File(databasePath);
    public int areaCode;
    
    private class Client {
    	
        SocketChannel socketChannel;


        private Client(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;


            receive();
        }

        private synchronized void receive() {
            Runnable runnable = () -> {
                while (!Thread.interrupted()) {
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(100000);
                        int readByteCount = socketChannel.read(byteBuffer);
                        if (readByteCount == -1) {
                            throw new IOException();
                        }

                        //logger.info("[ 요청 처리 {} : {} ]", socketChannel.getRemoteAddress(), Thread.currentThread().getName());
                        byteBuffer.flip();
                        Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                        String data = charset.decode(byteBuffer).toString();
                        //logger.info(data);

                        if (data.contains("서버 종료")) {
                            stopServer();
                            break;
                        }
                        if (data.contains("로그인")) {
                            String result = "";
                            String id = data.split("\\n+")[1];
                            String pw = data.split("\\n+")[2];
                            System.out.println(id);
                            System.out.println(pw);
                            databaseScanner = new Scanner(database);
                            while (true) {
                                if (databaseScanner.hasNextLine()) {
                                    String inform = databaseScanner.nextLine();
                                    String[] informParts = inform.split("\\|\\|");
                                    String informID = informParts[2].split("id : ")[1];
                                    String informPW = informParts[3].split("pw : ")[1];
                                    System.out.println(informID);
                                    System.out.println(informPW);
                                    if (informID.equals(id) && informPW.equals(pw)) {
                                        result = "로그인 성공!";
                                        break;
                                    }

                                } else {
                                    result = "로그인 실패!";
                                    break;
                                }
                            }
                            this.send(result);
                            databaseScanner.close();
                        }
                        System.out.println(data);
                        if (data.contains("회원가입")) {
                            if (database.exists()) {
                                databaseWriter = new FileWriter(databasePath, true);
                                databaseWriter.write(System.getProperty("line.separator"));
                            } else {
                                databaseWriter = new FileWriter(databasePath);
                            }
                            String[] parts = data.split("\\n" + "+");
                            for (int i = 1; i < parts.length; i++) {
                                databaseWriter.write(parts[i]);
                                databaseWriter.write("||");

                            }
                            if (databaseWriter != null)
                                databaseWriter.close();

                        }
                        if (data.contains("이메일로 아이디 찾기")) {
                            String result = "";
                            String[] parts = data.split("\\n+");//[0]은 이메일로 아이디 찾기
                            String name = parts[1].split("\uc774\ub984 : ")[1];//이름
                            String birth = parts[2].split("\uc0dd\ub144\uc6d4\uc77c : ")[1];//생년월일
                            String email = parts[3].split("\uc774\uba54\uc77c : ")[1];
                            databaseScanner = new Scanner(database);
                            while (true) {
                                if (databaseScanner.hasNextLine()) {
                                    String inform = databaseScanner.nextLine();
                                    String[] informParts = inform.split("\\|\\|");
                                    if (name.equals(informParts[0].split("\uc774\ub984 : ")[1]) && birth.equals(informParts[1].split("\uc0dd\ub144\uc6d4\uc77c : ")[1]) && email.equals(informParts[5].split("email : ")[1])) {
                                        StringBuilder stringBuilder = new StringBuilder("이메일로 아이디 찾기의 아이디는 ");
                                        stringBuilder.append(informParts[2].split("id : ")[1]);
                                        result = stringBuilder.toString();
                                        break;
                                    }
                                } else {
                                    result = "이메일로 아이디 찾기 실패";
                                    break;
                                }
                            }
                            databaseScanner.close();
                            this.send(result);
                        }
                        if (data.contains("휴대폰 번호로 아이디 찾기")) {
                            String result = "";
                            String[] parts = data.split("\\n+");
                            String name = parts[1].split("\uc774\ub984 : ")[1];//이름 :
                            String birth = parts[2].split("\uc0dd\ub144\uc6d4\uc77c : ")[1];//생년월일 :
                            String phoneNumber = parts[3].split("\ud734\ub300\ud3f0 \ubc88\ud638 : ")[1];//휴대폰 번호 :
                            databaseScanner = new Scanner(database);
                            while (true) {
                                if (databaseScanner.hasNextLine()) {
                                    String inform = databaseScanner.nextLine();
                                    String[] informParts = inform.split("\\|\\|");
                                    if (name.equals(informParts[0].split("\uc774\ub984 : ")[1]) && birth.equals(informParts[1].split("\uc0dd\ub144\uc6d4\uc77c : ")[1]) && phoneNumber.equals(informParts[4].split("휴대폰 번호")[1])) {
                                        StringBuilder stringBuilder = new StringBuilder("휴대폰 번호로 아이디 찾기의 아이디는 ");
                                        stringBuilder.append(informParts[2].split("id : ")[1]);
                                        result = stringBuilder.toString();
                                        break;
                                    }
                                } else {
                                    result = "휴대폰 번호로 아이디 찾기 실패";
                                    break;
                                }
                            }
                            databaseScanner.close();
                            this.send(result);
                        }
                        if (data.contains("비밀번호 찾기")) {
                            String result = "";
                            String[] parts = data.split("\\n+");
                            String id = parts[1].split("\uc544\uc774\ub514 : ")[1];
                            String name = parts[2].split("\uc774\ub984 : ")[1];
                            String birth = parts[3].split("\uc0dd\ub144\uc6d4\uc77c : ")[1];
                            databaseScanner = new Scanner(database);
                            while (true) {
                                if (databaseScanner.hasNextLine()) {
                                    String inform = databaseScanner.nextLine();
                                    String[] informParts = inform.split("\\|\\|");
                                    if (id.equals(informParts[2].split("id : ")[1]) && name.equals(informParts[0].split("\uc774\ub984 : ")[1]) && birth.equals(informParts[1].split("\uc0dd\ub144\uc6d4\uc77c : ")[1])) {
                                        StringBuilder stringBuilder = new StringBuilder("비밀번호는 ");
                                        stringBuilder.append(informParts[3].split("pw : ")[1]);
                                        result = stringBuilder.toString();
                                        break;
                                    }
                                } else {
                                    result = "비밀번호 찾기 실패";
                                    break;
                                }
                            }
                            databaseScanner.close();
                            this.send(result);
                        }
                        if (data.contains("아파트")) {
                            String[] parts = data.split("\\n+");
                            //parts[0] 아파트 검색
                            //parts[1] 선택한 시, 구, 동 이걸 활용해서 파싱 해서 코드 받아내서 해야 함!!!
                            //parts[2] 년월 : yyyyMM
                            //엑셀파싱 26230 대신에 지역코드를 파싱해서 넣어주면 됨
                            System.out.println(parts[3]);
                            ParsingApt parsingApt = new ParsingApt(parts[3], parts[2].split("날짜 : ")[1], parts[4]);
                            //지금은 테스트로 26230만 넣고 있지만 엑셀 파싱해서 값을 선택한 시 구 동에 맞게 바꿔줘야함
                            this.send("아파트 가격 결과\n" + parsingApt.getResult());
                        }
                        if (data.contains("오피스텔")) {
                            String[] parts = data.split("\\n+");
                            ParsingOft parsingOft = new ParsingOft(parts[3], parts[2].split("날짜 : ")[1], parts[4]);
                            //지금은 테스트로 26230만 넣고 있지만 엑셀 파싱해서 값을 선택한 시 구 동에 맞게 바꿔줘야함
                            this.send("오피스텔 가격 결과\n"+parsingOft.getResult());
                        }
                        if (data.contains("연립다세대")) {
                            String[] parts = data.split("\\n+");
                            ParsingRht parsingRht = new ParsingRht(parts[3], parts[2].split("날짜 : ")[1], parts[4]);
                            //지금은 테스트로 26230만 넣고 있지만 엑셀 파싱해서 값을 선택한 시 구 동에 맞게 바꿔줘야함
                            this.send("연립다세대 가격 결과\n"+parsingRht.getResult());
                        }
                        if (data.contains("단독다가구")) {
                            String[] parts = data.split("\\n+");
                            ParsingSht parsingSht = new ParsingSht(parts[3], parts[2].split("날짜 : ")[1], parts[4]);
                            //지금은 테스트로 26230만 넣고 있지만 엑셀 파싱해서 값을 선택한 시 구 동에 맞게 바꿔줘야함
                            this.send("단독다가구 가격 결과\n"+parsingSht.getResult());
                        }

                    } catch (IOException e) {
                        try {
                            if (socketChannel.isConnected())
                                socketChannel.close();
                            //logger.error("클라이언트 통신이 안 됨 : {} : {} ", socketChannel.getRemoteAddress(), Thread.currentThread().getName());

                        } catch (IOException e1) {
                            connections.remove(this);
                            Thread.currentThread().interrupt();

                            //logger.error("IOException이 발생");
                        }
                    }
                }
            };
            executorService.submit(runnable);
        }

        private void send(String data) {
            Runnable runnable = () -> {
                try {
                    Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                    ByteBuffer byteBuffer = charset.encode(data);
                    socketChannel.write(byteBuffer);
                } catch (IOException e) {
                    try {

                        //logger.error("[ 클라이언트 통신 안 됨 : {} : {} ]", socketChannel.getRemoteAddress(), Thread.currentThread().getName());
                        connections.remove(Client.this);
                        if (socketChannel.isConnected())
                            socketChannel.close();
                    } catch (IOException e1) {
                        //logger.error("IOException 발생");
                    }
                }
            };
            executorService.submit(runnable);
        }

    }

    public void startServer() {

        try {

            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//cpu코어 수에 맞게 스레드를 생성해서 관리한다.
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(true);
            serverSocketChannel.bind(new InetSocketAddress(5000));
        } catch (IOException e) {
            //logger.error("예외가 발생하여 서버를 강제 종료한다.");
            if (serverSocketChannel.isOpen()) {
                stopServer();
                return;
            }
        }
        Runnable runnable = () -> {
            while (true) {


                try {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //logger.info("[연결 수락 :  {} : {} ]",socketChannel.getRemoteAddress(),Thread.currentThread().getName());
                    Client client = new Client(socketChannel);
                    connections.add(client);
                    //logger.info("[ 연결 개수: {} ]", connections.size());
                    if (connections.size() >= Runtime.getRuntime().availableProcessors()) {
                        //logger.error("너무 많은 연결");
                        stopServer();
                        break;
                    }
                } catch (IOException e) {
                    //logger.error("오류가 발생하여 강제 종료한다.");
                    if (serverSocketChannel.isOpen()) {
                        stopServer();
                    }
                    break;
                }
            }


        };
        executorService.submit(runnable);//스레드풀에서 처리한다


    }

    private void stopServer() {
        try {
            Iterator<Client> iterator = connections.iterator();

            while (iterator.hasNext()) {
                Client client = iterator.next();
                client.socketChannel.close();
                if (databaseScanner != null)
                    databaseScanner.close();
                if (databaseWriter != null)
                    databaseWriter.close();

                iterator.remove();
            }
            if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
                serverSocketChannel.close();
            }
            if (executorService != null && executorService.isShutdown()) {
                executorService.isShutdown();
            }
            //logger.warn("서버 멈춤");
        } catch (IOException e) {
            //logger.warn("서버 멈추는 도중 IOException 발생");
        }
    }
}
