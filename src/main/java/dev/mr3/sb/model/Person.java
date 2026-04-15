package dev.mr3.sb.model;

public abstract class Person {
    private String name;
    private int age;
    private boolean gender;// camal case , 
    private String contact_no; // 11-digit string quanitive , qualili
    private String address;
   
    public Person(String name, int age, boolean gender, String contact_no, String address) {
        setName(name);
        setAge(age);
        setGender(gender);
        setContact_no(contact_no);
        setAddress(address);
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    /**
     * Retrieves the person's gender.
     * @return true if Male, false if Female
     */
    public boolean isGender() {
        return gender;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getAddress() {
        return address;
    }

    protected void setName(String name) {
        this.name = name == null ? "" : name.trim();
    }

    protected void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    protected void setGender(boolean gender) {
        this.gender = gender;
    }

    protected void setContact_no(String contact_no) {
        if (contact_no == null ) {
            throw new IllegalArgumentException("Contact number must be exactly 11 digits");
        }
        this.contact_no = contact_no;
    }

    protected void setAddress(String address) {
        this.address = (address == null) ? "" : address.trim();
    }
}