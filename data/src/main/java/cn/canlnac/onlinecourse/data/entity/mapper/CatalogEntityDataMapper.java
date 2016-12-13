package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.CatalogEntity;
import cn.canlnac.onlinecourse.domain.Catalog;

/**
 * 目录实体类转换.
 */
@Singleton
public class CatalogEntityDataMapper {

    @Inject
    public CatalogEntityDataMapper() {}

    /**
     * 转换
     * @param catalogEntity 目录实体类
     * @return
     */
    public Catalog transform(CatalogEntity catalogEntity) {
        Catalog catalog = null;
        if (catalogEntity != null) {
            catalog = new Catalog();
            catalog.setId(catalogEntity.getId());
            catalog.setCourseId(catalogEntity.getCourseId());
            catalog.setDate(catalogEntity.getDate());
            catalog.setDuration(catalogEntity.getDuration());
            catalog.setIndex(catalogEntity.getIndex());
            catalog.setIntroduction(catalogEntity.getIntroduction());
            catalog.setName(catalogEntity.getName());
            catalog.setPreviewImage(catalogEntity.getPreviewImage());
            catalog.setUrl(catalogEntity.getUrl());
        }
        return catalog;
    }
}
