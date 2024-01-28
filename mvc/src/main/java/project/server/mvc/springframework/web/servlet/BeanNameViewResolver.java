package project.server.mvc.springframework.web.servlet;

import java.util.HashMap;
import java.util.Map;

public class BeanNameViewResolver implements ViewResolver {

    private static final String HOME = "index.html";
    private static final String MY_INFO = "/my-info.html";
    private static final String REDIRECT_VIEW = "redirect:/index.html";

    private final Map<String, View> views = new HashMap<>();

    public BeanNameViewResolver() {
        views.put(HOME, new StaticView());
        views.put(MY_INFO, new MyInfoView());
        views.put(REDIRECT_VIEW, new RedirectView());
    }

    @Override
    public View resolveViewName(String viewName) {
        return views.get(viewName);
    }

    public Map<String, View> getViews() {
        return views;
    }

    @Override
    public String toString() {
        return String.format("views:%s", views);
    }
}
