package com.example.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.vo.Result;
import com.example.sys.entity.Category;
import com.example.sys.entity.Web;
import com.example.sys.mapper.CategoryMapper;
import com.example.sys.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sys.service.IWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    IWebService webService;

    @Resource
    CategoryMapper categoryMapper;


    /**
     * 得到网站目录列表
     *
     * @return {@link List}<{@link Category}>
     */

    @Override
    public List<Category> getWebList() {
        List<Category> mainCategory = this.baseMapper.getMainCategory();
        for(Category category:mainCategory){
            List<Category> childrenCategory = this.baseMapper.getChildrenCategory(category.getCategoryId());
            category.setChildrenCategory(childrenCategory);
        }
        return mainCategory;
    }

    @Override
    public List<Category> getMainCategory() {
        List<Category> allCategory = this.baseMapper.selectList(null);
        List<Category> parentCategory = categoryMapper.selectList(new LambdaQueryWrapper<Category>().isNotNull(Category::getPid));
        List<Integer> pid = new ArrayList<>();
        for (Category category : parentCategory){
            if (pid.contains(category.getPid())){
                pid.add(category.getPid());
            }
        }
        allCategory.removeAll(pid);
        return allCategory;
    }

    @Override
    public Result<?> deleteCategory(ArrayList<Integer> list) {
        //删除第一个主目录
        list.remove(0);
        List<Integer> mainCategory = this.baseMapper.getMainCategoryId();
        List<Integer> existCategory = this.baseMapper.getExistCategoryId();

        for (Integer i : list) {
            if (mainCategory.contains(i)) {
                return Result.fail("请勿删除主目录");
            }
            if (existCategory.contains(i)) {
                return Result.fail("请勿删除已有Web信息的目录");
            }
            if (!mainCategory.contains(i) && !existCategory.contains(i)) {
                //当list中的id不存在于mainCategory和existCategory当中时，能够进行删除操作
                categoryMapper.deleteById(i);
            }
        }
        return Result.success();
    }
}
