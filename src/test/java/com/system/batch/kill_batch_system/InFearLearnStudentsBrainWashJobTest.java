package com.system.batch.kill_batch_system;

import com.system.batch.kill_batch_system.batchTest.InFearLearnStudents;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
class InFearLearnStudentsBrainWashJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Job inFearLearnStudentsBrainWashJob;

    @TempDir
    private Path tempDir;

    private static final List<InFearLearnStudents> TEST_STUDENTS = List.of(
            new InFearLearnStudents("스프링 핵심 원*", "세계관 최강자", "MURDER_YOUR_IGNORANCE"),
            new InFearLearnStudents("고성* JPA & Hibernate", "자바계의 독재자", "SLAUGHTER_YOUR_LIMITS"),
            new InFearLearnStudents("토*의 스프링 부트", "원조 처형자", "EXECUTE_YOUR_POTENTIAL"),
            new InFearLearnStudents("스프링 시큐리티 완전 정*", "무결점 학살자", "TERMINATE_YOUR_EXCUSES"),
            new InFearLearnStudents("자바 프로그래밍 입* 강좌 (old ver.)", "InFearLearn", "RESIST_BRAINWASH") // 💀 이 놈은 ItemProcessor 필터링 대상
    );

    @PostConstruct
    public void configureJobLauncherTestUtils() throws Exception {
        jobLauncherTestUtils.setJob(inFearLearnStudentsBrainWashJob);
    }

    @AfterEach
    void cleanup() {
        jdbcTemplate.execute("TRUNCATE TABLE infearlearn_students RESTART IDENTITY");
    }

    @Test
    @DisplayName("💀 전체 Job 실행 성공 테스트")
    void shouldLaunchJobSuccessfully() throws Exception {
        // Given - 세뇌 대상자들 투입
        insertTestStudents();
        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
                .addString("filePath", tempDir.toString())
                .toJobParameters();


        // When - 세뇌 배치 실행
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);


        // Then - 배치 실행 결과 검증
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        Path expectedFile = Paths.get("src/test/resources/expected_brainwashed_victims.jsonl");
        Path actualFile = tempDir.resolve("brainwashed_victims.jsonl");

        List<String> expectedLines = Files.readAllLines(expectedFile);
        List<String> actualLines = Files.readAllLines(actualFile);

        Assertions.assertLinesMatch(expectedLines, actualLines);
    }

    private void insertTestStudents() {
        TEST_STUDENTS.forEach(student ->
                jdbcTemplate.update("INSERT INTO infearlearn_students (current_lecture, instructor, persuasion_method) VALUES (?, ?, ?)",
                        student.getCurrentLecture(), student.getInstructor(), student.getPersuasionMethod())
        );
    }
}