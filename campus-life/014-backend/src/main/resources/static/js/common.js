const API_BASE = '/api';

const auth = {
    getToken: () => localStorage.getItem('token'),
    setToken: (token) => localStorage.setItem('token', token),
    removeToken: () => localStorage.removeItem('token'),
    getUser: () => {
        try {
            return JSON.parse(localStorage.getItem('user') || '{}');
        } catch (e) {
            return {};
        }
    },
    setUser: (user) => localStorage.setItem('user', JSON.stringify(user)),
    removeUser: () => localStorage.removeItem('user'),
    isLoggedIn: () => {
        const token = localStorage.getItem('token');
        return !!token && token !== 'null' && token !== 'undefined';
    },
    isAdmin: () => auth.getUser().role === 'admin',
    logout: (redirectUrl) => {
        auth.removeToken();
        auth.removeUser();
        const fallback = window.location.pathname.includes('admin') ? 'admin-login.html' : 'login.html';
        window.location.href = redirectUrl || fallback;
    }
};

function buildAuthHeaders() {
    const token = auth.getToken();
    if (!token || token === 'null' || token === 'undefined') {
        return {};
    }
    return {
        'Authorization': 'Bearer ' + token
    };
}

const request = {
    get: (url, data = {}) => {
        const queryString = Object.keys(data).length ? '?' + $.param(data) : '';
        return $.ajax({
            url: API_BASE + url + queryString,
            method: 'GET',
            headers: buildAuthHeaders()
        });
    },
    
    post: (url, data = {}) => {
        return $.ajax({
            url: API_BASE + url,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            headers: buildAuthHeaders()
        });
    },
    
    put: (url, data = {}) => {
        return $.ajax({
            url: API_BASE + url,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            headers: buildAuthHeaders()
        });
    },
    
    delete: (url) => {
        return $.ajax({
            url: API_BASE + url,
            method: 'DELETE',
            headers: buildAuthHeaders()
        });
    }
};

const utils = {
    showToast: (message, type = 'success') => {
        const bgColor = type === 'success' ? 'bg-success' : 'bg-danger';
        const toast = $(`
            <div class="toast align-items-center text-white ${bgColor} border-0 position-fixed top-0 end-0 m-3" 
                 role="alert" style="z-index: 9999;">
                <div class="d-flex">
                    <div class="toast-body">${message}</div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        `);
        $('body').append(toast);
        const bsToast = new bootstrap.Toast(toast[0]);
        bsToast.show();
        toast.on('hidden.bs.toast', () => toast.remove());
    },
    
    formatDate: (dateString) => {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleString('zh-CN', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        });
    },
    
    getStatusText: (status, type = 'activity') => {
        if (type === 'activity') {
            const statusMap = {
                0: '报名中',
                1: '进行中',
                2: '已结束',
                3: '已取消'
            };
            return statusMap[status] || '未知';
        } else if (type === 'club') {
            const statusMap = {
                0: '待审核',
                1: '正常',
                2: '已拒绝',
                3: '已解散'
            };
            return statusMap[status] || '未知';
        }
    },
    
    getStatusClass: (status) => {
        const classMap = {
            0: 'success',
            1: 'primary',
            2: 'secondary',
            3: 'danger'
        };
        return classMap[status] || 'secondary';
    },
    
    requireAuth: () => {
        if (!auth.isLoggedIn()) {
            window.location.href = 'login.html';
            return false;
        }
        return true;
    }
};

$(document).ready(function() {
    if (auth.isLoggedIn()) {
        $('#loginNav').addClass('d-none');
        $('#userNav').removeClass('d-none');
        $('#username').text(auth.getUser().username || '用户');
    }
    
    $('#logoutBtn').on('click', function(e) {
        e.preventDefault();
        if (confirm('确定要退出登录吗？')) {
            auth.logout();
        }
    });
    
    $(document).ajaxError(function(event, jqXHR) {
        if (jqXHR.status === 401) {
            auth.logout();
        } else if (jqXHR.status >= 500) {
            utils.showToast('服务器错误，请稍后重试', 'error');
        }
    });
});

