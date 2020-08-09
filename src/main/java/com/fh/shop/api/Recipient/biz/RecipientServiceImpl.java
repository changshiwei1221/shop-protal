package com.fh.shop.api.Recipient.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.Member.vo.MemberVo;
import com.fh.shop.api.Recipient.mapper.RecipientMapper;
import com.fh.shop.api.Recipient.po.Recipient;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipientServiceImpl implements RecipientService {

    @Autowired
    private RecipientMapper recipientMapper;

    @Override
    public ServerResponse recipientList(MemberVo memberVo) {
        Long memberVoId = memberVo.getId();
        QueryWrapper<Recipient> wrapper = new QueryWrapper<>();
        wrapper.eq("memberId",memberVoId);
        List<Recipient> recipientList = recipientMapper.selectList(wrapper);
        return ServerResponse.success(recipientList);
    }

    @Override
    public ServerResponse addRecipient(Recipient recipient) {
        if (recipient.getId()!=null){
            recipientMapper.updateById(recipient);
        }else {
            recipientMapper.insert(recipient);
        }

        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteRecipient(Long id, Long memberVoId) {

        QueryWrapper<Recipient> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        wrapper.eq("memberId",memberVoId);
        recipientMapper.delete(wrapper);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse recipientOne(Long id, Long memberVoId) {

        QueryWrapper<Recipient> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        wrapper.eq("memberId",memberVoId);
        Recipient selectOne = recipientMapper.selectOne(wrapper);
        return ServerResponse.success(selectOne);
    }
}
