package project.server.mvc.springframework.web.servlet;

import java.util.HashMap;
import java.util.Map;

public class BeanNameViewResolver implements ViewResolver {

    private static final String HOME = "index.html";
    private static final String REDIRECT_LOCATION = "redirect:/index.html";

    private final Map<String, View> views = new HashMap<>();

    public BeanNameViewResolver() {
        views.put(HOME, new StaticView());
        views.put(REDIRECT_LOCATION, new RedirectView());
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
