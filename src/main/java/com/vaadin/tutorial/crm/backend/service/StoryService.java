package com.vaadin.tutorial.crm.backend.service;

import com.vaadin.tutorial.crm.backend.entity.Story;
import com.vaadin.tutorial.crm.backend.enums.Stage;
import com.vaadin.tutorial.crm.backend.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor =@__(@Autowired))
public class StoryService {

    private final StoryRepository storyRepository;

    public List<Story> findAll() {
        return storyRepository.findAll();
    }

    public long count() {
        return storyRepository.count();
    }

    public List<Story> getStoriesByStage(Stage stage) {
        return storyRepository.findAllByStage(stage);
    }
}
