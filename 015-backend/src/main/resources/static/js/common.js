const API_BASE_URL = 'http://localhost:8080/api';

const TOKEN_KEY = 'token';
const USER_INFO_KEY = 'userInfo';

function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
}

function removeToken() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_INFO_KEY);
}

function getUserInfo() {
    const userInfoStr = localStorage.getItem(USER_INFO_KEY);
    return userInfoStr ? JSON.parse(userInfoStr) : null;
}

function setUserInfo(userInfo) {
    localStorage.setItem(USER_INFO_KEY, JSON.stringify(userInfo));
}

function checkLogin() {
    const token = getToken();
    if (!token) {
        window.location.href = '/login.html';
        return false;
    }
    return true;
}

function logout() {
    removeToken();
    window.location.href = '/login.html';
}

function request(url, options = {}) {
    const token = getToken();
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };
    
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }
    
    return fetch(API_BASE_URL + url, {
        ...options,
        headers
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 401) {
            removeToken();
            window.location.href = '/login.html';
            throw new Error('未登录或登录已过期');
        }
        if (data.code !== 200) {
            throw new Error(data.message || '请求失败');
        }
        return data.data;
    });
}

function formatTime(dateString) {
    if (!dateString) return '';
    
    const date = new Date(dateString);
    const now = new Date();
    const diff = now - date;
    
    const minutes = Math.floor(diff / 60000);
    const hours = Math.floor(diff / 3600000);
    const days = Math.floor(diff / 86400000);
    
    if (minutes < 1) return '刚刚';
    if (minutes < 60) return `${minutes}分钟前`;
    if (hours < 24) return `${hours}小时前`;
    if (days < 7) return `${days}天前`;
    
    return date.toLocaleDateString('zh-CN');
}

function showToast(message, type = 'info') {
    const toastHtml = `
        <div class="toast align-items-center text-white bg-${type} border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">${message}</div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `;
    
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        container.className = 'toast-container position-fixed top-0 end-0 p-3';
        container.style.zIndex = '9999';
        document.body.appendChild(container);
    }
    
    const toastElement = $(toastHtml).appendTo(container);
    const toast = new bootstrap.Toast(toastElement[0]);
    toast.show();
    
    toastElement.on('hidden.bs.toast', function() {
        toastElement.remove();
    });
}

function getCategoryName(category) {
    const categories = {
        'CONFESS': '表白墙',
        'COMPLAIN': '吐槽墙',
        'HELP': '求助墙',
        'WHISPER': '树洞墙',
        'CAMPUS': '校园墙',
        'CHAT': '闲聊墙'
    };
    return categories[category] || category;
}

function getCategoryClass(category) {
    return 'category-' + category.toLowerCase();
}

function getReportTypeName(type) {
    const types = {
        'PORN': '色情低俗',
        'VIOLENCE': '暴力血腥',
        'AD': '广告营销',
        'FRAUD': '诈骗信息',
        'ATTACK': '人身攻击',
        'OTHER': '其他'
    };
    return types[type] || type;
}

function parseImages(imagesStr) {
    if (!imagesStr) return [];
    try {
        return JSON.parse(imagesStr);
    } catch (e) {
        return [];
    }
}

function renderPostCard(post) {
    const images = parseImages(post.images);
    const imagesHtml = images.length > 0 ? `
        <div class="post-images">
            ${images.map(img => `<img src="${img}" class="post-image" onclick="viewImage('${img}')">`).join('')}
        </div>
    ` : '';
    
    const tags = post.tags ? post.tags.split(',').filter(t => t.trim()) : [];
    const tagsHtml = tags.length > 0 ? `
        <div class="post-tags">
            ${tags.map(tag => `<span class="post-tag">${tag}</span>`).join('')}
        </div>
    ` : '';
    
    return `
        <div class="card post-card fade-in" data-post-id="${post.id}">
            <div class="post-header">
                <img src="${post.anonymousAvatar || '/img/avatar.png'}" class="post-avatar">
                <div class="post-info">
                    <div class="post-nickname">${post.anonymousNickname || '匿名用户'}</div>
                    <div class="post-meta">
                        ${formatTime(post.createTime)}
                        <span class="post-category ${getCategoryClass(post.category)}">${getCategoryName(post.category)}</span>
                    </div>
                </div>
            </div>
            ${post.title ? `<h5 class="mb-3">${post.title}</h5>` : ''}
            <div class="post-content">${post.content}</div>
            ${imagesHtml}
            ${tagsHtml}
            <div class="post-stats">
                <span class="post-stat"><i class="bi bi-eye"></i> ${post.viewCount || 0}</span>
                <span class="post-stat"><i class="bi bi-heart"></i> ${post.likeCount || 0}</span>
                <span class="post-stat"><i class="bi bi-chat"></i> ${post.commentCount || 0}</span>
                <div class="post-actions">
                    <button class="action-btn" onclick="likePost(${post.id})">
                        <i class="bi bi-heart"></i>
                    </button>
                    <button class="action-btn" onclick="goToPost(${post.id})">
                        <i class="bi bi-chat"></i>
                    </button>
                </div>
            </div>
        </div>
    `;
}

function goToPost(postId) {
    window.location.href = `/post-detail.html?id=${postId}`;
}

function viewImage(src) {
    const modal = `
        <div class="modal fade" id="imageModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <div class="modal-body p-0">
                        <img src="${src}" class="w-100">
                    </div>
                </div>
            </div>
        </div>
    `;
    $(modal).appendTo('body');
    const imageModal = new bootstrap.Modal(document.getElementById('imageModal'));
    imageModal.show();
    $('#imageModal').on('hidden.bs.modal', function() {
        $(this).remove();
    });
}

function showLoading(container) {
    $(container).html('<div class="loading"><i class="bi bi-hourglass-split"></i> 加载中...</div>');
}

function showEmpty(container, message = '暂无数据') {
    $(container).html(`
        <div class="empty-state">
            <i class="bi bi-inbox"></i>
            <p>${message}</p>
        </div>
    `);
}

function debounce(func, wait) {
    let timeout;
    return function(...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

$(document).ready(function() {
    $('[data-bs-toggle="tooltip"]').tooltip();
});

