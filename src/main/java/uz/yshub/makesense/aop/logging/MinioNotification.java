package uz.yshub.makesense.aop.logging;

import io.minio.CloseableIterator;
import io.minio.ListenBucketNotificationArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Event;
import io.minio.messages.NotificationRecords;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioNotification {

    public static void minioNotificationHandler() {

        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("minioadmin", "minioadmin")
                .build();

        String[] events = {"s3:ObjectCreated:*", "s3:ObjectAccessed:*", "s3:ObjectRemoved:*"};
        try (CloseableIterator<Result<NotificationRecords>> ci =
                     minioClient.listenBucketNotification(
                             ListenBucketNotificationArgs.builder()
                                     .bucket("makesense")
                                     .prefix("")
                                     .suffix("")
                                     .events(events)
                                     .build())) {
            while (ci.hasNext()) {
                NotificationRecords records = ci.next().get();
                for (Event event : records.events()) {
                    System.out.println("<<<<<----- Minio Notification ----->>>>> Event: " + event.eventType() + " occurred at " + event.eventTime()
                            + " for " + event.bucketName() + "/" + event.objectName() + ". File size: " + event.objectSize());
                }
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        }
    }
}
