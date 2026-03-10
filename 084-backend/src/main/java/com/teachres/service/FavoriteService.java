package com.teachres.service;

import com.teachres.common.BusinessException;
import com.teachres.entity.MaterialFavorite;
import com.teachres.entity.MaterialInfo;
import com.teachres.entity.StudyList;
import com.teachres.mapper.MaterialFavoriteMapper;
import com.teachres.mapper.MaterialInfoMapper;
import com.teachres.mapper.StudyListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteService {

    @Autowired
    private MaterialFavoriteMapper materialFavoriteMapper;

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Autowired
    private StudyListMapper studyListMapper;

    public List<Map<String, Object>> list(Long userId) {
        List<MaterialFavorite> favorites = materialFavoriteMapper.selectByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        for (MaterialFavorite favorite : favorites) {
            MaterialInfo materialInfo = materialInfoMapper.selectById(favorite.getMaterialId());
            if (materialInfo != null) {
                Map<String, Object> row = new HashMap<>();
                row.put("favorite", favorite);
                row.put("material", materialInfo);
                result.add(row);
            }
        }
        return result;
    }

    public void add(Long userId, Long materialId) {
        MaterialFavorite exists = materialFavoriteMapper.selectByUserAndMaterial(userId, materialId);
        if (exists != null) {
            throw new BusinessException("已收藏该资料");
        }
        MaterialFavorite favorite = new MaterialFavorite();
        favorite.setUserId(userId);
        favorite.setMaterialId(materialId);
        materialFavoriteMapper.insert(favorite);
        materialInfoMapper.incrementFavoriteCount(materialId);

        StudyList studyExists = studyListMapper.selectByUserAndMaterial(userId, materialId);
        if (studyExists == null) {
            StudyList studyList = new StudyList();
            studyList.setUserId(userId);
            studyList.setMaterialId(materialId);
            studyList.setProgress(0);
            studyList.setStatus(1);
            studyListMapper.insert(studyList);
        }
    }

    public void cancel(Long userId, Long materialId) {
        materialFavoriteMapper.deleteByUserAndMaterial(userId, materialId);
        materialInfoMapper.decrementFavoriteCount(materialId);
    }

    public List<StudyList> studyList(Long userId) {
        return studyListMapper.selectByUserId(userId);
    }

    public void updateStudy(StudyList studyList, Long userId) {
        StudyList exists = studyListMapper.selectByUserAndMaterial(userId, studyList.getMaterialId());
        if (exists == null) {
            studyList.setUserId(userId);
            if (studyList.getStatus() == null) {
                studyList.setStatus(1);
            }
            if (studyList.getProgress() == null) {
                studyList.setProgress(0);
            }
            studyListMapper.insert(studyList);
        } else {
            exists.setProgress(studyList.getProgress());
            exists.setNote(studyList.getNote());
            exists.setStatus(studyList.getStatus());
            studyListMapper.update(exists);
        }
    }

    public void deleteStudy(Long id) {
        studyListMapper.deleteById(id);
    }
}
