package project.server.mvc.tomcat;

import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

public class Nio2EndPoint extends AbstractJsseEndpoint<SocketChannel> {

    private final Poller poller;

    public Nio2EndPoint(ExecutorService executorService) {
        super(executorService);
        this.poller = new Poller();
    }

    @Override
    public boolean setSocketOptions(SocketChannel socket) {
//        PollerEvent pollerEvent = new PollerEvent(socket);
        return false;
    }

    class Poller implements Runnable {

        private static final Queue<PollerEvent> events = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (true) {
                break;
            }
        }

        public void register(NioSocketWrapper socketWrapper) {
            PollerEvent event = createPollerEvent(socketWrapper);
            addEvent(event);
        }

        private PollerEvent createPollerEvent(NioSocketWrapper socketWrapper) {
            return new PollerEvent(socketWrapper);
        }

        private void addEvent(PollerEvent event) {
            events.offer(event);
        }

        public void events(UUID uuid) {
            PollerEvent findEvent = events.stream()
                .filter(equals(uuid))
                .findAny()
                .orElseGet(() -> null);
            events.remove(findEvent);
        }

        private Predicate<PollerEvent> equals(UUID uuid) {
            return event -> event.uuid.equals(uuid);
        }
    }

    public static class PollerEvent {

        private final UUID uuid;
        private NioSocketWrapper socketWrapper;

        public PollerEvent(NioSocketWrapper socketWrapper) {
            this.uuid = UUID.randomUUID();
            this.socketWrapper = socketWrapper;
        }

        public UUID getUuid() {
            return uuid;
        }

        public NioSocketWrapper getSocketWrapper() {
            return socketWrapper;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            PollerEvent event = (PollerEvent) object;
            return uuid.equals(event.uuid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uuid);
        }
    }
}
