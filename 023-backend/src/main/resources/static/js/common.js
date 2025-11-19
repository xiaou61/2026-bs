// 全局JavaScript工具函数和通用功能

// API请求配置
const API_BASE_URL = '/api';
let authToken = localStorage.getItem('authToken');

// 设置jQuery AJAX默认配置
$.ajaxSetup({
    beforeSend: function(xhr) {
        if (authToken) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + authToken);
        }
        showLoading();
    },
    complete: function() {
        hideLoading();
    },
    error: function(xhr, status, error) {
        console.error('AJAX Error:', error);
        if (xhr.status === 401) {
            // 未授权，清除token并跳转到登录页
            localStorage.removeItem('authToken');
            window.location.href = '/login';
        } else if (xhr.status === 500) {
            showMessage('服务器内部错误', 'danger');
        } else {
            showMessage('网络请求失败', 'danger');
        }
    }
});

// 显示加载指示器
function showLoading() {
    $('#loadingIndicator').show();
}

// 隐藏加载指示器
function hideLoading() {
    $('#loadingIndicator').hide();
}

// 显示消息提示
function showMessage(message, type = 'info', duration = 5000) {
    const alertId = 'alert-' + Date.now();
    const iconClass = getAlertIcon(type);
    
    const alertHtml = `
        <div id="${alertId}" class="alert alert-${type} alert-dismissible fade show" role="alert">
            <i class="fas ${iconClass} me-2"></i>
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
    
    $('#messageContainer').append(alertHtml);
    
    // 自动隐藏
    setTimeout(() => {
        $(`#${alertId}`).alert('close');
    }, duration);
}

// 获取警告图标
function getAlertIcon(type) {
    const icons = {
        'success': 'fa-check-circle',
        'danger': 'fa-exclamation-circle',
        'warning': 'fa-exclamation-triangle',
        'info': 'fa-info-circle',
        'primary': 'fa-info-circle',
        'secondary': 'fa-info-circle'
    };
    return icons[type] || 'fa-info-circle';
}

// API请求封装
class ApiClient {
    static async get(endpoint, params = {}) {
        try {
            const queryString = new URLSearchParams(params).toString();
            const url = `${API_BASE_URL}${endpoint}${queryString ? '?' + queryString : ''}`;
            
            const response = await $.get(url);
            return response;
        } catch (error) {
            console.error('API GET Error:', error);
            throw error;
        }
    }
    
    static async post(endpoint, data = {}) {
        try {
            const response = await $.ajax({
                url: `${API_BASE_URL}${endpoint}`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data)
            });
            return response;
        } catch (error) {
            console.error('API POST Error:', error);
            throw error;
        }
    }
    
    static async put(endpoint, data = {}) {
        try {
            const response = await $.ajax({
                url: `${API_BASE_URL}${endpoint}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data)
            });
            return response;
        } catch (error) {
            console.error('API PUT Error:', error);
            throw error;
        }
    }
    
    static async delete(endpoint) {
        try {
            const response = await $.ajax({
                url: `${API_BASE_URL}${endpoint}`,
                type: 'DELETE'
            });
            return response;
        } catch (error) {
            console.error('API DELETE Error:', error);
            throw error;
        }
    }
}

// 用户认证相关
class AuthManager {
    static login(username, password) {
        return ApiClient.post('/user/login', { username, password })
            .then(response => {
                if (response.success) {
                    authToken = response.data.token;
                    localStorage.setItem('authToken', authToken);
                    localStorage.setItem('user', JSON.stringify(response.data.user));
                    return response;
                }
                throw new Error(response.message);
            });
    }
    
    static logout() {
        localStorage.removeItem('authToken');
        localStorage.removeItem('user');
        authToken = null;
        window.location.href = '/login';
    }
    
    static getCurrentUser() {
        const userStr = localStorage.getItem('user');
        return userStr ? JSON.parse(userStr) : null;
    }
    
    static isLoggedIn() {
        return !!authToken;
    }
}

// 学习行为记录
class BehaviorTracker {
    static sessionId = this.generateSessionId();
    
    static generateSessionId() {
        return 'session_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
    }
    
    static track(behaviorType, targetType = null, targetId = null, data = null) {
        const behavior = {
            behaviorType,
            targetType,
            targetId,
            sessionId: this.sessionId,
            data: data ? JSON.stringify(data) : null
        };
        
        ApiClient.post('/analysis/behavior', behavior)
            .catch(error => console.warn('Behavior tracking failed:', error));
    }
    
    static trackPageView(pageName) {
        this.track('page_view', 'page', null, { pageName });
    }
    
    static trackClick(elementType, elementId) {
        this.track('click', elementType, elementId);
    }
    
    static trackLearningStart(courseId) {
        this.track('learning_start', 'course', courseId);
    }
    
    static trackLearningEnd(courseId, duration) {
        this.track('learning_end', 'course', courseId, { duration });
    }
}

// 数据格式化工具
class DataFormatter {
    static formatTime(minutes) {
        if (minutes < 60) {
            return `${Math.round(minutes)}分钟`;
        } else {
            const hours = Math.floor(minutes / 60);
            const remainingMinutes = Math.round(minutes % 60);
            return `${hours}小时${remainingMinutes > 0 ? remainingMinutes + '分钟' : ''}`;
        }
    }
    
    static formatDate(date) {
        if (typeof date === 'string') {
            date = new Date(date);
        }
        
        const now = new Date();
        const diff = now - date;
        const daysDiff = Math.floor(diff / (1000 * 60 * 60 * 24));
        
        if (daysDiff === 0) {
            return '今天';
        } else if (daysDiff === 1) {
            return '昨天';
        } else if (daysDiff < 7) {
            return `${daysDiff}天前`;
        } else {
            return date.toLocaleDateString('zh-CN');
        }
    }
    
    static formatPercentage(value, decimals = 0) {
        return (value * 100).toFixed(decimals) + '%';
    }
    
    static formatNumber(value, decimals = 0) {
        return parseFloat(value).toFixed(decimals);
    }
}

// 图表工具
class ChartHelper {
    static createProgressChart(containerId, data) {
        const chart = echarts.init(document.getElementById(containerId));
        
        const option = {
            tooltip: {
                trigger: 'item',
                formatter: '{b}: {c}%'
            },
            series: [{
                name: '学习进度',
                type: 'pie',
                radius: '70%',
                data: data,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };
        
        chart.setOption(option);
        return chart;
    }
    
    static createMasteryChart(containerId, data) {
        const chart = echarts.init(document.getElementById(containerId));
        
        const option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                data: data.map(item => item.name),
                axisLabel: {
                    interval: 0,
                    rotate: 45
                }
            },
            yAxis: {
                type: 'value',
                max: 100,
                axisLabel: {
                    formatter: '{value}%'
                }
            },
            series: [{
                name: '掌握度',
                type: 'bar',
                data: data.map(item => (item.value * 100).toFixed(1)),
                itemStyle: {
                    color: function(params) {
                        const value = params.value;
                        if (value >= 80) return '#28a745';
                        if (value >= 60) return '#ffc107';
                        return '#dc3545';
                    }
                }
            }]
        };
        
        chart.setOption(option);
        return chart;
    }
    
    static createLearningTimeChart(containerId, data) {
        const chart = echarts.init(document.getElementById(containerId));
        
        const option = {
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                data: data.map(item => item.date)
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                    formatter: '{value}分钟'
                }
            },
            series: [{
                name: '学习时长',
                type: 'line',
                data: data.map(item => item.minutes),
                smooth: true,
                lineStyle: {
                    color: '#007bff'
                },
                areaStyle: {
                    color: {
                        type: 'linear',
                        x: 0,
                        y: 0,
                        x2: 0,
                        y2: 1,
                        colorStops: [{
                            offset: 0, color: 'rgba(0, 123, 255, 0.3)'
                        }, {
                            offset: 1, color: 'rgba(0, 123, 255, 0.1)'
                        }]
                    }
                }
            }]
        };
        
        chart.setOption(option);
        return chart;
    }
}

// 模态框工具
class ModalHelper {
    static show(title, content, buttons = []) {
        const modalId = 'modal-' + Date.now();
        let buttonHtml = '';
        
        if (buttons.length === 0) {
            buttonHtml = '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>';
        } else {
            buttonHtml = buttons.map(btn => 
                `<button type="button" class="btn btn-${btn.type || 'primary'}" 
                 onclick="${btn.onclick || ''}" 
                 ${btn.dismiss ? 'data-bs-dismiss="modal"' : ''}>${btn.text}</button>`
            ).join(' ');
        }
        
        const modalHtml = `
            <div class="modal fade" id="${modalId}" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">${title}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            ${content}
                        </div>
                        <div class="modal-footer">
                            ${buttonHtml}
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        $('body').append(modalHtml);
        const modal = new bootstrap.Modal(document.getElementById(modalId));
        
        // 模态框隐藏后删除DOM元素
        $(`#${modalId}`).on('hidden.bs.modal', function() {
            $(this).remove();
        });
        
        modal.show();
        return modal;
    }
    
    static confirm(title, message, onConfirm) {
        return this.show(title, message, [
            { text: '取消', type: 'secondary', dismiss: true },
            { text: '确定', type: 'primary', onclick: `${onConfirm.toString()}(); bootstrap.Modal.getInstance(this.closest('.modal')).hide();`, dismiss: false }
        ]);
    }
}

// 表单验证工具
class FormValidator {
    static validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
    
    static validatePhone(phone) {
        const re = /^1[3-9]\d{9}$/;
        return re.test(phone);
    }
    
    static validatePassword(password) {
        // 至少8位，包含字母和数字
        return password.length >= 8 && /[A-Za-z]/.test(password) && /\d/.test(password);
    }
    
    static showFieldError(fieldId, message) {
        const field = $(`#${fieldId}`);
        field.addClass('is-invalid');
        
        let feedback = field.next('.invalid-feedback');
        if (feedback.length === 0) {
            feedback = $('<div class="invalid-feedback"></div>');
            field.after(feedback);
        }
        feedback.text(message);
    }
    
    static clearFieldError(fieldId) {
        const field = $(`#${fieldId}`);
        field.removeClass('is-invalid');
        field.next('.invalid-feedback').remove();
    }
    
    static clearAllErrors(formId) {
        $(`#${formId} .is-invalid`).removeClass('is-invalid');
        $(`#${formId} .invalid-feedback`).remove();
    }
}

// 页面加载完成后初始化
$(document).ready(function() {
    // 初始化工具提示
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // 记录页面访问
    const pageName = document.title;
    BehaviorTracker.trackPageView(pageName);
    
    // 自动隐藏警告消息
    setTimeout(() => {
        $('.alert').alert('close');
    }, 5000);
    
    // 添加页面动画
    $('main .container, main .container-fluid').addClass('fade-in');
});

// 页面离开前的清理工作
$(window).on('beforeunload', function() {
    // 记录页面停留时间等
    const duration = Date.now() - window.performance.timing.navigationStart;
    BehaviorTracker.track('page_leave', 'page', null, { duration });
});