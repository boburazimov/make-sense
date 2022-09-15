package uz.yshub.makesense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uz.yshub.makesense.aop.logging.MinioNotification;

@SpringBootApplication
public class MakeSenseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakeSenseApplication.class, args);
        MinioNotification.minioNotificationHandler();
    }

}
