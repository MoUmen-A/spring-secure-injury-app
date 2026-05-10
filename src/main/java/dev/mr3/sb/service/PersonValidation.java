package dev.mr3.sb.service;
 
import dev.mr3.sb.model.Person;
 
/**
 * Centralized validation and normalization for person fields.
 * Simplicity is a must.
 */
public final class PersonValidation {
 
    private PersonValidation() {
    }
 
    public static void validateAndNormalize(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        person.setName(normalize(person.getName()));
        person.setEmail(normalize(person.getEmail()).toLowerCase());
        person.setAge(validateAge(person.getAge()));
        person.setContact_no(normalize(person.getContact_no()).replaceAll(" ", ""));
        person.setAddress(normalize(person.getAddress()));
    }
 
    private static String normalize(String s) {
        if (s == null) return "";
        return s.trim().replaceAll("\\s+", " ");
    }
 
    private static int validateAge(int age) {
        if (age < 0 || age > 90) {
            throw new IllegalArgumentException("Age must be between 0 and 90");
        }
        return age;
    }
}
