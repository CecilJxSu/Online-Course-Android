package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.QuestionList;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.QuestionListModel;

@PerActivity
public class QuestionListModelDataMapper {
    private final QuestionModelDataMapper questionModelDataMapper;
    @Inject
    public QuestionListModelDataMapper(QuestionModelDataMapper questionModelDataMapper) {
        this.questionModelDataMapper = questionModelDataMapper;
    }

    public QuestionListModel transform(QuestionList questionList) {
        if (questionList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        QuestionListModel questionListModel = new QuestionListModel();
        questionListModel.setTotal(questionList.getTotal());
        questionListModel.setQuestions(questionModelDataMapper.transform(questionList.getQuestions()));

        return questionListModel;
    }
}
