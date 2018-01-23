package Selector;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ruanxin
 * @create 2018-01-22
 * @desc
 */
public class MySelector {

    public void selectorDemo() {
        SocketChannel socketChannel = null;
        Selector selector = null;
        try {
            socketChannel = SocketChannel.open();
            selector = Selector.open();
            //must be non-blocking channel
            socketChannel.configureBlocking(false);
            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);

            //intereset set
            int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
            key.interestOps();
            selector.select();

            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }
                Set selectedKeys = selector.selectedKeys();
                Iterator iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    //every key is a channel object
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
                    if (key.isAcceptable()) {
                        key.channel();
                    } else if (key.isConnectable()) {

                    } else if (key.isReadable()) {

                    }  else if (key.isWritable()) {

                    }
                }
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
