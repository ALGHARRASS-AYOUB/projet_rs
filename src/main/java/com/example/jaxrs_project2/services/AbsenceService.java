package com.example.jaxrs_project2.services;

public class AbsenceService {
    public double calculerNoteFinale(double moyenne, int absence, int nombreSeance) {
        if(absence==0)
            return moyenne;
        double tauxAbsence = calculerTauxAbscence(absence, nombreSeance);
        return moyenne - tauxAbsence * moyenne;
    }

    public double calculerTauxAbscence(int absence, int nombreSeance){
        if(absence==0)
            return 0;
        double tauxAbsence = Math.max(Math.min(absence / nombreSeance , 1), 0);
        return tauxAbsence;
    }
}
