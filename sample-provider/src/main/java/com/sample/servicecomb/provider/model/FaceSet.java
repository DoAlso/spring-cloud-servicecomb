package com.sample.servicecomb.provider.model;

import java.io.Serializable;
import java.util.Date;

public class FaceSet implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.face_set_name
     *
     * @mbg.generated
     */
    private String faceSetName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.face_set_id
     *
     * @mbg.generated
     */
    private String faceSetId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.creator
     *
     * @mbg.generated
     */
    private String creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.created
     *
     * @mbg.generated
     */
    private Date created;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.updator
     *
     * @mbg.generated
     */
    private String updator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.updated
     *
     * @mbg.generated
     */
    private Date updated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.face_set_capacity
     *
     * @mbg.generated
     */
    private Long faceSetCapacity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.project_id
     *
     * @mbg.generated
     */
    private Long projectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column face_set.face_set_type
     *
     * @mbg.generated
     */
    private Byte faceSetType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table face_set
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.id
     *
     * @return the value of face_set.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.id
     *
     * @param id the value for face_set.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.face_set_name
     *
     * @return the value of face_set.face_set_name
     *
     * @mbg.generated
     */
    public String getFaceSetName() {
        return faceSetName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.face_set_name
     *
     * @param faceSetName the value for face_set.face_set_name
     *
     * @mbg.generated
     */
    public void setFaceSetName(String faceSetName) {
        this.faceSetName = faceSetName == null ? null : faceSetName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.face_set_id
     *
     * @return the value of face_set.face_set_id
     *
     * @mbg.generated
     */
    public String getFaceSetId() {
        return faceSetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.face_set_id
     *
     * @param faceSetId the value for face_set.face_set_id
     *
     * @mbg.generated
     */
    public void setFaceSetId(String faceSetId) {
        this.faceSetId = faceSetId == null ? null : faceSetId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.creator
     *
     * @return the value of face_set.creator
     *
     * @mbg.generated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.creator
     *
     * @param creator the value for face_set.creator
     *
     * @mbg.generated
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.created
     *
     * @return the value of face_set.created
     *
     * @mbg.generated
     */
    public Date getCreated() {
        return created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.created
     *
     * @param created the value for face_set.created
     *
     * @mbg.generated
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.updator
     *
     * @return the value of face_set.updator
     *
     * @mbg.generated
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.updator
     *
     * @param updator the value for face_set.updator
     *
     * @mbg.generated
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.updated
     *
     * @return the value of face_set.updated
     *
     * @mbg.generated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.updated
     *
     * @param updated the value for face_set.updated
     *
     * @mbg.generated
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.face_set_capacity
     *
     * @return the value of face_set.face_set_capacity
     *
     * @mbg.generated
     */
    public Long getFaceSetCapacity() {
        return faceSetCapacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.face_set_capacity
     *
     * @param faceSetCapacity the value for face_set.face_set_capacity
     *
     * @mbg.generated
     */
    public void setFaceSetCapacity(Long faceSetCapacity) {
        this.faceSetCapacity = faceSetCapacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.project_id
     *
     * @return the value of face_set.project_id
     *
     * @mbg.generated
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.project_id
     *
     * @param projectId the value for face_set.project_id
     *
     * @mbg.generated
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column face_set.face_set_type
     *
     * @return the value of face_set.face_set_type
     *
     * @mbg.generated
     */
    public Byte getFaceSetType() {
        return faceSetType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column face_set.face_set_type
     *
     * @param faceSetType the value for face_set.face_set_type
     *
     * @mbg.generated
     */
    public void setFaceSetType(Byte faceSetType) {
        this.faceSetType = faceSetType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table face_set
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FaceSet other = (FaceSet) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFaceSetName() == null ? other.getFaceSetName() == null : this.getFaceSetName().equals(other.getFaceSetName()))
            && (this.getFaceSetId() == null ? other.getFaceSetId() == null : this.getFaceSetId().equals(other.getFaceSetId()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreated() == null ? other.getCreated() == null : this.getCreated().equals(other.getCreated()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getUpdated() == null ? other.getUpdated() == null : this.getUpdated().equals(other.getUpdated()))
            && (this.getFaceSetCapacity() == null ? other.getFaceSetCapacity() == null : this.getFaceSetCapacity().equals(other.getFaceSetCapacity()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getFaceSetType() == null ? other.getFaceSetType() == null : this.getFaceSetType().equals(other.getFaceSetType()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table face_set
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFaceSetName() == null) ? 0 : getFaceSetName().hashCode());
        result = prime * result + ((getFaceSetId() == null) ? 0 : getFaceSetId().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreated() == null) ? 0 : getCreated().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getUpdated() == null) ? 0 : getUpdated().hashCode());
        result = prime * result + ((getFaceSetCapacity() == null) ? 0 : getFaceSetCapacity().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getFaceSetType() == null) ? 0 : getFaceSetType().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table face_set
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", faceSetName=").append(faceSetName);
        sb.append(", faceSetId=").append(faceSetId);
        sb.append(", creator=").append(creator);
        sb.append(", created=").append(created);
        sb.append(", updator=").append(updator);
        sb.append(", updated=").append(updated);
        sb.append(", faceSetCapacity=").append(faceSetCapacity);
        sb.append(", projectId=").append(projectId);
        sb.append(", faceSetType=").append(faceSetType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}