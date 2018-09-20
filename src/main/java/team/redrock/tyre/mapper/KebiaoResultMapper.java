package team.redrock.tyre.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import team.redrock.tyre.entity.CourseInfo;
import team.redrock.tyre.entity.KebiaoResult;

import java.util.List;

public interface KebiaoResultMapper {


    @Insert("insert into kebiaoResult (status,success,version,term,stuNum,data,nowWeek)" +
            "  values (#{status},#{success},#{version},#{term},#{stuNum},#{data},#{nowWeek})")
    void insertOne(KebiaoResult kebiaoResult);

    @Select("select * from kebiaoResult where stuNum=#{stuNum}")
    KebiaoResult selectOnrByStuNum(String stuNum);


    @Update("update kebiaoResult set status=#{status},success=#{success},version=#{version},term=#{term}," +
            "data=#{data},nowWeek=#{nowWeek} where stuNum=#{stuNum}")
    void updateOne(KebiaoResult kebiaoResult);

    @Update("update kebiaoResult set data=#{data} where stuNum=#{stuNum}")
    void updateData(@Param("data") String data,@Param("stuNum") String stuNum);

}
