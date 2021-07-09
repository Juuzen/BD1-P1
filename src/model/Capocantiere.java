package model;

import dao.CapocantiereDao;
import org.jetbrains.annotations.Nullable;

public class Capocantiere {
    String username;
    String password;
    String name;
    String surname;
    String fiscalCode;
    Integer id;
    @Nullable
    Integer cantiereId;

    public Capocantiere(String username, String password, String name, String surname, String fiscalCode, Integer id, @Nullable Integer cantiereId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.fiscalCode = fiscalCode;
        this.id = id;
        this.cantiereId = cantiereId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCantiereId() {
        return cantiereId;
    }

    public void setCantiereId(Integer cantiereId) {
        this.cantiereId = cantiereId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }
}
