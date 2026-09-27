package com.info.course_service.repo;

import com.info.course_service.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategoryId(Long categoryId);
    List<Course> findByInstructorId(Long instructorId);
    List<Course> findByStatus(String status);
}
