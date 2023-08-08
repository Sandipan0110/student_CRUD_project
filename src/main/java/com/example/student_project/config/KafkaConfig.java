package com.example.student_project.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic kafkaTopicWithStrng() {
        return TopicBuilder
                .name("New_Kafka_Topic_String")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic kafkaTopicWithJson() {
        return TopicBuilder
                .name("New_Kafka_Topic_Json")
                .partitions(2)
                .build();
    }
}
