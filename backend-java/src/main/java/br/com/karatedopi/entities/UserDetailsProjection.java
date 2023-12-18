package br.com.karatedopi.entities;

public interface UserDetailsProjection {

    Long getId();
    String getUsername();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}
