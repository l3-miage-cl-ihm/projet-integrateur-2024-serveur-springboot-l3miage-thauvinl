package fr.uga.l3miage.integrator.exceptions.rest;

public class BadRequestRestException extends RuntimeException{
    public BadRequestRestException(String format){super(format);}
}
