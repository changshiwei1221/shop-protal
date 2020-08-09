package com.fh.shop.api.Recipient.biz;

import com.fh.shop.api.Member.vo.MemberVo;
import com.fh.shop.api.Recipient.po.Recipient;
import com.fh.shop.api.common.ServerResponse;

public interface RecipientService {
    ServerResponse recipientList(MemberVo memberVo);

    ServerResponse addRecipient(Recipient recipient);

    ServerResponse deleteRecipient(Long id, Long memberVoId);

    ServerResponse recipientOne(Long id, Long memberVoId);
}
