package com.example.jaxrs_project2.resources;

import com.example.jaxrs_project2.data.Context;
import com.example.jaxrs_project2.entities.Student;
import com.example.jaxrs_project2.services.AbsenceService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/absence")
public class AbsenceReserouce {
    private AbsenceService absenceService;
    public AbsenceReserouce(){
        this.absenceService = new AbsenceService();
    }

    @PUT
    @Path("/addAbsence/{studentId}")
    public Response addStudentAbsence(@PathParam("studentId") String studentId){
        try{
            var optional = Context.students.stream().filter(item -> item.cne.equals(studentId)).findFirst();

            if(optional.isPresent()){
                Student student = optional.get();
                student.absence += 4;
                return Response.status(200).entity(student.absence).build();
            }

            return Response.status(404).entity("Student Not Found").build();
        }catch (Exception ex){
            return Response.status(500).entity("Internal Server Error").build();
        }
    }

    @GET
    @Path("/blackList/{studentId}")
    @Produces("application/json")
    public Response getAbsenceRate(@PathParam("studentId") String studentId) {
        // Implémentation de l'opération Read
        try{
            var optional = Context.students.stream().filter(item -> item.cne.equals(studentId) ).findFirst();

            if(optional.isPresent()){
                Student student = optional.get();
                return Response.status(200).entity(student.absence).build();
            }

            return Response.status(404).entity("Student Not Found").build();
        }catch (Exception ex){
            return Response.status(500).entity("Internal Server Error").build();
        }
    }

    @GET
    @Path("/generateBlackList/{nombreSeance}/{seuil}")
    @Produces("application/json")
    public Response blackListCreate(@PathParam("nombreSeance") int nombreSeance, @PathParam("seuil") double seuil) {
        // Implémentation de l'opération Read

        try{
            var optional = Context.students.stream().filter(item -> this.absenceService.calculerTauxAbscence(item.absence, nombreSeance) >= seuil);

            List<Student> students = optional.collect(Collectors.toList());

            Context.blackList = new ArrayList<>();

            for (Student student: students) {
                Context.blackList.add(student.toString());
            }

            return Response.status(200).entity(students).build();
        }catch (Exception ex){
            return Response.status(500).entity("Internal Server Error").build();
        }
    }

    @PUT
    @Path("/noteFinal/{nombreSeance}")
    @Produces("application/json")
    public Response calculateFinalNote(@PathParam("nombreSeance") int nombreSeance) {
        // Implémentation de l'opération Read
        try{
            var optional = Context.students.stream().map(item -> {
                item.noteFinale = this.absenceService.calculerNoteFinale(item.moyenne, item.absence, nombreSeance);

                return item;
            });

            for(int i = 0; i < Context.students.size(); i++){
                Student student = Context.students.get(i);
                student.noteFinale = this.absenceService.calculerNoteFinale(student.moyenne, student.absence, nombreSeance);
            }

            return Response.status(200).entity(Context.students).build();
        }catch (Exception ex){
            return Response.status(500).entity("Internal Server Error").build();
        }
    }
}
