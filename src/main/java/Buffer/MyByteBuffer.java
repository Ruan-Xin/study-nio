package Buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ruanxin
 * @create 2018-01-19
 * @desc byteBuffer
 */
class MyByteBuffer {

    private ByteBuffer rbtbuffer_all = ByteBuffer.allocate(8);


    private ByteBuffer rbtbuffer_allDir = ByteBuffer.allocateDirect(48);

    public void byteBufferDemo() {
        RandomAccessFile myFile = null;
        try {
            myFile = new RandomAccessFile("/Users/ruanxin/Documents/myblog/bytebuffer/bytebufferdemo.txt", "rw");
            FileChannel myfileChanel = myFile.getChannel();
            //chanel --> buf(write mode now!)
            int bytesread = myfileChanel.read(rbtbuffer_all);
            //若管道中的数据量大于缓冲区，则需要多次写入，并且每次读取完后清理缓冲区（外层循环）。
            while (bytesread != -1) {
                //write mode -> read mode
                rbtbuffer_all.flip();
                while (rbtbuffer_all.hasRemaining()) {
                    System.out.println((char) rbtbuffer_all.get());

                }
                rbtbuffer_all.clear();
                bytesread = myfileChanel.read(rbtbuffer_all);

                rbtbuffer_all.rewind();

                rbtbuffer_all.compact();

                rbtbuffer_all.mark();

                rbtbuffer_all.reset();

                rbtbuffer_all.compareTo(rbtbuffer_allDir);
                
                rbtbuffer_all.equals(rbtbuffer_allDir);
            }

            rbtbuffer_all.clear();
            //use put to write buffer.
            rbtbuffer_all.put((byte) 127);
            rbtbuffer_all.flip();
            System.out.println((int) rbtbuffer_all.get());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (myFile != null) {
                try {
                    myFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {
        MyByteBuffer myByteBuffer = new MyByteBuffer();
        myByteBuffer.byteBufferDemo();
    }
}
