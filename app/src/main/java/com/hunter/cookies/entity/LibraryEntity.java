package com.hunter.cookies.entity;

public class LibraryEntity {

    private String name;
    private String author;
    private String license;
    private String link;

    public LibraryEntity(String name, String author, String license, String link) {
        this.name = name;
        this.author = author;
        this.license = license;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
