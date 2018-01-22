package Channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author ruanxin
 * @create 2018-01-22
 * @desc
 */
public class MyChannel {

    public void serverSocketChannelDemo() {
        
    }
    //socket channel
    public void socketChannelDemo() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();

            //read from channel
            ByteBuffer buffer = ByteBuffer.allocate(48);
            int bytesRead = socketChannel.read(buffer);
            if (bytesRead == -1) {
                // the end of stream, the connect has closed;
            }

            //write into channel
            String myData = "this is buffer data!";
            ByteBuffer buffer1 = ByteBuffer.allocate(48);
            buffer1.clear();
            buffer1.put(myData.getBytes());

            buffer1.flip();
            while (buffer1.hasRemaining()) {
                socketChannel.write(buffer1);
            }

            //non-blocking mode
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("https://www.baidu.com", 80));

            while (!socketChannel.finishConnect()) {
                //wait or doing something
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
