package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

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
            catalog.setParentId(catalogEntity.getParentId());
            catalog.setIntroduction(catalogEntity.getIntroduction());
            catalog.setName(catalogEntity.getName());
            catalog.setPreviewImage(catalogEntity.getPreviewImage());
            catalog.setUrl(catalogEntity.getUrl());
        }
        return catalog;
    }

    /**
     * 转换列表
     * @param catalogEntityList 目录实体类列表
     * @return
     */
    public List<Catalog> transform(List<CatalogEntity> catalogEntityList) {
        List<Catalog> catalogList = new ArrayList<>(catalogEntityList.size());
        Catalog catalog;
        for (CatalogEntity catalogEntity : catalogEntityList) {
            catalog = transform(catalogEntity);
            if (catalog != null) {
                catalogList.add(catalog);
            }
        }

        return catalogList;
    }
}
