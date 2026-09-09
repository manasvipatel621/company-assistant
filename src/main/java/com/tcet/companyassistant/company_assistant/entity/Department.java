package com.tcet.companyassistant.company_assistant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "department_name")
    private String departmentName;

    private String description;

    @Column(name = "head_user_id")
    private Integer headUserId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "head_user_id",
        referencedColumnName = "user_id",
        insertable = false,
        updatable = false
    )
    private User headUser;

    @OneToMany(
        mappedBy = "department",
        fetch = FetchType.LAZY
    )
    private List<User> users;


    // Getters and setters

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHeadUserId() {
        return headUserId;
    }

    public void setHeadUserId(Integer headUserId) {
        this.headUserId = headUserId;
    }

    public User getHeadUser() {
        return headUser;
    }

    public void setHeadUser(User headUser) {
        this.headUser = headUser;
    }
    @JsonIgnore 
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}