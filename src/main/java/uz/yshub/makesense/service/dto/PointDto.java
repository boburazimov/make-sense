package uz.yshub.makesense.service.dto;

import lombok.Data;

@Data
public class PointDto {
    private String label;
    private double pointX;
    private double pointY;
    private long imageId;
    private String imageName;
    private int width;
    private int height;
}
