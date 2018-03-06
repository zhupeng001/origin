package wonder.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by apple on 2018/3/3.
 */
public class HandMadeRedisCli {
    private Socket socket;
    private InputStream reader;
    private OutputStream writer;
    public HandMadeRedisCli() throws IOException {
        socket = new Socket( "127.0.0.1" , 6379 );
        reader = socket.getInputStream();
        writer = socket.getOutputStream();
    }
    public String set( String key , String value) throws IOException {
        StringBuffer command = new StringBuffer();
        command.append("*3").append("\r\n");
        command.append("$3").append("\r\n");
        command.append("set").append("\r\n");
        command.append("$").append(key.getBytes().length).append("\r\n");
        command.append(key).append("\r\n");
        command.append("$").append(value.getBytes().length).append("\r\n");
        command.append(value).append("\r\n");
        return executeCommand(command);
    }
    public String get(String key) throws IOException {
        StringBuffer command = new StringBuffer();
        command.append("*2").append("\r\n");
        command.append("$3").append("\r\n");
        command.append("get").append("\r\n");
        command.append("$").append(key.getBytes().length).append("\r\n");
        command.append(key).append("\r\n");
        return executeCommand(command);
    }
    private String executeCommand(StringBuffer command) throws IOException {
        // 发送给服务器命令
        writer.write(command.toString().getBytes());
        byte[] b= new byte[1024];
        // 接收服务器消息
        reader.read(b);
        return new String(b);
    }
}
