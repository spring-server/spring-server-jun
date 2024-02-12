package project.server.app.core.web.user.application;

public interface UserSaveUseCase {
    Long save(String username, String password);
}
