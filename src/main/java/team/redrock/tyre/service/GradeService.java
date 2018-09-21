package team.redrock.tyre.service;


import team.redrock.tyre.util.response.GradeResponse;

public interface GradeService {
    public GradeResponse getGradeInfoFromIf(String stu_num, String id_num);
    public GradeResponse getGradeInfoFromDB(String stu_name, String id_num);
    public void deleteGradeCache();
}
