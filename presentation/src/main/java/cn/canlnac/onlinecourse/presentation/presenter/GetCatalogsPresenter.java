package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Catalog;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CatalogModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;

@PerActivity
public class GetCatalogsPresenter implements Presenter {

    CourseActivity getCatalogsActivity;

    private final UseCase getCatalogsUseCase;
    private final CatalogModelDataMapper catalogModelDataMapper;

    @Inject
    public GetCatalogsPresenter(UseCase getCatalogsUseCase,CatalogModelDataMapper catalogModelDataMapper) {
        this.getCatalogsUseCase = getCatalogsUseCase;
        this.catalogModelDataMapper = catalogModelDataMapper;
    }

    public void setView(@NonNull CourseActivity getCatalogsActivity) {
        this.getCatalogsActivity = getCatalogsActivity;
    }

    public void initialize() {
        this.getCatalogsUseCase.execute(new GetCatalogsPresenter.GetCatalogsSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCatalogsUseCase.unsubscribe();
    }

    private final class GetCatalogsSubscriber extends DefaultSubscriber<List<Catalog>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("参数错误");
                        break;
                    case 401:
                        GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("未登陆");
                        GetCatalogsPresenter.this.getCatalogsActivity.toLogin();
                        break;
                    case 404:
                        GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("章节不存在");
                        break;
                    default:
                        GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(List<Catalog> catalogList) {
            GetCatalogsPresenter.this.getCatalogsActivity.showCatalogs(catalogModelDataMapper.transform(catalogList));
        }
    }
}