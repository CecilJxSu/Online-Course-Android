package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

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
        questionModel.setItem(question.getItem());
        questionModel.setQuestion(question.getQuestion());
        questionModel.setType(question.getType());
        questionModel.setUrl(question.getUrl());

        return questionModel;
    }

    public List<QuestionModel> transform(List<Question> questionList) {
        List<QuestionModel> questionModelList = new ArrayList<>(questionList.size());
        QuestionModel questionModel;
        for (Question question : questionList) {
            questionModel = transform(question);
            if (question != null) {
                questionModelList.add(questionModel);
            }
        }

        return questionModelList;
    }
}
