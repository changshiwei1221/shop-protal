package com.fh.shop.api.Product.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.Product.mapper.ProductMapper;
import com.fh.shop.api.Product.po.Product;
import com.fh.shop.api.Product.vo.ProductVo;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.utils.EmailHelper;
import com.fh.shop.api.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private EmailHelper emailHelper;
    @Override
    public ServerResponse queryHotList() {
        String queryHotList = RedisUtil.get("queryHotList");
        if (StringUtils.isNotBlank(queryHotList)){
            List<ProductVo> productList = JSONObject.parseArray(queryHotList, ProductVo.class);
            return ServerResponse.success(productList);
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","productName","price","mainImagePath","stock");
        queryWrapper.eq("isHot",1);
        queryWrapper.eq("status",1);
        List<Product> productList = productMapper.selectList(queryWrapper);
        ArrayList<ProductVo> voArrayList = new ArrayList<>();
        for (Product product : productList) {
            ProductVo productVo = new ProductVo();
            productVo.setId(product.getId());
            productVo.setPrice(product.getPrice().toString());
            productVo.setProductName(product.getProductName());
            productVo.setMainImagePath(product.getMainImagePath());
            productVo.setStock(product.getStock());
            voArrayList.add(productVo);
        }
        if (voArrayList!=null){
            String jsonString = JSONObject.toJSONString(voArrayList);
            RedisUtil.set("queryHotList",jsonString);
        }
        return ServerResponse.success(voArrayList);
    }

    @Override
    public ServerResponse sendMail() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("stock",10);
        List<Product> productList = productMapper.selectList(queryWrapper);
        StringBuilder content = new StringBuilder("<html><head></head><body><h2>title</h2>");
        content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>id</th><th>商品名</th><th>商品库存</th></tr>");
        for (Product product : productList) {
            content.append("<tr>");
            content.append("<td>" + product.getId() + "</td>"); //第一列
            content.append("<td>" + product.getProductName() + "</td>"); //第二列
            content.append("<td>" + product.getStock() + "</td>"); //第三列
            content.append("</tr>");
        }
        content.append("</table>");
        content.append("</body></html>");
        emailHelper.sendEmail("409112398@qq.com","库存预警",content.toString());
        return ServerResponse.success();
    }
}
