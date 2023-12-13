package com.example.jaxrs_project2.resources;

import com.example.jaxrs_project2.data.Context;
import com.example.jaxrs_project2.entities.Student;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/student")
public class StudentResource {
    @GET
    @Path("/students")
    public Response getStudents() {
        return Response.status(200).entity(Context.students).build();
    }


    @POST
    @Path("/")
    public Response addStudent(Student student) {
        if (student == null){
            return Response.status(400).entity("Bad Request").build();
        }

        if(Context.students.stream().filter(item -> item.cne == student.cne).findFirst().isPresent()){
            return Response.status(409).entity("Student with the id: " + student.cne + " already in the list").build();
        }

        Context.students.add(student);

        return Response.status(200).entity("Student: " + student.firstname + " " + student.lastname + " added to the list").build();
    }


    @GET
    @Path("/{studentCne}")
    public Response getStudent(@PathParam("studentCne") String studentCne) {
        System.out.println(studentCne);
        try{
            var optional = Context.students.stream().filter(item -> item.cne.equals(studentCne)).findFirst();

            if(optional.isPresent()){
                Student student = optional.get();

                return Response.status(200).entity(student).build();
            }

            return Response.status(404).entity("Student Not Found").build();
        }catch (Exception ex){
            return Response.status(500).entity("Internal Server Error").build();
        }
    }

    @DELETE
    @Path("/{studentCne}")
    public Response deleteStudent(@PathParam("studentCne") String studentCne) {
        try{
            var optional = Context.students.stream().filter(item -> item.cne.equals(studentCne)).findFirst();

            if(optional.isPresent()){
                Student student = optional.get();

                Context.students = Context.students.stream().filter(item -> !item.cne.equals(studentCne)).collect(Collectors.toList());


                return Response.status(200).entity(student).build();

            }

            return Response.status(404).entity("Student Not Found").build();
        }catch (Exception ex){
            return Response.status(500).entity("Internal Server Error").build();
        }
    }

    @PUT
    @Path("/update/{studentId}/{moyenne}")
    public Response updateStudent(@PathParam("studentId") String studentId, @PathParam("moyenne") double moyenne){
        try{
            var optional = Context.students.stream().filter(item -> item.cne.equals(studentId)).findFirst();

            if(optional.isPresent()){
                Student student = optional.get();
                student.moyenne = moyenne;
                return Response.status(200).entity(student.absence).build();
            }

            return Response.status(404).entity("Student Not Found").build();
        }catch (Exception ex){
            return Response.status(500).entity("Internal Server Error").build();
        }
    }
    // Ajoutez d'autres méthodes pour les opérations Update, Delete, BlackListCreate
}