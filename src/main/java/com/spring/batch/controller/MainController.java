package com.spring.batch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class MainController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;


    /**
     * API batch 실행 방법
     * @param value
     * @return
     * @throws Exception
     */
    @GetMapping("/first")
    public String firstAPI(@RequestParam("value") String value) throws Exception {

        // 원하는 파라미터 설정 방법??
        // value 값이 같으면 실행을 하지 않음
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("data",value)
                .toJobParameters();


        jobLauncher.run(jobRegistry.getJob("firstJob"), jobParameters );

        return "ok";
    }
}
