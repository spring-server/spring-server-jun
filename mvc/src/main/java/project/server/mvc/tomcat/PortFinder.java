package project.server.mvc.tomcat;

public final class PortFinder {

    private PortFinder() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    private static final int MIN_PORT_NUMBER = 1;
    private static final int MAX_PORT_NUMBER = 65535;
    private static final int DEFAULT_PORT = 8086;

    public static int findPort(String[] args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }
        return parsePort(args);
    }

    private static int parsePort(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
                return DEFAULT_PORT;
            }
            return port;
        } catch (NumberFormatException exception) {
            return DEFAULT_PORT;
        }
    }
}
