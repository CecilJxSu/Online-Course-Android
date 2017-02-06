package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.QuestionListEntity;
import cn.canlnac.onlinecourse.domain.QuestionList;

/**
 * 小测列表实体类转换.
 */
@Singleton
public class QuestionListEntityDataMapper {
    private PaperEntityDataMapper paperEntityDataMapper;

    @Inject
    public QuestionListEntityDataMapper(PaperEntityDataMapper paperEntityDataMapper) {
        this.paperEntityDataMapper = paperEntityDataMapper;
    }

    /**
     * 转换
     * @param questionListEntity 小测列表实体类
     * @return
     */
    public QuestionList transform(QuestionListEntity questionListEntity) {
        QuestionList questionList = null;
        if (questionListEntity != null) {
            questionList = new QuestionList();
            questionList.setTotal(questionListEntity.getTotal());
            questionList.setQuestions(paperEntityDataMapper.transform(questionListEntity.getQuestions()));
        }
        return questionList;
    }
}
