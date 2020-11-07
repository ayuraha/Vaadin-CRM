package com.vaadin.tutorial.crm.ui.view.list;

import com.vaadin.flow.component.grid.ColumnTextAlign;
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

        addClassName("story-view");
        setSizeFull();
        configureGrid();

        add(storyGrid);

        updateList();
    }

    private void configureGrid() {
        storyGrid.addClassName("story-grid");
        storyGrid.setSizeFull();
        storyGrid.removeAllColumns();
        createColums();
        storyGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void createColums() {
//        ProgressBar progressBar = new ProgressBar();
//        progressBar.setValue(0.345);
        storyGrid.addColumn(story -> OPEN.equals(story.getStage()) ? story.getTitle() : MINUS).setHeader("Open").setTextAlign(ColumnTextAlign.CENTER).setFooter(progressBar);
        storyGrid.addColumn(story -> TODO.equals(story.getStage()) ? story.getTitle() : MINUS).setHeader("TODO").setTextAlign(ColumnTextAlign.CENTER).setFooter(progressBar);
        storyGrid.addColumn(story -> IN_PROGRESS.equals(story.getStage()) ? story.getTitle() : MINUS).setHeader("In Progress").setTextAlign(ColumnTextAlign.CENTER).setFooter(progressBar);
        storyGrid.addColumn(story -> DONE.equals(story.getStage()) ? story.getTitle() : MINUS).setHeader("Done").setTextAlign(ColumnTextAlign.CENTER).setFooter(progressBar);
    }

    private void updateList() {
        storyGrid.setItems(storyService.findAll());
        storyGrid.addThemeVariants();
    }
}
