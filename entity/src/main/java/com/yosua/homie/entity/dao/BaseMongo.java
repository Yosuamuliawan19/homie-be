package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.fields.BaseMongoFields;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseMongo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Field(value = BaseMongoFields.ID)
  private String id;

  @Version
  @Field(value = BaseMongoFields.VERSION)
  private Long version;

  @CreatedDate
  @Field(value = BaseMongoFields.CREATED_DATE)
  private Date createdDate;

  @CreatedBy
  @Field(value = BaseMongoFields.CREATED_BY)
  private String createdBy;

  @LastModifiedDate
  @Field(value = BaseMongoFields.UPDATED_DATE)
  private Date updatedDate;

  @LastModifiedBy
  @Field(value = BaseMongoFields.UPDATED_BY)
  private String updatedBy;

  @Field(value = BaseMongoFields.IS_DELETED)
  private Integer isDeleted = 0;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Integer getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(Integer isDeleted) {
    this.isDeleted = isDeleted;
  }

  @Override
  public String toString() {
    return "BaseMongo{" +
            "id='" + id + '\'' +
            ", version=" + version +
            ", createdDate=" + createdDate +
            ", createdBy='" + createdBy + '\'' +
            ", updatedDate=" + updatedDate +
            ", updatedBy='" + updatedBy + '\'' +
            ", isDeleted=" + isDeleted +
            '}';
  }
}
