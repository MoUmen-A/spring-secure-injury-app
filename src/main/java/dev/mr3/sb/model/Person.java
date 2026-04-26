package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private boolean gender;

    @Column
    private String contact_no;

    @Column
    private String address;

    public Person() {
    }
    public Person(String name, int age, boolean gender, String contact_no, String address) {
        setName(name);
        setAge(age);
        setGender(gender);
        setContact_no(contact_no);
        setAddress(address);
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public boolean isGender() {
        return gender;
    }
    public String getContact_no() {
        return contact_no;
    }
    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name.trim();
    }
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public void setContact_no(String contact_no) {
        if (contact_no == null) {
            throw new IllegalArgumentException("Contact number cannot be null");
        }
        this.contact_no = contact_no;
    }
    public void setAddress(String address) {
        this.address = (address == null) ? "" : address.trim();
    }
}