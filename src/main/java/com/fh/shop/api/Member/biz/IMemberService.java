package com.fh.shop.api.Member.biz;

import com.fh.shop.api.Member.po.Member;
import com.fh.shop.api.common.ServerResponse;

import java.io.UnsupportedEncodingException;

public interface IMemberService {
    ServerResponse register(Member member) throws UnsupportedEncodingException;

    ServerResponse query(Member member);

    ServerResponse login(Member member) throws UnsupportedEncodingException;
}
