package com.fh.shop.api.Cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.Cart.vo.Cart;
import com.fh.shop.api.Cart.vo.CartItem;
import com.fh.shop.api.Product.mapper.ProductMapper;
import com.fh.shop.api.Product.po.Product;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.utils.BigDecimalUtil;
import com.fh.shop.api.utils.KeyUtils;
import com.fh.shop.api.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ServerResponse cart(Long memberId, Long productId, int num) {
        //判断商品是否存在
        Product product = productMapper.selectById(productId);
        if (product==null){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_NULL);
        }
        //判断商品是否上架
        if (product.getStatus()==0){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_DOWN);

        }
        //判断当前登录用户是否有购物车
        String cartJson = RedisUtil.get(KeyUtils.cartKey(memberId));
        Cart cart =null;
        if(cartJson!=null){
            cart = JSONObject.parseObject(cartJson, Cart.class);
        }
        //如果有购物车
        if (cart!=null){
            List<CartItem> cartItemList= cart.getItemList();
            CartItem cartItemOne=null;
            //判断该商品在购物车里有没有
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getProductId()==productId){
                    cartItemOne=cartItem;
                    break;
                }
            }
            //如果商品在购物车中
            if (cartItemOne!=null){
                //更新购物车中的商品
                cartItemOne.setNum( cartItemOne.getNum()+num);
                //判断更新后的商品数量是否小于等于0
                if(cartItemOne.getNum()<=0){
                    //如果商品数量是否小于等于0将这个商品从集合中移除
                    cartItemList.remove(cartItemOne);
                }else {
                    //如果不小于 重新计算商品的小计
                    BigDecimal subPrice = BigDecimalUtil.cheng(cartItemOne.getNum() + "", cartItemOne.getPrice().toString());
                    cartItemOne.setSubPrice(subPrice);
                }
                //判断集合里是否有值
                if (cartItemList.size()==0){
                    //如果没有值 将删除整个购物车
                   RedisUtil.del(KeyUtils.cartKey(memberId));
                }else {
                    //如果有值 则更新购物车
                    updateCart(memberId, cart);
                }
            }
            //如果商品没有在购物车中
            else {
                if(num<=0){
                    return ServerResponse.error(ResponseEnum.CART_NUM_IS_ERROR);
                }
                CartItem cartItem = buildCartItem(productId, num, product);
                cartItemList.add(cartItem);
                //更新购物车的总价格 总数量
                updateCart(memberId, cart);
            }

            //如果没有购物车
        }else {
            if(num<=0){
                return ServerResponse.error(ResponseEnum.CART_NUM_IS_ERROR);
            }
            //创建一个购物车
            //将商品添加到购物车中
            //更新购物车
            CartItem cartItem = buildCartItem(productId, num, product);
            Cart cart1 = new Cart();
            List<CartItem> itemList = cart1.getItemList();
            itemList.add(cartItem);
            //更新购物车的总价格 总数量
            updateCart(memberId, cart1);
        }

        return ServerResponse.success();
    }

    @Override
    public ServerResponse cartList(Long memberId) {
        String cartJson = RedisUtil.get(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        return ServerResponse.success(cart);
    }

    @Override
    public ServerResponse delProduct(Long memberId, Long productId) {
        String cartJson = RedisUtil.get(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> itemList = cart.getItemList();
        for (CartItem cartItem : itemList) {
            if (cartItem.getProductId()==productId){
                itemList.remove(cartItem);
                break;
            }
        }
        //判断集合里是否有值
        if (itemList.size()==0){
            //如果没有值 将删除整个购物车
            RedisUtil.del(KeyUtils.cartKey(memberId));
        }else {
            //如果有值 则更新购物车
            updateCart(memberId, cart);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse cartItemCount(Long memberId) {
        String cartJson = RedisUtil.get(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        if (cart==null){
            return  ServerResponse.error();
        }
        Integer totalNum = cart.getTotalNum();
        return ServerResponse.success(totalNum);
    }

    @Override
    public ServerResponse deletes(Long memberId, List<Long> arr) {
        String cartJson = RedisUtil.get(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> itemList = cart.getItemList();
        Iterator<CartItem> iterator = itemList.iterator();
        while (iterator.hasNext()){
            CartItem next = iterator.next( );
            for (Long id:arr) {
                if(next.getProductId().longValue()==id.longValue()){
                    iterator.remove();
                }
            }
        }
        //判断集合里是否有值
        if (itemList.size()==0){
            //如果没有值 将删除整个购物车
            RedisUtil.del(KeyUtils.cartKey(memberId));
        }else {
            //如果有值 则更新购物车
            updateCart(memberId, cart);
        }
        return ServerResponse.success();
    }

    private CartItem buildCartItem(Long productId, int num, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setNum(num);
        cartItem.setImgUrl(product.getMainImagePath());
        cartItem.setPrice(product.getPrice());
        cartItem.setProductId(productId);
        cartItem.setProductName(product.getProductName());
        BigDecimal subPrice = BigDecimalUtil.cheng(cartItem.getNum() + "", cartItem.getPrice().toString());
        cartItem.setSubPrice(subPrice);
        return cartItem;
    }

    public void updateCart(Long memberId, Cart cart) {
        List<CartItem> itemList = cart.getItemList();
        //更新购物车的总价格 总数量
        int totalNum=0;
        BigDecimal totalPrice=new BigDecimal(0);
        for (CartItem cartItem : itemList) {
            totalNum+=cartItem.getNum();
            totalPrice = BigDecimalUtil.sum(totalPrice.toString(), cartItem.getSubPrice().toString());
        }
        cart.setTotalNum(totalNum);
        cart.setTotalPrice(totalPrice);
        String cartNewJson = JSONObject.toJSONString(cart);
        RedisUtil.set(KeyUtils.cartKey(memberId),cartNewJson);
    }
}
