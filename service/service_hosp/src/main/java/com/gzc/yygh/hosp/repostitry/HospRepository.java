package com.gzc.yygh.hosp.repostitry;

import com.gzc.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/05  10:04  周二
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public interface HospRepository extends MongoRepository<Hospital,String> {

    Hospital findByHoscode(String hoscode);

    List<Hospital> findByHosnameLike(String name);
}

