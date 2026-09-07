package com.security.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.common.BusinessException;
import com.security.dto.AdminLoginDTO;
import com.security.entity.*;
import com.security.mapper.*;
import com.security.service.AdminService;
import com.security.utils.JwtUtils;
import com.security.vo.LoginVO;
import com.security.vo.StatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private QaPostMapper qaPostMapper;

    @Autowired
    private AnswerRecordMapper answerRecordMapper;

    @Override
    public LoginVO login(AdminLoginDTO dto) {
        Admin admin = this.getOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, dto.getUsername()));
        if (admin == null) {
            throw new BusinessException("用户名或密码错误");
        }
        String md5Password = DigestUtil.md5Hex(dto.getPassword());
        if (!admin.getPassword().equals(md5Password)) {
            throw new BusinessException("用户名或密码错误");
        }
        if (admin.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }

        String token = jwtUtils.generateToken(admin.getId(), "admin");
        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setUserId(admin.getId());
        vo.setNickname(admin.getNickname());
        return vo;
    }

    @Override
    public Page<User> getUserList(Integer page, Integer size, String keyword) {
        Page<User> userPage = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(User::getNickname, keyword);
        }
        wrapper.orderByDesc(User::getCreateTime);
        return userMapper.selectPage(userPage, wrapper);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setLevel(status);
            userMapper.updateById(user);
        }
    }

    @Override
    public Page<KnowledgeCategory> getCategoryList(Integer page, Integer size) {
        Page<KnowledgeCategory> categoryPage = new Page<>(page, size);
        return categoryMapper.selectPage(categoryPage,
                new LambdaQueryWrapper<KnowledgeCategory>().orderByAsc(KnowledgeCategory::getSort));
    }

    @Override
    public void saveCategory(KnowledgeCategory category) {
        if (category.getId() == null) {
            categoryMapper.insert(category);
        } else {
            categoryMapper.updateById(category);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public Page<KnowledgeArticle> getArticleList(Integer page, Integer size, Long categoryId) {
        Page<KnowledgeArticle> articlePage = new Page<>(page, size);
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(KnowledgeArticle::getCategoryId, categoryId);
        }
        wrapper.orderByDesc(KnowledgeArticle::getCreateTime);
        return articleMapper.selectPage(articlePage, wrapper);
    }

    @Override
    public void saveArticle(KnowledgeArticle article) {
        if (article.getId() == null) {
            article.setViewCount(0);
            article.setLikeCount(0);
            article.setStatus(1);
            articleMapper.insert(article);
        } else {
            articleMapper.updateById(article);
        }
    }

    @Override
    public void deleteArticle(Long id) {
        articleMapper.deleteById(id);
    }

    @Override
    public Page<Question> getQuestionList(Integer page, Integer size, Long categoryId) {
        Page<Question> questionPage = new Page<>(page, size);
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(Question::getCategoryId, categoryId);
        }
        return questionMapper.selectPage(questionPage, wrapper);
    }

    @Override
    public void saveQuestion(Question question) {
        if (question.getId() == null) {
            questionMapper.insert(question);
        } else {
            questionMapper.updateById(question);
        }
    }

    @Override
    public void deleteQuestion(Long id) {
        questionMapper.deleteById(id);
    }

    @Override
    public Page<News> getNewsList(Integer page, Integer size) {
        Page<News> newsPage = new Page<>(page, size);
        return newsMapper.selectPage(newsPage,
                new LambdaQueryWrapper<News>().orderByDesc(News::getPublishTime));
    }

    @Override
    public void saveNews(News news) {
        if (news.getId() == null) {
            news.setViewCount(0);
            news.setStatus(1);
            newsMapper.insert(news);
        } else {
            newsMapper.updateById(news);
        }
    }

    @Override
    public void deleteNews(Long id) {
        newsMapper.deleteById(id);
    }

    @Override
    public Page<QaPost> getQaList(Integer page, Integer size) {
        Page<QaPost> qaPage = new Page<>(page, size);
        return qaPostMapper.selectPage(qaPage,
                new LambdaQueryWrapper<QaPost>().orderByDesc(QaPost::getCreateTime));
    }

    @Override
    public void updateQaStatus(Long id, Integer status) {
        QaPost post = qaPostMapper.selectById(id);
        if (post != null) {
            post.setStatus(status);
            qaPostMapper.updateById(post);
        }
    }

    @Override
    public StatsVO getStats() {
        StatsVO vo = new StatsVO();
        vo.setUserCount(userMapper.selectCount(null));
        vo.setArticleCount(articleMapper.selectCount(null));
        vo.setQuestionCount(questionMapper.selectCount(null));
        vo.setNewsCount(newsMapper.selectCount(null));
        vo.setQaCount(qaPostMapper.selectCount(null));

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        Long todayAnswerCount = answerRecordMapper.selectCount(
                new LambdaQueryWrapper<AnswerRecord>()
                        .ge(AnswerRecord::getCreateTime, todayStart));
        vo.setTodayAnswerCount(todayAnswerCount);

        return vo;
    }
}
