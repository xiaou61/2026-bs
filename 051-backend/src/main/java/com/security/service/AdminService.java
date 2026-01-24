package com.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.security.dto.AdminLoginDTO;
import com.security.entity.*;
import com.security.vo.LoginVO;
import com.security.vo.StatsVO;

public interface AdminService extends IService<Admin> {
    LoginVO login(AdminLoginDTO dto);
    
    Page<User> getUserList(Integer page, Integer size, String keyword);
    void updateUserStatus(Long id, Integer status);
    
    Page<KnowledgeCategory> getCategoryList(Integer page, Integer size);
    void saveCategory(KnowledgeCategory category);
    void deleteCategory(Long id);
    
    Page<KnowledgeArticle> getArticleList(Integer page, Integer size, Long categoryId);
    void saveArticle(KnowledgeArticle article);
    void deleteArticle(Long id);
    
    Page<Question> getQuestionList(Integer page, Integer size, Long categoryId);
    void saveQuestion(Question question);
    void deleteQuestion(Long id);
    
    Page<News> getNewsList(Integer page, Integer size);
    void saveNews(News news);
    void deleteNews(Long id);
    
    Page<QaPost> getQaList(Integer page, Integer size);
    void updateQaStatus(Long id, Integer status);
    
    StatsVO getStats();
}
