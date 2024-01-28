package project.server.mvc.springframework.web.servlet;

import project.server.mvc.springframework.ui.ModelMap;

public class ModelAndView {

    private Object view;
    private ModelMap model;

    public ModelAndView() {
    }

    public ModelAndView(
        Object view,
        ModelMap model
    ) {
        this.view = view;
        this.model = model;
    }

    public ModelAndView(Object view) {
        this(view, null);
    }

    public ModelAndView(String viewName) {
        this(viewName, null);
    }

    public Object getView() {
        return view;
    }

    public String getViewName() {
        return (this.view instanceof String name ? name : null);
    }

    public void setViewName(String viewName) {
        this.view = viewName;
    }

    public ModelMap getModelMap() {
        return model;
    }

    @Override
    public String toString() {
        return String.format("view: %s, model: %s", view, model);
    }
}
