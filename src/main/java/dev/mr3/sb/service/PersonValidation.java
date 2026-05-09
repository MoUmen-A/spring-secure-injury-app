package dev.mr3.sb.service;

import dev.mr3.sb.model.Person;

/**
 * Centralized validation and normalization for person fields.
 */
public final class PersonValidation {
    private PersonValidation() {
    }

    public static void validateAndNormalize(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        person.setName(normalizeName(person.getName()));
        person.setAge(validateAge(person.getAge()));
        person.setContact_no(validateContactNo(person.getContact_no()));
        person.setAddress(normalizeAddress(person.getAddress()));
    }

    private static String normalizeName(String name) {
        return name == null ? "" : name.trim();
    }

    private static int validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        return age;
    }

    private static String validateContactNo(String contactNo) {
        if (contactNo == null) {
            throw new IllegalArgumentException("Contact number cannot be null");
        }
        return contactNo;
    }

    private static String normalizeAddress(String address) {
        return address == null ? "" : address.trim();
    }
}
