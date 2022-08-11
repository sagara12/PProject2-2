import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            // 소켓 생성
            serverSocket = new ServerSocket();

            // 포트 바인딩
            serverSocket.bind(new InetSocketAddress("localhost", 5001));
            System.out.println("Connected by:" + "localhost"+ 5001);
            while (true) {

                socket = serverSocket.accept();

                //연결된 클라이언트 IP 주소 얻기
                InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();


                byte[] bytes = null;
                String message = null;

                //데이터 받기
                InputStream is = socket.getInputStream();
                DataInputStream dins = new DataInputStream(is);
                String str = dins.readUTF();
                System.out.println("Received(Server) : " + str);

                if (str.equals("Ping")) {
                    message = "Pong";
                }else{
                    message = str;
                }
                /*bytes = new byte[100];
                int readByteCount = is.read(bytes);
                message = new String(bytes,0, readByteCount, "UTF-8");*/


                //데이터 보내기
                OutputStream os = socket.getOutputStream();
                DataOutputStream dous = new DataOutputStream(os);
                System.out.println("Send(Server): " + message);
                dous.writeUTF(message);

                bytes = message.getBytes("UTF-8");
                os.write(bytes);
                os.flush();





                is.close();
                os.close();
                socket.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!serverSocket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
