package com.system.batch.kill_batch_system.batchTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BrainwashedVictim {
    private Long victimId;
    private String originalLecture;
    private String originalInstructor;
    private String brainwashMessage;
    private String newMaster;
    private String conversionMethod;
    private String brainwashStatus;
    private String nextAction;
}