package Channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author ruanxin
 * @create 2018-01-22
 * @desc
 */
public class MyChannel {

    //udp channel
    public void datagramChannelDemo () {
        DatagramChannel datagramChannel = null;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.socket().bind(new InetSocketAddress(9999));
            //receive data
            ByteBuffer buffer = ByteBuffer.allocate(48);
            buffer.clear();
            datagramChannel.receive(buffer);

            // send data
            String sendData = "this is send data";
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.put(sendData.getBytes());
            buf.flip();

            int bytesSent = datagramChannel.send(buf, new InetSocketAddress("https://www.baidu.com", 80));

            //there is no connecting and then the server may wont response.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void serverSocketChannelDemo() {
        ServerSocketChannel serverSocketChannel = null;

        try {
            // -------------------- this is blocking mode ------------
            //open the serverSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));

            while (true) {
                //
                SocketChannel socketChannel = serverSocketChannel.accept();
                //do something with the socketChannel

                //to reach next demo, and order to delete the error
                break;
            }

            // -------------------- this is non-blocking mode --------
            serverSocketChannel.configureBlocking(false);
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();

                if (socketChannel != null) {
                    // do something with thie socketChannel
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //close the serverSocketChannel
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
