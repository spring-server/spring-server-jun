package project.server.mvc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import project.server.mvc.tomcat.AsyncRequest;
import project.server.mvc.tomcat.Nio2EndPoint;

@Slf4j
public class Acceptor implements Runnable {

    private static final int FINISHED = -1;
    private static final int BUFFER_CAPACITY = 1024;

    private final Selector selector;
    private final Nio2EndPoint nio2EndPoint;

    public Acceptor(
        int port,
        Nio2EndPoint nio2EndPoint
    ) throws Exception {
        this.selector = Selector.open();
        this.nio2EndPoint = nio2EndPoint;
        initContext(port);
    }

    private void initContext(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keys = selectedKeys.iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();

                if (key.isAcceptable()) {
                    acceptSocket(key, selector);
                } else if (key.isReadable()) {
                    read(key);
                }
                keys.remove();
            }
        }
    }

    private void acceptSocket(
        SelectionKey key,
        Selector selector
    ) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
        int bytesRead = socketChannel.read(buffer);
        if (bytesRead == FINISHED) {
            socketChannel.close();
            log.info("Connection closed by client.");
            return;
        }

        buffer.flip();
        new AsyncRequest(socketChannel, buffer)
            .run();
    }
}
