package com.vaadin.tutorial.crm.ui.view.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Story;
import com.vaadin.tutorial.crm.backend.service.StoryService;
import com.vaadin.tutorial.crm.ui.MainLayout;

import static com.vaadin.tutorial.crm.backend.enums.Stage.DONE;
import static com.vaadin.tutorial.crm.backend.enums.Stage.IN_PROGRESS;
import static com.vaadin.tutorial.crm.backend.enums.Stage.OPEN;
import static com.vaadin.tutorial.crm.backend.enums.Stage.TODO;

@Route(value = "stories", layout = MainLayout.class)
@PageTitle("Stories | Vaadin CRM")
public class StoryListView extends VerticalLayout {

    public static final String MINUS = "-";
    private Grid<Story> storyGrid = new Grid<>(Story.class);
    private StoryService storyService;

    public StoryListView(StoryService storyService) {
        this.storyService = storyService;

        addClassName("list-view");
        setSizeFull();

        configureGrid();

        add(storyGrid);


    }

    private void configureGrid() {
        storyGrid.addClassName("contact-grid");
        storyGrid.setSizeFull();
        storyGrid.removeAllColumns();
        createColums();
        storyGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void createColums() {
        storyGrid.addColumn(story -> OPEN.equals(story.getStage()) ? story.getTitle() : MINUS).setHeader("Open");
        storyGrid.addColumn(story -> TODO.equals(story.getStage()) ? story.getTitle() : MINUS).setHeader("TODO");
        storyGrid.addColumn(story -> IN_PROGRESS.equals(story.getStage()) ? story.getTitle() : MINUS).setHeader("In Progress");
        storyGrid.addColumn(story -> DONE.equals(story.getStage()) ? story.getTitle() : MINUS).setHeader("Done");
    }
}
