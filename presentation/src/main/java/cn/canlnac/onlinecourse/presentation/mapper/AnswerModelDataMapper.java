package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Answer;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.AnswerModel;

@PerActivity
public class AnswerModelDataMapper {
    private final LoginModelDataMapper loginModelDataMapper;
    @Inject
    public AnswerModelDataMapper(LoginModelDataMapper loginModelDataMapper) {
        this.loginModelDataMapper = loginModelDataMapper;
    }

    public AnswerModel transform(Answer answer) {
        if (answer == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        AnswerModel answerModel = new AnswerModel();
        answerModel.setId(answer.getId());
        answerModel.setAnswer(answer.getAnswer());
        answerModel.setDate(answer.getDate());
        answerModel.setQuestionId(answer.getQuestionId());
        answerModel.setUser(loginModelDataMapper.transform(answer.getUser()));

        return answerModel;
    }
}
