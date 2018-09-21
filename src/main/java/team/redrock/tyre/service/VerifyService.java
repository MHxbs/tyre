package team.redrock.tyre.service;


import team.redrock.tyre.util.response.GradeResponse;

public interface VerifyService {
    public GradeResponse getGrade(String stu_name, String id_num);
}
