package com.info.course_service.dto;

public class CourseRequestDTO {
    private String title;
    private String description;
    private Double price;
    private Long categoryId;
    private String level; // BEGINNER, INTERMEDIATE, ADVANCED

    public CourseRequestDTO() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getLevel() {
        return level == null ? "BEGINNER" : level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
