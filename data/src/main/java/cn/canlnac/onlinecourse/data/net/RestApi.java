package cn.canlnac.onlinecourse.data.net;

import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.AnswerEntity;
import cn.canlnac.onlinecourse.data.entity.CatalogEntity;
import cn.canlnac.onlinecourse.data.entity.ChatEntity;
import cn.canlnac.onlinecourse.data.entity.ChatListEntity;
import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import cn.canlnac.onlinecourse.data.entity.CourseEntity;
import cn.canlnac.onlinecourse.data.entity.CourseListEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentListEntity;
import cn.canlnac.onlinecourse.data.entity.FollowerEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordListEntity;
import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.data.entity.MessageEntity;
import cn.canlnac.onlinecourse.data.entity.MessageListEntity;
import cn.canlnac.onlinecourse.data.entity.ProfileEntity;
import cn.canlnac.onlinecourse.data.entity.QuestionEntity;
import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import rx.Observable;

/**
 * Rest api 接口，从后台获取数据
 */

public interface RestApi {
    /********************************** 注册 ***********************************/
    Observable<LoginEntity> login(String username, String password);

    /********************************** 用户 ***********************************/
    /** 注册 */
    Observable<RegisterEntity> register(String username, String password);
    /** 获取用户资料 */
    Observable<ProfileEntity> getUserProfile(int userId);
    /** 更新个人资料 */
    Observable<Void> updateUserProfile(Map<String,String> profile);
    /** 获取个人消息 */
    Observable<MessageListEntity> getMessages(int start, int count, boolean isRead);
    /** 获取指定消息 */
    Observable<MessageEntity> getMessage(int messageId);
    /** 删除消息 */
    Observable<Void> deleteMessage(int messageId);
    /** 获取别人学习记录 */
    Observable<LearnRecordListEntity> getOtherUserLearnRecord(int userId, int start, int count);
    /** 获取自己的粉丝 */
    Observable<FollowerEntity> getFollower(int start, int count);
    /** 获取用户的关注 */
    Observable<FollowerEntity> getUserFollowing(int userId, int start, int count);
    /** 关注用户 */
    Observable<Void> followUser(int userId);
    /** 取消关注 */
    Observable<Void> unfollowUser(int userId);

    /********************************** 文档 ***********************************/
    /** 获取指定文档 */
    Observable<DocumentEntity> getDocument(int documentId);
    /** 删除指定文档 */
    Observable<Void> deleteDocument(int documentId);

    /********************************** 课程 ***********************************/
    /** 获取指定课程 */
    Observable<CourseEntity> getCourse(int courseId);
    /** 修改课程 */
    Observable<Void> updateCourse(int courseId, Map<String,String> course);
    /** 删除课程 */
    Observable<Void> deleteCourse(int courseId);
    /** 创建课程 */
    Observable<Integer> createCourse(Map<String,String> course);
    /** 点赞课程 */
    Observable<Void> likeCourse(int courseId);
    /** 取消点赞 */
    Observable<Void> unlikeCourse(int courseId);
    /** 收藏课程 */
    Observable<Void> favoriteCourse(int courseId);
    /** 取消收藏 */
    Observable<Void> unfavoriteCourse(int courseId);
    /** 创建文档 */
    Observable<Integer> createDocument(int courseId, Map<String,String> document);
    /** 课程下的文档 */
    Observable<DocumentListEntity> getDocumentsInCourse(int courseId, int start, int count, String sort);
    /** 获取课程下的评论 */
    Observable<CommentListEntity> getCommentsInCourse(int courseId, int start, int count, String sort);
    /** 创建评论 */
    Observable<Integer> createCommentInCourse(int courseId, Map<String,Object> comment);
    /** 回复评论 */
    Observable<Integer> replyCommentInCourse(int courseId, int commentId, Map<String,Object> reply);
    /** 创建目录 */
    Observable<Integer> createCatalog(int courseId, Map<String,Object> catalog);
    /** 获取课程下的章节 */
    Observable<List<CatalogEntity>> getCatalogs(int courseId);

    /** 获取课程列表 */
    Observable<CourseListEntity> getCourses(int start, int count, String sort, List<String> departments);

    /********************************** 评论 ***********************************/
    /** 点赞评论 */
    Observable<Void> likeComment(int commentId);
    /** 取消点赞评论 */
    Observable<Void> unlikeComment(int commentId);

    /********************************** 话题 ***********************************/
    /** 获取话题 */
    Observable<ChatEntity> getChat(int chatId);
    /** 删除话题 */
    Observable<Void> deleteChat(int chatId);
    /** 创建话题 */
    Observable<Integer> createChat(Map<String,Object> chat);
    /** 点赞话题 */
    Observable<Void> likeChat(int chatId);
    /** 取消点赞话题 */
    Observable<Void> unlikeChat(int chatId);
    /** 收藏话题 */
    Observable<Void> favoriteChat(int chatId);
    /** 取消收藏话题 */
    Observable<Void> unfavoriteChat(int chatId);
    /** 获取话题下的评论 */
    Observable<CommentListEntity> getCommentsInChat(int chatId, int start, int count, String sort);
    /** 评论话题 */
    Observable<Integer> createCommentInChat(int chatId, Map<String,Object> comment);
    /** 回复评论 */
    Observable<Integer> replyCommentInChat(int chatId, int commentId, Map<String,Object> reply);

    /** 话题列表 */
    Observable<ChatListEntity> getChats(int start, int count, String sort);

    /********************************** 目录 ***********************************/
    /** 获取目录 */
    Observable<CatalogEntity> getCatalog(int catalogId);
    /** 修改目录 */
    Observable<Void> updateCatalog(int catalogId, Map<String,Object> catalog);
    /** 删除目录 */
    Observable<Void> deleteCatalog(int catalogId);
    /** 创建小测 */
    Observable<Integer> createQuestion(int catalogId, Map<String,Object> question);
    /** 更新小测 */
    Observable<Void> updateQuestion(int catalogId, Map<String,Object> question);
    /** 删除小测 */
    Observable<Void> deleteQuestion(int catalogId);
    /** 获取小测 */
    Observable<QuestionEntity> getQuestion(int catalogId);
    /** 获取学习记录 */
    Observable<LearnRecordEntity> getLearnRecord(int catalogId);
    /** 创建学习记录 */
    Observable<Integer> createLearnRecord(int catalogId, Map<String,Object> learnRecord);
    /** 更新学习记录 */
    Observable<Void> updateLearnRecord(int catalogId, Map<String,Object> learnRecord);
    /** 创建文档 */
    Observable<Integer> getDocumentInCatalog(int catalogId, Map<String,Object> document);
    /** 文档列表 */
    Observable<DocumentListEntity> getDocumentsInCatalog(int catalogId, int start, int count, String sort);
    /** 获取章节下的回答 */
    Observable<AnswerEntity> getAnswer(int catalogId);
    /** 创建回答 */
    Observable<Integer> createAnser(int catalogId, Map<String,Object> answer);
}

