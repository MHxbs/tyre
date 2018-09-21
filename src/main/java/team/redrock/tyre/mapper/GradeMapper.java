package team.redrock.tyre.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.redrock.tyre.entity.GradeInfo;

import java.util.List;

@Mapper
@Repository
@Transactional
public interface GradeMapper {

    @Insert("Insert into grade(course,grade,property,status,student,term) values(#{course},#{grade},#{property},#{status},#{student},#{term})")
    void insert(GradeInfo gradeInfo);

    @Select({"select * from grade where student = #{student}"})
    List<GradeInfo> SelectByStuNum(String Student);


}
