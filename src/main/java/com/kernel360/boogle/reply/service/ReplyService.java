package com.kernel360.boogle.reply.service;

import com.kernel360.boogle.bookreport.db.BookReportEntity;
import com.kernel360.boogle.bookreport.db.BookReportRepository;
import com.kernel360.boogle.member.db.MemberEntity;
import com.kernel360.boogle.reply.db.ReplyEntity;
import com.kernel360.boogle.reply.db.ReplyRepository;
import com.kernel360.boogle.reply.exception.EmptyContentException;
import com.kernel360.boogle.reply.model.ReplyDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BookReportRepository bookReportRepository;

    public void createReply(ReplyDTO reply, @AuthenticationPrincipal MemberEntity member) {

        if (reply.getReplyEntity().getContent().replaceAll("\\s", "").equals("")) {
            throw new EmptyContentException();
        }

        reply.getReplyEntity().setMemberEntity(member);
        replyRepository.save(reply.getReplyEntity());
    }

    public Optional<ReplyEntity> getReplyById(Long id) {
        return replyRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ReplyDTO> getRepliesByBookReportId(Long bookReportId) {
        BookReportEntity bookReport = bookReportRepository.findById(bookReportId).get();
        return replyRepository.findAllByBookReportEntity(bookReport)
                .stream()
                .map(ReplyDTO::from)
                .toList();
    }

    public Optional<List<ReplyEntity>> getRepliesByParentReplyId(Long parentReplyId) {
        return replyRepository.findAllByParentReplyId(parentReplyId);
    }

    public void updateReply(ReplyDTO reply, @AuthenticationPrincipal MemberEntity member) {

        if (reply.getReplyEntity().getContent().replaceAll("\\s", "").equals("")) {
            throw new EmptyContentException();
        }

        reply.getReplyEntity().setMemberEntity(member);
        replyRepository.save(reply.getReplyEntity());
    }

    public void deleteReply(ReplyDTO reply) {

        replyRepository.deleteById(reply.getId());
    }

    public int getAllRepliesCount(Long memberId) {

        return 5;
    }

    @Transactional(readOnly = true)
    public List<ReplyDTO> getRecentRepliesByMemberId(Long memberId, int cnt) {
        return replyRepository.findAllByMemberId(memberId, cnt)
                .stream()
                .map(ReplyDTO::from)
                .toList();
    }
}
