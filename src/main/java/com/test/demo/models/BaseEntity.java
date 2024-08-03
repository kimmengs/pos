package com.test.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity<TKey extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @NotNull(message = "Id can not be null")
    private TKey id;

    @Column(name = "main_action_code", nullable = false)
    @NotNull(message = "Main Action Code can not be null.")
    private String mainActionCode;

    @Column(name = "mod_num", nullable = false)
    @NotNull(message = "Mod Num can not be null.")
    private Integer modNum;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Status can not be null.")
    private String status;

    @Column(name = "created_date", nullable = false)
    @NotNull(message = "Created Date can not be null.")
    private Long createdDate;

    @Column(name = "created_by", nullable = false)
    @NotNull(message = "Created By can not be null.")
    private String createdBy;

    @Column(name = "modified_date")
    private Long modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

}
