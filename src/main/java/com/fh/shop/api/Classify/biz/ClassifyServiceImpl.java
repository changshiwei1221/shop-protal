package com.fh.shop.api.Classify.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.Classify.mapper.ClassifyMapper;
import com.fh.shop.api.Classify.po.Classify;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyMapper classifyMapper;
    @Override
    public ServerResponse queryClassIfylist() {
        String classIfyList = RedisUtil.get("ClassIfyList");
        if (classIfyList!=null){
            List<Classify> classifyList = JSONObject.parseArray(classIfyList, Classify.class);
            return ServerResponse.success(classifyList);
        }
        List<Classify> classifyList = classifyMapper.selectList(null);
        String jsonString = JSONObject.toJSONString(classifyList);
        RedisUtil.set("ClassIfyList",jsonString);
        return ServerResponse.success(classifyList);
    }
}
