package com.spring.batch.batch;

import com.spring.batch.entity.AfterEntity;
import com.spring.batch.entity.BeforeEntity;
import com.spring.batch.repository.AfterRepository;
import com.spring.batch.repository.BeforeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class FirstBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BeforeRepository beforeRepository;
    private final AfterRepository afterRepository;


    @Bean
    public Job firstJob() {

        return new JobBuilder("firstJob", jobRepository)
                .start(firstStep()) // 스텝 들어갈 자리
//                .next() // 다음 스텝
//                .next()
                .build(); // 마지막 실행
    }

    @Bean
    public Step firstStep() {
        return new StepBuilder("firstStep", jobRepository)
                // chunk : 실행 단위 메모리 용량을 생각해서 정하자
                .<BeforeEntity, AfterEntity> chunk(10, platformTransactionManager)
                .reader(beforeReader())
                .processor(middleProcessor())
                .writer(afterWriter())
                .build();
    }

    @Bean
    public RepositoryItemReader<BeforeEntity> beforeReader(){

        return new RepositoryItemReaderBuilder<BeforeEntity>()
                .name("beforeReader")
                .pageSize(10) // 10개씩
                .methodName("findAll") // 메서드 명
                .repository(beforeRepository)
                .sorts(Map.of("id", Sort.Direction.ASC)) // 정렬
                .build();
    }

    @Bean
    public ItemProcessor<BeforeEntity, AfterEntity> middleProcessor(){

        return new ItemProcessor<BeforeEntity, AfterEntity>() {
            @Override
            public AfterEntity process(BeforeEntity item) throws Exception {

                AfterEntity afterEntity = new AfterEntity();
                afterEntity.setUsername(item.getUsername());

                return afterEntity;
            }
        };
    }

    @Bean
    public RepositoryItemWriter<AfterEntity> afterWriter(){

        return new RepositoryItemWriterBuilder<AfterEntity>()
                .repository(afterRepository)
                .methodName("save")
                .build();

    }



}
