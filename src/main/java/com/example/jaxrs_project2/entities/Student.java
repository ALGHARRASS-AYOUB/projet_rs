package com.example.jaxrs_project2.entities;

enum Level{
    CP1,
    CP2,
    CI1,
    CI2,
    CI3
}
public class Student {
    public String cne;

    public String lastname;

    public String firstname;

    public int absence = 0;

    public double moyenne = 0;

    public double noteFinale = 0;

    public Level level = Level.CP1;
}
