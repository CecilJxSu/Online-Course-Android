package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.QuestionEntity;
import cn.canlnac.onlinecourse.data.net.RestApiConnection;
import cn.canlnac.onlinecourse.domain.Question;

/**
 * 问题实体类转换.
 */
@Singleton
public class QuestionEntityDataMapper {

    @Inject
    public QuestionEntityDataMapper() {}

    /**
     * 转换
     * @param questionEntity 问题实体类
     * @return
     */
    public Question transform(QuestionEntity questionEntity) {
        Question question = null;
        if (questionEntity != null) {
            question = new Question();
            question.setAnswer(questionEntity.getAnswer());
            question.setExplains(questionEntity.getExplains());
            question.setItem(questionEntity.getItem());
            question.setQuestion(questionEntity.getQuestion());
            question.setType(questionEntity.getType());
            question.setUrl(RestApiConnection.API_FILE + "/" + questionEntity.getUrl());
        }
        return question;
    }

    /**
     * 转换列表
     * @param questionEntityList    问题实体类列表
     * @return
     */
    public List<Question> transform(List<QuestionEntity> questionEntityList) {
        List<Question> questionList = new ArrayList<>(questionEntityList.size());
        Question question;
        for (QuestionEntity questionEntity : questionEntityList) {
            question = transform(questionEntity);
            if (question != null) {
                questionList.add(question);
            }
        }

        return questionList;
    }
}
