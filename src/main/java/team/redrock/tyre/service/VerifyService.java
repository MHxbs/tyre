package team.redrock.tyre.service;


import team.redrock.tyre.domain.response.GradeResponse;

public interface VerifyService {
    public GradeResponse getGrade(String stu_name, String id_num);
}
