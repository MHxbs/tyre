package team.redrock.tyre.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import team.redrock.tyre.entity.CourseInfo;
import team.redrock.tyre.entity.KebiaoResult;

import java.util.List;

public interface KebiaoResultMapper {


    @Insert("insert into kebiaoResult (status,success,version,term,stuNum,data,nowWeek)" +
            "  values (#{status},#{success},#{version},#{term},#{stuNum},#{data},#{nowWeek})")
    void insertOne(KebiaoResult kebiaoResult);

    @Select("select * from kebiaoResult where stuNum=#{stuNum}")
    KebiaoResult selectOnrByStuNum(String stuNum);

}
