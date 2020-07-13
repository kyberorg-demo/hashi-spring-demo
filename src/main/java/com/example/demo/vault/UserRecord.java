package com.example.demo.vault;

@SuppressWarnings("unused") //all getters must be present, otherwise field without getter in not saved in Vault
public class UserRecord {

    String username;
    String password;
    String customValue;

    /**
     * Needed for Vault Ops
     */
    @SuppressWarnings("unused")
    public UserRecord() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCustomValue() {
        return customValue;
    }
}