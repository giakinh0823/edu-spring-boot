package com.example.demo.domain.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="[user]")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "nvarchar(255)")
	private String username;
	@Column(columnDefinition = "nvarchar(255)")
	private String password;
	@Column(columnDefinition = "nvarchar(255)")
	private String first_name;
	@Column(columnDefinition = "nvarchar(255)")
	private String last_name;
	@Column(columnDefinition = "nvarchar(255) not null")
	private String email;
	@Column(columnDefinition = "nvarchar(1000)")
	private String image;
	@Column(columnDefinition = "nvarchar(20)")
	private String phone;
	private Boolean is_super;
	private Boolean is_active;
	private Boolean gender;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_permission",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
            )
	private Set<Permission> permissions = new HashSet<>();
}
