package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.PaperEntity;
import cn.canlnac.onlinecourse.domain.Paper;

/**
 * 小测类型题目实体类转换.
 */
@Singleton
public class PaperEntityDataMapper {
    private QuestionEntityDataMapper questionEntityDataMapper;

    @Inject
    public PaperEntityDataMapper(QuestionEntityDataMapper questionEntityDataMapper) {
        this.questionEntityDataMapper = questionEntityDataMapper;
    }

    /**
     * 转换
     * @param paperEntity 小测类型题目实体类
     * @return
     */
    public Paper transform(PaperEntity paperEntity) {
        Paper paper = null;
        if (paperEntity != null) {
            paper = new Paper();
            paper.setType(paperEntity.getType());
            paper.setQuestions(questionEntityDataMapper.transform(paperEntity.getQuestions()));
            paper.setScore(paperEntity.getScore());
        }
        return paper;
    }

    /**
     * 转换列表
     * @param paperEntityList    小测类型题目类列表
     * @return
     */
    public List<Paper> transform(List<PaperEntity> paperEntityList) {
        List<Paper> papers = new ArrayList<>(paperEntityList.size());
        Paper paper;
        for (PaperEntity paperEntity : paperEntityList) {
            paper = transform(paperEntity);
            if (paper != null) {
                papers.add(paper);
            }
        }

        return papers;
    }
}
