package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.AnswerEntity;
import cn.canlnac.onlinecourse.domain.Answer;

/**
 * 回答实体类转换.
 */
@Singleton
public class AnswerEntityDataMapper {
    private LoginEntityDataMapper loginEntityDataMapper;

    @Inject
    public AnswerEntityDataMapper(LoginEntityDataMapper loginEntityDataMapper) {
        this.loginEntityDataMapper = loginEntityDataMapper;
    }

    /**
     * 转换
     * @param answerEntity 回答实体类
     * @return
     */
    public Answer transform(AnswerEntity answerEntity) {
        Answer answer = null;
        if (answerEntity != null) {
            answer = new Answer();
            answer.setAnswer(answerEntity.getAnswer());
            answer.setDate(answerEntity.getDate());
            answer.setId(answerEntity.getId());
            answer.setQuestionId(answerEntity.getQuestionId());
            answer.setUser(loginEntityDataMapper.transform(answerEntity.getUser()));
        }
        return answer;
    }
}
