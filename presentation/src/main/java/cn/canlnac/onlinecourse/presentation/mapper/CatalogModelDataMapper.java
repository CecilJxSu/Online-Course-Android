package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Catalog;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CatalogModel;

@PerActivity
public class CatalogModelDataMapper {
    @Inject
    public CatalogModelDataMapper() {}

    public CatalogModel transform(Catalog catalog) {
        if (catalog == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CatalogModel catalogModel = new CatalogModel();
        catalogModel.setId(catalog.getId());
        catalogModel.setCourseId(catalog.getCourseId());
        catalogModel.setDate(catalog.getDate());
        catalogModel.setDuration(catalog.getDuration());
        catalogModel.setIndex(catalog.getIndex());
        catalogModel.setIntroduction(catalog.getIntroduction());
        catalogModel.setName(catalog.getName());
        catalogModel.setPreviewImage(catalog.getPreviewImage());
        catalogModel.setUrl(catalog.getUrl());

        return catalogModel;
    }

    public List<CatalogModel> transform(List<Catalog> catalogList) {
        List<CatalogModel> catalogModelList = new ArrayList<>(catalogList.size());
        CatalogModel catalogModel;
        for (Catalog catalog : catalogList) {
            catalogModel = transform(catalog);
            if (catalog != null) {
                catalogModelList.add(catalogModel);
            }
        }

        return catalogModelList;
    }
}
