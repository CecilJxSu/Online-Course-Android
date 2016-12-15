package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 从服务器中获取评论数据.
 */

public class CloudCommentDataStore implements CommentDataStore {
    private final RestApi restApi;

    public CloudCommentDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<Void> likeComment(int commentId) {
        return this.restApi.likeComment(commentId);
    }

    @Override
    public Observable<Void> unlikeComment(int commentId) {
        return this.restApi.unlikeComment(commentId);
    }
}
