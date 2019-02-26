package com.sample.servicecomb.common.bean;

/**
 * @ClassName FaceCaptured
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/6 13:34
 */
public class FaceCaptured {
    private String camera_id;
    private String face_id;
    private String ori_img_id;
    private Long time;
    private Bbox bbox;
    private String image_data;

    public String getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    public String getOri_img_id() {
        return ori_img_id;
    }

    public void setOri_img_id(String ori_img_id) {
        this.ori_img_id = ori_img_id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Bbox getBbox() {
        return bbox;
    }

    public void setBbox(Bbox bbox) {
        this.bbox = bbox;
    }

    public String getImage_data() {
        return image_data;
    }

    public void setImage_data(String image_data) {
        this.image_data = image_data;
    }
}
