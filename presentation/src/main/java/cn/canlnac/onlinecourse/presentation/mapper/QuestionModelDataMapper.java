package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Question;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;

@PerActivity
public class QuestionModelDataMapper {
    @Inject
    public QuestionModelDataMapper() {}

    public QuestionModel transform(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        QuestionModel questionModel = new QuestionModel();
        questionModel.setAnswer(question.getAnswer());
        questionModel.setExplains(question.getExplains());
        questionModel.setIndex(question.getIndex());
        questionModel.setItem(question.getItem());
        questionModel.setQuestion(question.getQuestion());
        questionModel.setScore(question.getScore());
        questionModel.setType(question.getType());
        questionModel.setUrl(question.getUrl());

        return questionModel;
    }
}
