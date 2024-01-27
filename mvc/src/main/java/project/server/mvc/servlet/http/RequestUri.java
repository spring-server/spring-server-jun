package project.server.mvc.servlet.http;

public record RequestUri(String url) {

    public RequestUri {
        validateURI(url);
    }

    private void validateURI(String url) {
        if (url == null) {
            throw new IllegalArgumentException("올바른 URL을 입력해주세요.");
        }
    }

    @Override
    public String toString() {
        return url;
    }
}
