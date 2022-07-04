package ch2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingServer {
    private ServerSocket server;

    public static void main(String[] args) throws Exception {
        BlockingServer server = new BlockingServer();
        server.run();
    }

    private void run() throws IOException {
        server = new ServerSocket(8888); // 1
        System.out.println("접속 대기중");

        while (true) {
            // 연결되는 클라이언트가 없으면 쓰레드 또한 2번에서 멈춘다
            Socket sock = server.accept(); // 2
            System.out.println("클라이언트 연결");

            OutputStream out = sock.getOutputStream(); // 3
            InputStream in = sock.getInputStream(); // 4

            while (true) {
                try {
                    /**
                     * 소켓이 연결이 되어도 5번에서 또 멈춘다.
                     * 클라이언트에서 데이터 입력이 아직 없으니깐 기다린다
                     */
                    int request = in.read(); // 5
                    out.write(request);
                } catch (IOException e) {
                    break;
                }
            }
        }
    }
}
