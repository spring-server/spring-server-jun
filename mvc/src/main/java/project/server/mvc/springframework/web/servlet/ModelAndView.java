package project.server.mvc.springframework.web.servlet;

import project.server.mvc.springframework.ui.ModelMap;

public class ModelAndView {

    private Object view;
    private ModelMap model;

    public ModelAndView() {
    }

    public ModelAndView(Object view) {
        this.view = view;
        this.model = new ModelMap();
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
        this.model = new ModelMap();
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
