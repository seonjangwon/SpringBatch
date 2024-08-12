package com.spring.batch.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class FirstSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;


    // 10초 마다 X n분 10초에 실행
//    @Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul") // 초 분 시간 날 월 년?
    public  void runFirstJob() throws Exception {

        System.out.println("first schedule start");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = dateFormat.format( new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("firstJob"), jobParameters);
    }
}
