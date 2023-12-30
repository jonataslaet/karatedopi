package br.com.karatedopi.entities;

public interface UserDetailsProjection {

    Long getId();
    String getFirstname();
    String getLastname();
    String getUsername();
    String getPassword();
    Long getRoleId();
    String getAuthority();
    String getStatus();
}
