package com.matchpoint.model;

import com.matchpoint.Util.DateUtil;
import com.matchpoint.enums.GenderTypeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by gokul on 15/6/17.
 */
@Entity
@Table(name="user")
public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="active")
    private boolean active;
    @NotNull
    @Column(name="email",unique = true)
    private String email;
    @Column(name="password")
    private String password;
    @NotNull
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(columnDefinition="int default 0")
    private int gender;
    @OneToOne
    @JoinColumn(name = "player_category_id")
    private PlayerCategory playerCategory;
    @Column
    private boolean adminApproved =true;
    @NotNull
    @Column(name="mobile")
    private String mobile;
    @NotNull
    @Column(name = "dob")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dob;
    @OneToMany(targetEntity = Payment.class,mappedBy = "user",cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Payment> payments;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn)
    private Set<Role> roles;

    public User() {
    }

    public User(boolean active, String email, String password, String firstName, String lastName, String mobile,Date dob, Set<Role> roles) {
        this.active = active;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.dob=dob;
        this.roles = roles;
    }

    public User(User user) {
        this.id=user.getId();
        this.active=user.isActive();
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.mobile=user.getMobile();
        this.dob=user.getDob();
        this.roles=user.getRoles();

    }


    public PlayerCategory getPlayerCategory() {
        return playerCategory;
    }

    public User setPlayerCategory(PlayerCategory playerCategory) {
        this.playerCategory = playerCategory;
        return this;
    }

    public boolean isAdminApproved() {
        return adminApproved;
    }

    public User setAdminApproved(boolean adminApproved) {
        this.adminApproved = adminApproved;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDob() {return dob;}

    public void setDob(Date dob) { this.dob = dob;}

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public User setPayments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public String getGenderName(int genderCode) {
        if (genderCode==0)
            return "";
        return Arrays.stream(GenderTypeEnum.values()).filter(genderTypeEnum -> genderTypeEnum.getValue()==genderCode).findAny().get().getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (active != user.active) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (!mobile.equals(user.mobile)) return false;
        if (!dob.equals(user.dob)) return false;
        return roles != null ? roles.equals(user.roles) : user.roles == null;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dob=" + dob +
                ", roles=" + roles +
                '}';
    }

    public Payment getCurrentMonthPayment() {
        List<Payment> payments = this.getPayments();
        if(payments!=null){
            Optional<Payment> monthly = payments.stream().filter(payment -> payment.getFee().getFeeName().equals("Monthly") && DateUtil.isCurrentMonth(payment.getPaymentDate())).findAny();
            if (monthly.isPresent()){
                return monthly.get();
            }
        }
        return null;
    }
}