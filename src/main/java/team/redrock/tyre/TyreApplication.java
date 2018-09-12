package team.redrock.tyre;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("team.redrock.tyre.mapper")
public class TyreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TyreApplication.class, args);
	}
}
