package com.sample.servicecomb.provider.model.req;

import com.obs.services.model.PartEtag;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CompleteMultipartReq
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/21 19:43
 */
public class CompleteMultipartReq implements Serializable {
    private String uploadId;
    private String fileName;
    private List<PartEtag> partETags;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<PartEtag> getPartETags() {
        return partETags;
    }

    public void setPartETags(List<PartEtag> partETags) {
        this.partETags = partETags;
    }
}
