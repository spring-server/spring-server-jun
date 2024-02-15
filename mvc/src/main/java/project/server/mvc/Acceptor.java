package project.server.mvc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newFixedThreadPool;
import lombok.extern.slf4j.Slf4j;
import project.server.mvc.tomcat.AsyncRequest;
import project.server.mvc.tomcat.Nio2EndPoint;

@Slf4j
public class Acceptor implements Runnable {

    private static final int FIXED_THREAD_COUNT = 32;
    private static final int BUFFER_CAPACITY = 1024;

    private final Selector selector;
    private final Nio2EndPoint nio2EndPoint;
    private final ExecutorService service = newFixedThreadPool(FIXED_THREAD_COUNT);

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
    public void run() {
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keys = selectedKeys.iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    if (key.isAcceptable()) {
                        acceptSocket(key, selector);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                    keys.remove();
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private void acceptSocket(
        SelectionKey key,
        Selector selector
    ) {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel socketChannel = serverChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, OP_READ);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void read(SelectionKey key) {
        log.debug("READ");
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
            socketChannel.read(buffer);
            AsyncRequest request = new AsyncRequest(socketChannel, buffer);
            buffer.flip();

            socketChannel.register(selector, OP_WRITE);
            key.attach(request);
        } catch (IOException exception) {

        }
    }

    private void write(SelectionKey key) {
        log.debug("WRITE");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        service.submit((Runnable) key.attachment());
        try {
            socketChannel.register(selector, OP_READ);
        } catch (ClosedChannelException e) {
            throw new RuntimeException(e);
        }
    }
}
