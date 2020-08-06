package com.coded.repo;

import com.coded.Entity.Code;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @ClassName CodeRepo
 * @Description TODO
 * @Author Administrator
 * @Version 1.0
 */
@Component
public interface CodeRepo extends ElasticsearchRepository<Code, Long> {
}
