package utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class DakalaServer {
    static void server() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);//数据箱子
        Selector selector = Selector.open();//调度器，一个即可

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8888));
        ssc.register(selector, SelectionKey.OP_ACCEPT);//只对Accept状态感兴趣的集合

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();

            while (it.hasNext()) {//有新的Channel注册到Selector上
                SelectionKey key = it.next();
                //key符合条件 Accept条件
                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();//拿到这个key对应的Channel

                    SocketChannel sc = ssChannel.accept();//每次ServerSocketChannel调用accept就产生一个新的SocketChannel
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);//SocketChannel只对READ状态有兴趣
                }
                //符合READ条件
                else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer bb = ByteBuffer.allocate(512);
                    sc.read(bb);
                    String result = new String(bb.array()).trim();
                    System.out.println(result+System.currentTimeMillis());
                }
                it.remove();
            }
        }
    }



    public static void main(String[] args) throws IOException {
        server();
    }
}
