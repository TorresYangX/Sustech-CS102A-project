import java.net.*;
public class E2 {
    public static void main(String[] args) {
        InetAddress ip;
        try{
            ip = InetAddress.getLocalHost();
            String LocalName = ip.getHostName();
            String LocalIp = ip.getHostAddress();
            System.out.println("name:"+LocalName);
            System.out.println("ip:"+LocalIp);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
