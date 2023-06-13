import java.io.*;
import java.net.*;

public class Myserver {
    private ServerSocket server;
    private Socket socket;

    void start(){
        try{
            server = new ServerSocket(8998);
            System.out.println("创建成功");
            while (true){
                System.out.println("等待客户联机");
                socket = server.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true){
                    String message = reader.readLine();
                    if ("exit".equals(message)){
                        System.out.println("客户机退出");
                        break;
                    }
                    System.out.println("客户机："+message);
                }
                reader.close();
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Myserver tcp = new Myserver();
        tcp.start();
    }
}
