package com.teachres.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachres.common.BusinessException;
import com.teachres.entity.MaterialAudit;
import com.teachres.entity.MaterialDownload;
import com.teachres.entity.MaterialFile;
import com.teachres.entity.MaterialInfo;
import com.teachres.mapper.MaterialAuditMapper;
import com.teachres.mapper.MaterialDownloadMapper;
import com.teachres.mapper.MaterialFileMapper;
import com.teachres.mapper.MaterialInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialService {

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Autowired
    private MaterialFileMapper materialFileMapper;

    @Autowired
    private MaterialAuditMapper materialAuditMapper;

    @Autowired
    private MaterialDownloadMapper materialDownloadMapper;

    public PageInfo<MaterialInfo> list(String title, Long categoryId, Integer auditStatus, Integer publishStatus, Long uploaderId,
                                       Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MaterialInfo> list = materialInfoMapper.selectList(title, categoryId, auditStatus, publishStatus, uploaderId);
        return new PageInfo<>(list);
    }

    public PageInfo<MaterialInfo> publicList(String title, Long categoryId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MaterialInfo> list = materialInfoMapper.selectPublicList(title, categoryId);
        return new PageInfo<>(list);
    }

    public Map<String, Object> detail(Long id) {
        MaterialInfo materialInfo = materialInfoMapper.selectById(id);
        if (materialInfo == null) {
            throw new BusinessException("资料不存在");
        }
        MaterialFile materialFile = materialFileMapper.selectByMaterialId(id);
        Map<String, Object> result = new HashMap<>();
        result.put("material", materialInfo);
        result.put("file", materialFile);
        return result;
    }

    public void add(Map<String, Object> params, Long userId) {
        if (params.get("title") == null || String.valueOf(params.get("title")).trim().isEmpty()) {
            throw new BusinessException("资料标题不能为空");
        }
        if (params.get("categoryId") == null) {
            throw new BusinessException("资料分类不能为空");
        }
        MaterialInfo materialInfo = new MaterialInfo();
        materialInfo.setTitle(String.valueOf(params.get("title")));
        materialInfo.setSummary(params.get("summary") == null ? null : String.valueOf(params.get("summary")));
        materialInfo.setCategoryId(Long.valueOf(String.valueOf(params.get("categoryId"))));
        materialInfo.setGrade(params.get("grade") == null ? null : String.valueOf(params.get("grade")));
        materialInfo.setSubject(params.get("subject") == null ? null : String.valueOf(params.get("subject")));
        materialInfo.setKeyword(params.get("keyword") == null ? null : String.valueOf(params.get("keyword")));
        materialInfo.setUploaderId(userId);
        materialInfo.setAuditStatus(0);
        materialInfo.setPublishStatus(0);
        materialInfo.setDownloadCount(0);
        materialInfo.setFavoriteCount(0);
        materialInfo.setStatus(1);
        materialInfoMapper.insert(materialInfo);

        MaterialFile file = new MaterialFile();
        file.setMaterialId(materialInfo.getId());
        file.setFileName(params.get("fileName") == null ? "未命名资料" : String.valueOf(params.get("fileName")));
        file.setFilePath(params.get("filePath") == null ? "/upload/default.file" : String.valueOf(params.get("filePath")));
        file.setFileSize(params.get("fileSize") == null ? 0L : Long.valueOf(String.valueOf(params.get("fileSize"))));
        file.setFileType(params.get("fileType") == null ? "unknown" : String.valueOf(params.get("fileType")));
        materialFileMapper.insert(file);
    }

    public void update(Map<String, Object> params) {
        Long id = Long.valueOf(String.valueOf(params.get("id")));
        MaterialInfo materialInfo = materialInfoMapper.selectById(id);
        if (materialInfo == null) {
            throw new BusinessException("资料不存在");
        }
        if (params.containsKey("title")) {
            materialInfo.setTitle(String.valueOf(params.get("title")));
        }
        if (params.containsKey("summary")) {
            materialInfo.setSummary(params.get("summary") == null ? null : String.valueOf(params.get("summary")));
        }
        if (params.containsKey("categoryId")) {
            materialInfo.setCategoryId(Long.valueOf(String.valueOf(params.get("categoryId"))));
        }
        if (params.containsKey("grade")) {
            materialInfo.setGrade(params.get("grade") == null ? null : String.valueOf(params.get("grade")));
        }
        if (params.containsKey("subject")) {
            materialInfo.setSubject(params.get("subject") == null ? null : String.valueOf(params.get("subject")));
        }
        if (params.containsKey("keyword")) {
            materialInfo.setKeyword(params.get("keyword") == null ? null : String.valueOf(params.get("keyword")));
        }
        if (params.containsKey("publishStatus")) {
            materialInfo.setPublishStatus(Integer.valueOf(String.valueOf(params.get("publishStatus"))));
        }
        materialInfoMapper.update(materialInfo);

        MaterialFile materialFile = materialFileMapper.selectByMaterialId(id);
        if (materialFile != null) {
            if (params.containsKey("fileName")) {
                materialFile.setFileName(params.get("fileName") == null ? null : String.valueOf(params.get("fileName")));
            }
            if (params.containsKey("filePath")) {
                materialFile.setFilePath(params.get("filePath") == null ? null : String.valueOf(params.get("filePath")));
            }
            if (params.containsKey("fileSize")) {
                materialFile.setFileSize(params.get("fileSize") == null ? null : Long.valueOf(String.valueOf(params.get("fileSize"))));
            }
            if (params.containsKey("fileType")) {
                materialFile.setFileType(params.get("fileType") == null ? null : String.valueOf(params.get("fileType")));
            }
            materialFileMapper.update(materialFile);
        }
    }

    public void delete(Long id) {
        materialInfoMapper.deleteById(id);
        materialFileMapper.deleteByMaterialId(id);
    }

    public void publish(Long id, Integer publishStatus) {
        MaterialInfo materialInfo = new MaterialInfo();
        materialInfo.setId(id);
        materialInfo.setPublishStatus(publishStatus);
        materialInfoMapper.update(materialInfo);
    }

    public void audit(Long materialId, Integer auditStatus, String auditRemark, Long auditorId) {
        MaterialInfo target = materialInfoMapper.selectById(materialId);
        if (target == null) {
            throw new BusinessException("资料不存在");
        }
        MaterialInfo update = new MaterialInfo();
        update.setId(materialId);
        update.setAuditStatus(auditStatus);
        if (auditStatus != null && auditStatus == 1) {
            update.setPublishStatus(1);
        } else {
            update.setPublishStatus(0);
        }
        materialInfoMapper.update(update);

        MaterialAudit audit = new MaterialAudit();
        audit.setMaterialId(materialId);
        audit.setAuditStatus(auditStatus);
        audit.setAuditRemark(auditRemark);
        audit.setAuditorId(auditorId);
        materialAuditMapper.insert(audit);
    }

    public List<MaterialAudit> auditList(Long materialId) {
        return materialAuditMapper.selectList(materialId);
    }

    public void download(Long materialId, Long userId) {
        MaterialInfo materialInfo = materialInfoMapper.selectById(materialId);
        if (materialInfo == null) {
            throw new BusinessException("资料不存在");
        }
        materialInfoMapper.incrementDownloadCount(materialId);
        MaterialDownload materialDownload = new MaterialDownload();
        materialDownload.setMaterialId(materialId);
        materialDownload.setUserId(userId);
        materialDownload.setSource("web");
        materialDownloadMapper.insert(materialDownload);
    }

    public MaterialInfo getById(Long id) {
        return materialInfoMapper.selectById(id);
    }

    public long countAll() {
        return materialInfoMapper.countAll();
    }

    public long countPassed() {
        return materialInfoMapper.countByAuditStatus(1);
    }
}
