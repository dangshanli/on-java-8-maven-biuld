package utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChannelSelector {

    void selector() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);//数据箱子
        Selector selector = Selector.open();//调度器，一个即可

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8081));
        ssc.register(selector, SelectionKey.OP_ACCEPT);//只对Accept状态感兴趣的集合

        while (true) {
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
                    it.remove();
                }
                //符合READ条件
                else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    while (true) {
                        buffer.clear();
                        int n = sc.read(buffer);
                        if (n < 0) {
                            break;
                        }
                        buffer.flip();
                    }
                    it.remove();
                }
            }
        }
    }

    static void testBuffer() throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put("天下一家".getBytes("utf-8"));
        bb.flip();
        FileChannel channel = FileChannel.open(Path.of("src/resources/man.txt"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        channel.write(bb);

    }

    static void testRead() throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(512);
        FileChannel channel = FileChannel.open(Path.of("src/resources/man.txt"), StandardOpenOption.READ);
        int number = channel.read(bb);
        System.out.println("number:" + number);

        bb.flip();
        byte[] bytes = new byte[bb.limit()];
        bb.get(bytes);
        String s = new String(bytes, "utf-8");
        System.out.println(s);
    }


    static void transfer() throws IOException {
        FileChannel channel = FileChannel.open(Path.of("src/resources/girl.txt"),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);

//        channel.transferFrom();
    }


    static void clients() {
        ExecutorService pools = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            pools.submit(() -> {
                try {
                    SocketChannel sc = SocketChannel.open();
                    sc.connect(new InetSocketAddress("localhost", 8001));
                    sc.configureBlocking(true);
                    ByteBuffer bb = ByteBuffer.wrap(("朝辞白帝彩云间" + Thread.currentThread().getName())
                            .getBytes(StandardCharsets.UTF_8));
                    bb.flip();
                    while (bb.hasRemaining()) {
                        sc.write(bb);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        pools.shutdown();


    }

    static void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8001));
        socketChannel.configureBlocking(true);
        ByteBuffer bb = ByteBuffer.wrap(("朝辞白帝彩云间" + Thread.currentThread().getName())
                .getBytes(StandardCharsets.UTF_8));
        bb.flip();
        while (bb.hasRemaining())
            socketChannel.write(bb);
        assert bb.position() == bb.limit();
        socketChannel.close();
    }


    public static void main(String[] args) throws IOException {
//        testBuffer();
//        testRead();
//        clients();
        client();
    }


}
