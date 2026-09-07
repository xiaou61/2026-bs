// 应用主要JavaScript逻辑

class AILearningApp {
    constructor() {
        this.user = AuthManager.getCurrentUser();
        this.charts = {};
        this.init();
    }

    init() {
        this.initEventHandlers();
        this.loadUserData();
        this.initRealtimeUpdates();
    }

    initEventHandlers() {
        // 全局搜索
        $('#globalSearch').on('keypress', (e) => {
            if (e.which === 13) {
                this.performGlobalSearch($(e.target).val());
            }
        });

        // 快速操作按钮
        $('.quick-action').on('click', (e) => {
            const action = $(e.target).data('action');
            this.handleQuickAction(action);
        });

        // 课程卡片点击
        $(document).on('click', '.course-card', (e) => {
            const courseId = $(e.target).closest('.course-card').data('course-id');
            this.viewCourse(courseId);
        });

        // 知识点卡片点击
        $(document).on('click', '.knowledge-card', (e) => {
            const knowledgeId = $(e.target).closest('.knowledge-card').data('knowledge-id');
            this.viewKnowledgePoint(knowledgeId);
        });

        // 学习路径步骤点击
        $(document).on('click', '.path-step', (e) => {
            const stepIndex = $(e.target).closest('.path-step').data('step-index');
            const pathId = $(e.target).closest('.path-container').data('path-id');
            this.updatePathProgress(pathId, stepIndex);
        });

        // 推荐反馈
        $(document).on('click', '.recommendation-feedback', (e) => {
            const rating = $(e.target).data('rating');
            const contentId = $(e.target).closest('.recommendation-item').data('content-id');
            const contentType = $(e.target).closest('.recommendation-item').data('content-type');
            this.submitRecommendationFeedback(contentId, contentType, rating);
        });
    }

    loadUserData() {
        if (!this.user) return;

        // 加载用户统计数据
        this.loadUserStatistics();
        
        // 加载推荐内容
        this.loadRecommendations();
        
        // 加载学习路径
        this.loadLearningPaths();
        
        // 加载最近活动
        this.loadRecentActivity();
    }

    async loadUserStatistics() {
        try {
            const response = await ApiClient.get('/analysis/statistics');
            if (response.success) {
                this.updateStatisticsDisplay(response.data);
            }
        } catch (error) {
            console.error('Failed to load user statistics:', error);
        }
    }

    updateStatisticsDisplay(stats) {
        $('#totalLearningTime').text(DataFormatter.formatTime(stats.totalLearningTimeMinutes || 0));
        $('#coursesCompleted').text(stats.coursesLearned || 0);
        $('#avgMasteryLevel').text(DataFormatter.formatPercentage(stats.avgMasteryLevel || 0));
        $('#knowledgePointsLearned').text(stats.knowledgePointsLearned || 0);
    }

    async loadRecommendations() {
        try {
            const response = await ApiClient.get('/recommendation/courses', { limit: 6 });
            if (response.success) {
                this.displayRecommendations(response.data);
            }
        } catch (error) {
            console.error('Failed to load recommendations:', error);
        }
    }

    displayRecommendations(courses) {
        const container = $('#recommendationsContainer');
        container.empty();

        if (courses.length === 0) {
            container.html('<div class="text-center text-muted">暂无推荐内容</div>');
            return;
        }

        courses.forEach(course => {
            const courseHtml = `
                <div class="col-md-4 mb-3">
                    <div class="card course-card" data-course-id="${course.id}">
                        <div class="card-body">
                            <h6 class="card-title">${course.courseName}</h6>
                            <p class="card-text text-muted small">${course.description || '暂无描述'}</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="badge bg-primary">难度 ${course.difficultyLevel || 1}</span>
                                <div class="recommendation-feedback">
                                    <button class="btn btn-sm btn-outline-success recommendation-feedback" 
                                            data-rating="1" data-content-id="${course.id}" data-content-type="1">
                                        <i class="fas fa-thumbs-up"></i>
                                    </button>
                                    <button class="btn btn-sm btn-outline-danger recommendation-feedback" 
                                            data-rating="0" data-content-id="${course.id}" data-content-type="1">
                                        <i class="fas fa-thumbs-down"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            container.append(courseHtml);
        });
    }

    async loadLearningPaths() {
        try {
            const response = await ApiClient.get('/learning-path/list');
            if (response.success) {
                this.displayLearningPaths(response.data);
            }
        } catch (error) {
            console.error('Failed to load learning paths:', error);
        }
    }

    displayLearningPaths(paths) {
        const container = $('#learningPathsContainer');
        container.empty();

        if (paths.length === 0) {
            container.html('<div class="text-center text-muted">暂无学习路径</div>');
            return;
        }

        paths.forEach(path => {
            const pathHtml = `
                <div class="card path-container mb-3" data-path-id="${path.id}">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h6 class="mb-0">${path.pathName}</h6>
                        <span class="badge bg-info">${DataFormatter.formatPercentage(path.progressRate / 100)}完成</span>
                    </div>
                    <div class="card-body">
                        <div class="progress mb-2">
                            <div class="progress-bar" style="width: ${path.progressRate}%"></div>
                        </div>
                        <p class="text-muted small">预计时长: ${path.estimatedDuration}小时</p>
                        <button class="btn btn-sm btn-primary" onclick="app.viewLearningPath(${path.id})">
                            继续学习
                        </button>
                    </div>
                </div>
            `;
            container.append(pathHtml);
        });
    }

    async loadRecentActivity() {
        try {
            const response = await ApiClient.get('/analysis/statistics');
            if (response.success) {
                // 这里可以扩展显示最近的学习活动
                console.log('Recent activity loaded');
            }
        } catch (error) {
            console.error('Failed to load recent activity:', error);
        }
    }

    async performGlobalSearch(query) {
        if (!query.trim()) return;

        try {
            const [coursesResponse, knowledgeResponse] = await Promise.all([
                ApiClient.get('/course/list', { keyword: query, page: 1, size: 5 }),
                ApiClient.get('/qa/keywords', { text: query })
            ]);

            this.displaySearchResults(coursesResponse.data, knowledgeResponse.data, query);
        } catch (error) {
            console.error('Search failed:', error);
            showMessage('搜索失败', 'danger');
        }
    }

    displaySearchResults(courses, keywords, query) {
        // 创建搜索结果模态框
        const resultsHtml = `
            <h6>课程搜索结果:</h6>
            <div class="mb-3">
                ${courses.records && courses.records.length > 0 ? 
                    courses.records.map(course => `
                        <div class="border rounded p-2 mb-2 cursor-pointer" onclick="app.viewCourse(${course.id})">
                            <strong>${course.courseName}</strong>
                            <p class="mb-0 text-muted small">${course.description || '暂无描述'}</p>
                        </div>
                    `).join('') : 
                    '<p class="text-muted">未找到相关课程</p>'
                }
            </div>
            <h6>相关关键词:</h6>
            <div>
                ${keywords && keywords.length > 0 ? 
                    keywords.map(keyword => `<span class="badge bg-secondary me-1">${keyword}</span>`).join('') :
                    '<p class="text-muted">暂无相关关键词</p>'
                }
            </div>
        `;

        ModalHelper.show(`搜索结果: "${query}"`, resultsHtml);
    }

    handleQuickAction(action) {
        switch (action) {
            case 'qa':
                window.location.href = '/qa';
                break;
            case 'path':
                this.showCreatePathModal();
                break;
            case 'analytics':
                window.location.href = '/analytics';
                break;
            case 'quiz':
                showMessage('快速测验功能开发中', 'info');
                break;
            default:
                console.warn('Unknown quick action:', action);
        }
    }

    showCreatePathModal() {
        const modalContent = `
            <form id="createPathForm">
                <div class="mb-3">
                    <label class="form-label">目标知识点</label>
                    <select class="form-select" id="targetKnowledgePoint" required>
                        <option value="">请选择目标知识点</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">学习目标描述</label>
                    <textarea class="form-control" id="pathDescription" rows="3"></textarea>
                </div>
            </form>
        `;

        ModalHelper.show('创建学习路径', modalContent, [
            { text: '取消', type: 'secondary', dismiss: true },
            { text: '创建', type: 'primary', onclick: 'app.createLearningPath()', dismiss: false }
        ]);

        // 加载知识点选项
        this.loadKnowledgePointOptions();
    }

    async loadKnowledgePointOptions() {
        try {
            const response = await ApiClient.get('/recommendation/knowledge-points', { limit: 50 });
            if (response.success) {
                const select = $('#targetKnowledgePoint');
                select.empty().append('<option value="">请选择目标知识点</option>');
                
                response.data.forEach(kp => {
                    select.append(`<option value="${kp.id}">${kp.pointName}</option>`);
                });
            }
        } catch (error) {
            console.error('Failed to load knowledge points:', error);
        }
    }

    async createLearningPath() {
        const targetKnowledgePointId = $('#targetKnowledgePoint').val();
        if (!targetKnowledgePointId) {
            showMessage('请选择目标知识点', 'warning');
            return;
        }

        try {
            const response = await ApiClient.post('/learning-path/create', {
                targetKnowledgePointId: parseInt(targetKnowledgePointId)
            });

            if (response.success) {
                showMessage('学习路径创建成功', 'success');
                this.loadLearningPaths(); // 重新加载学习路径
                bootstrap.Modal.getInstance(document.querySelector('.modal.show')).hide();
            } else {
                showMessage(response.message || '创建失败', 'danger');
            }
        } catch (error) {
            console.error('Failed to create learning path:', error);
            showMessage('创建学习路径失败', 'danger');
        }
    }

    async submitRecommendationFeedback(contentId, contentType, rating) {
        try {
            await ApiClient.post('/recommendation/feedback', {
                contentId: parseInt(contentId),
                contentType: parseInt(contentType),
                rating: parseFloat(rating)
            });

            showMessage('感谢您的反馈', 'success', 2000);
            
            // 更新UI显示
            const feedbackBtns = $(`.recommendation-feedback[data-content-id="${contentId}"]`);
            feedbackBtns.removeClass('active');
            $(`.recommendation-feedback[data-content-id="${contentId}"][data-rating="${rating}"]`).addClass('active');
            
        } catch (error) {
            console.error('Failed to submit feedback:', error);
        }
    }

    async updatePathProgress(pathId, stepIndex) {
        try {
            const response = await ApiClient.put(`/learning-path/${pathId}/progress`, {
                currentStep: stepIndex
            });

            if (response.success) {
                showMessage('学习进度已更新', 'success');
                this.loadLearningPaths(); // 重新加载学习路径
            }
        } catch (error) {
            console.error('Failed to update path progress:', error);
            showMessage('更新进度失败', 'danger');
        }
    }

    viewCourse(courseId) {
        BehaviorTracker.trackClick('course', courseId);
        window.location.href = `/courses/${courseId}`;
    }

    viewKnowledgePoint(knowledgeId) {
        BehaviorTracker.trackClick('knowledge_point', knowledgeId);
        // 实现知识点详情页面跳转
        showMessage('知识点详情页面开发中', 'info');
    }

    viewLearningPath(pathId) {
        BehaviorTracker.trackClick('learning_path', pathId);
        window.location.href = `/learning-path/${pathId}`;
    }

    initRealtimeUpdates() {
        // 定期更新用户数据 (每5分钟)
        setInterval(() => {
            if (AuthManager.isLoggedIn()) {
                this.loadUserStatistics();
            }
        }, 5 * 60 * 1000);
    }

    // 图表初始化方法
    initCharts() {
        // 学习进度饼图
        if (document.getElementById('learningProgress')) {
            const progressData = [
                { name: '已完成', value: 65 },
                { name: '进行中', value: 25 },
                { name: '未开始', value: 10 }
            ];
            this.charts.progress = ChartHelper.createProgressChart('learningProgress', progressData);
        }
    }

    // 响应式图表调整
    resizeCharts() {
        Object.values(this.charts).forEach(chart => {
            if (chart && chart.resize) {
                chart.resize();
            }
        });
    }
}

// 智能问答功能
class QAInterface {
    constructor() {
        this.sessionId = this.generateSessionId();
        this.isWaitingForResponse = false;
    }

    generateSessionId() {
        return 'qa_session_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
    }

    async askQuestion(question) {
        if (this.isWaitingForResponse || !question.trim()) return;

        this.isWaitingForResponse = true;
        this.addMessageToChat('user', question);
        this.showTypingIndicator();

        try {
            const response = await ApiClient.post('/qa/ask', {
                question: question.trim(),
                sessionId: this.sessionId
            });

            this.hideTypingIndicator();

            if (response.success) {
                this.addMessageToChat('assistant', response.data.answer);
                BehaviorTracker.track('qa_question', 'question', null, { question, answer: response.data.answer });
            } else {
                this.addMessageToChat('assistant', '抱歉，我暂时无法回答您的问题。请稍后再试。');
            }
        } catch (error) {
            this.hideTypingIndicator();
            this.addMessageToChat('assistant', '网络连接出现问题，请检查网络连接后重试。');
            console.error('QA request failed:', error);
        } finally {
            this.isWaitingForResponse = false;
        }
    }

    addMessageToChat(sender, message) {
        const chatMessages = $('#chatMessages');
        const messageHtml = `
            <div class="chat-message ${sender}">
                <div class="message-bubble">
                    ${this.formatMessage(message)}
                </div>
                <small class="text-muted">${new Date().toLocaleTimeString()}</small>
            </div>
        `;
        
        chatMessages.append(messageHtml);
        chatMessages.scrollTop(chatMessages[0].scrollHeight);
    }

    formatMessage(message) {
        // 简单的消息格式化，可以扩展支持Markdown等
        return message.replace(/\n/g, '<br>');
    }

    showTypingIndicator() {
        const indicator = `
            <div class="chat-message assistant typing-indicator">
                <div class="message-bubble">
                    <span class="typing-dots">
                        <span></span><span></span><span></span>
                    </span>
                </div>
            </div>
        `;
        $('#chatMessages').append(indicator);
    }

    hideTypingIndicator() {
        $('.typing-indicator').remove();
    }
}

// 全局应用实例
let app;
let qaInterface;

// 初始化应用
$(document).ready(function() {
    app = new AILearningApp();
    qaInterface = new QAInterface();

    // 窗口大小改变时调整图表
    $(window).on('resize', function() {
        if (app && app.resizeCharts) {
            app.resizeCharts();
        }
    });

    // 初始化图表
    setTimeout(() => {
        if (app && app.initCharts) {
            app.initCharts();
        }
    }, 100);
});

// 导出全局函数供HTML调用
window.app = app;
window.qaInterface = qaInterface;