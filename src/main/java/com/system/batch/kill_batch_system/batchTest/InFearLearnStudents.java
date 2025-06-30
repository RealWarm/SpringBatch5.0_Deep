package com.system.batch.kill_batch_system.batchTest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InFearLearnStudents {
    private Long studentId;
    private String currentLecture;
    private String instructor;
    private String persuasionMethod;

    public InFearLearnStudents(String currentLecture, String instructor, String persuasionMethod) {
        this.currentLecture = currentLecture;
        this.instructor = instructor;
        this.persuasionMethod = persuasionMethod;
    }
}