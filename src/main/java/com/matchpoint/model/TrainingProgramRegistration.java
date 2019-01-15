package com.matchpoint.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Prithviprakash on 3/11/18.
 */
@Entity
public class TrainingProgramRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String playerName;
    @Column
    private String playerEmail;
    @Column
    private String playerMobile;
    @Column(columnDefinition="int default 0")
    private int gender;
    @Column(name = "user_dob")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date userDob;
    @Column
    private String address;
    @Column
    private String highestLevelPlayed;
    @Column
    private int yearsOfPlaying;
    @Column
    private String tShirtSize;
    @Column
    private String batchPreference;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "training_program_payment_id")
    private TrainingProgramPayment trainingProgramPayment;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    public String getPlayerMobile() {
        return playerMobile;
    }

    public void setPlayerMobile(String playerMobile) {
        this.playerMobile = playerMobile;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getUserDob() {
        return userDob;
    }

    public void setUserDob(Date userDob) {
        this.userDob = userDob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHighestLevelPlayed() {
        return highestLevelPlayed;
    }

    public void setHighestLevelPlayed(String highestLevelPlayed) {
        this.highestLevelPlayed = highestLevelPlayed;
    }

    public int getYearsOfPlaying() {
        return yearsOfPlaying;
    }

    public void setYearsOfPlaying(int yearsOfPlaying) {
        this.yearsOfPlaying = yearsOfPlaying;
    }

    public String gettShirtSize() {
        return tShirtSize;
    }

    public void settShirtSize(String tShirtSize) {
        this.tShirtSize = tShirtSize;
    }

    public String getBatchPreference() {
        return batchPreference;
    }

    public void setBatchPreference(String batchPreference) {
        this.batchPreference = batchPreference;
    }

    public TrainingProgramPayment getTrainingProgramPayment() {
        return trainingProgramPayment;
    }

    public void setTrainingProgramPayment(TrainingProgramPayment trainingProgramPayment) {
        this.trainingProgramPayment = trainingProgramPayment;
    }

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
}
