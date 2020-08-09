package com.fh.shop.api.Cart.biz;

import com.fh.shop.api.common.ServerResponse;

import java.util.List;

public interface CartService {
    ServerResponse cart(Long memberId, Long productId, int num);

    ServerResponse cartList(Long memberId);

    ServerResponse delProduct(Long memberId,Long productId);

    ServerResponse cartItemCount(Long memberId);

    ServerResponse deletes(Long memberId, List<Long> arr);
}
