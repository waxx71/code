package com.coded.Controller;

import com.coded.repo.CodeRepo;
import com.coded.Entity.Code;
import com.coded.repo.CodeRepo;
import org.springframework.data.repository.CrudRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CodeController
 * @Description TODO
 * @Author Administrator
 * @Version 1.0
 */
@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CodeRepo codeRepo;

    /**
     * 保存
     * @param name
     * @param description
     * @return
     */
    @RequestMapping("/save")
    public String save(String name, String description){
        Code code = new Code(System.currentTimeMillis(), name, description);
        CodeRepo.save(code);
        return "success";
    }

    /**
     * 更新
     * @param id
     * @param name
     * @param description
     * @return
     */
    @RequestMapping("/update")
    public String update(Long id, String name, String description){
        Code code = new Code(id, name, description);
        CodeRepo.save(code);
        return "success";
    }

    /**
     * 根据id查找
     * @param id
     * @return
     */
    @RequestMapping("/selectOne")
    public Optional<Code> selectOne(Long id){
        return CodeRepo.findById(id);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Long id){
        CodeRepo.deleteById(id);
        return "success";
    }

    /**
     * 限定关键词的搜索
     * @param name
     * @param pageable
     * @return
     */
    @RequestMapping("/getGoodsListByItem")
    public List<Code> getGoodsListByItem(String name, @PageableDefault(page=0, value = 10) Pageable pageable){
        //设置分页
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", name);
        Page<Code> goodsPage = CodeRepo.search(queryBuilder, pageable);
        return goodsPage.getContent();
    }

    /**
     * 模糊搜索
     * @param condition
     * @return
     */
    @RequestMapping("/getCodeList")
    public List<Code> getCodeList(String condition){
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(condition);
        Iterable<Code> searchResult = CodeRepo.search(builder);
        List<Code> CodeList = new ArrayList<>();
        Iterator<Code> iterator = searchResult.iterator();
        while (iterator.hasNext()){
            CodeList.add(iterator.next());
        }
        return CodeList;
    }
}
