package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Paper;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.PaperModel;

@PerActivity
public class PaperModelDataMapper {
    QuestionModelDataMapper questionModelDataMapper;
    @Inject
    public PaperModelDataMapper(QuestionModelDataMapper questionModelDataMapper) {
        this.questionModelDataMapper = questionModelDataMapper;
    }

    public PaperModel transform(Paper paper) {
        if (paper == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        PaperModel paperModel = new PaperModel();
        paperModel.setScore(paper.getScore());
        paperModel.setType(paper.getType());
        paperModel.setQuestions(questionModelDataMapper.transform(paper.getQuestions()));
        return paperModel;
    }

    public List<PaperModel> transform(List<Paper> paperList) {
        List<PaperModel> paperModelList = new ArrayList<>(paperList.size());
        PaperModel paperModel;
        for (Paper paper : paperList) {
            paperModel = transform(paper);
            if (paper != null) {
                paperModelList.add(paperModel);
            }
        }

        return paperModelList;
    }
}
