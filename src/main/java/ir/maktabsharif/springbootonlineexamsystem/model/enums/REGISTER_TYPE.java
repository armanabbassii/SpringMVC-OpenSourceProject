package ir.maktabsharif.springbootonlineexamsystem.model.enums;

import java.util.List;

public enum REGISTER_TYPE {
    TEACHER,
    STUDENT,
    ALL,
    ADMIN;


    public static List<REGISTER_TYPE> publicTypes(){
        return List.of(STUDENT,TEACHER);
    }
}
