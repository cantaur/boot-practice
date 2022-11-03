package com.cantaur.practice.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TaskletJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job TaskletJob(){

        Job customJob = jobBuilderFactory.get("taskletJob")
                .start(TaskStep())
                .build();

        return customJob;
    }

    @Bean
    public Step TaskStep(){
        return stepBuilderFactory.get("taskletStep")
                .tasklet((contribution, chunkContext) ->{

                    //비즈니스 로직
                    for(int idx = 0; idx < 10; idx ++){
                        log.info("[idx] = " + idx);
                    }



                    return RepeatStatus.FINISHED;
                }).build();
    }
}
