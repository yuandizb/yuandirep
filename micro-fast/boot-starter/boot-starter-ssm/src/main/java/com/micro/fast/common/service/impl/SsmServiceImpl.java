package com.micro.fast.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.micro.fast.boot.starter.common.exception.SystemException;
import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.common.dao.SsmMapper;
import com.micro.fast.common.service.SsmService;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * T表示的是实体类型,ID表示的是主键类型，DAO表示的是dao层的类型
 * @author lsy
 */
@Setter
public  class SsmServiceImpl<T, ID extends Serializable, MAPPER extends SsmMapper<T,ID>> implements SsmService<T,ID>{

    /**
     * 这个属性一定要使用子类的覆盖,不然会报空指针
     */
    private MAPPER mapper;


    @Override
    public ServerResponse add(T pojo) {
        int insert = mapper.insertSelective(pojo);
        if (insert!=1){
            throw  new SystemException(BaseConst.SystemResponse.DATABASE_INSERT_FAILURE);
        }
        return ServerResponse.successMsgData("添加成功",pojo);
    }

    @Override
    public ServerResponse getById(ID id)
    {
        T t = mapper.selectByPrimaryKey(id);
        return ServerResponse.successData(t);
    }

    @Override
    public  ServerResponse getByCondition(T pojo,int pageNum,int pageSize,String orderBy){
        // 判断是否排序
        if (StringUtils.isBlank(orderBy)) {
            PageHelper.startPage(pageNum, pageSize);
        }else{
            // 是否排序的标记
            boolean isOrderBy = false;
            // 判断传入的排序条件是否正确
            String[] split = orderBy.split(BaseConst.ORDER_SPLIT);
            if (split.length==2){
                Class<?> aClass = pojo.getClass();
                Field[] declaredFields = aClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    String name = declaredField.getName();
                    if (name.equals(split[0])){
                        if (split[1].equals("desc")||split[1].equals("asc")){
                            PageHelper.startPage(pageNum, pageSize,split[0]+BaseConst.ORDER_CONTACT+split[1]);
                            isOrderBy = true;
                            break;
                        }
                    }
                }
            }
            //如果排序条件不正确就简单分页查询
            if (isOrderBy == false){
                PageHelper.startPage(pageNum, pageSize);
            }
        }
        List<T> list = mapper.selectByCondition(pojo);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.successData(pageInfo);
    }

    @Override
    public ServerResponse update(T pojo) {
        int i = mapper.updateByPrimaryKeySelective(pojo);
        if (i<1){
            ServerResponse.errorMsg("更新失败");
        }
        return ServerResponse.successMsg("更新成功");
    }

    @Override
    public ServerResponse deleteById(ID id) {
        int i = mapper.deleteByPrimaryKey(id);
        if (i<1){
            ServerResponse.errorMsg("删除失败");
        }
        return ServerResponse.successMsg("删除成功");
    }

    @Override
    public ServerResponse deleteByIds(List<ID> ids) {
        int i = mapper.deleteByPrimaryKeys(ids);
        if (i<1){
            ServerResponse.errorMsg("没有删除任何记录");
        }
        return ServerResponse.successMsg("删除成功");
    }
}
