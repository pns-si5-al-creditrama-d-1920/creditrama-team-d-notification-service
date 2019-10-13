package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.utils;

import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service.SendNotifConsumer;

import java.util.Properties;

public class ConsumerConfig {

    public static SendNotifConsumer configConsumer(String kafkaBrokers) {
        Properties props = new Properties();
        props.setProperty("group.id", SendNotifConsumer.class.getCanonicalName());
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("bootstrap.servers", kafkaBrokers);
        props.setProperty("auto.commit.interval.ms", "100");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return new SendNotifConsumer(props);
    }

    public static SendNotifConsumer configConsumer(String groupId, String kafkaBrokers) {
        Properties props = new Properties();
        props.setProperty("group.id", groupId);
        props.setProperty("bootstrap.servers", kafkaBrokers);
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "100");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return new SendNotifConsumer(props);
    }

    /*
    public static SendNotifProducer configProducer(String groupId, String kafkaBrokers) {
        Properties props = new Properties();
        props.setProperty("group.id", groupId);
        props.setProperty("bootstrap.servers", kafkaBrokers);
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "100");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new SendNotifProducer(props);
    }*/
}
