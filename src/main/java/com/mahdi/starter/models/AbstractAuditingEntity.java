package com.mahdi.starter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۲۲/۱۰/۲۰۲۰ & Time: 10:26
 * http://gitlab.com/mahdihp
 */
@Data
public class AbstractAuditingEntity {

  private static final long serialVersionUID = 1L;

  @CreatedBy
  @JsonIgnore
  private String createdBy;

  @CreatedDate
  @JsonIgnore
  private Instant createdDate = Instant.now();

  @LastModifiedBy
  @JsonIgnore
  private String lastModifiedBy;

  @LastModifiedDate
  @JsonIgnore
  private Instant lastModifiedDate = Instant.now();
}
