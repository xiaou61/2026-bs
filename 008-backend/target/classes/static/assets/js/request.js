function getToken() {
    return localStorage.getItem('token');
}

function setToken(token) {
    localStorage.setItem('token', token);
}

function removeToken() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
}

function getUser() {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
}

function setUser(user) {
    localStorage.setItem('user', JSON.stringify(user));
}

function request(url, options = {}) {
    const token = getToken();
    
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    if (token) {
        defaultOptions.headers['Authorization'] = `Bearer ${token}`;
    }

    const finalOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...(options.headers || {})
        }
    };

    return $.ajax({
        url: API_BASE_URL + url,
        ...finalOptions,
        error: function(xhr) {
            if (xhr.status === 401 || xhr.status === 403) {
                layer.msg('登录已过期，请重新登录');
                removeToken();
                setTimeout(() => {
                    window.location.href = '/index.html';
                }, 1500);
            }
        }
    });
}

function checkAuth() {
    const token = getToken();
    if (!token) {
        window.location.href = '/index.html';
        return false;
    }
    return true;
}

function logout() {
    layer.confirm('确定要退出登录吗？', {
        btn: ['确定', '取消']
    }, function(index) {
        removeToken();
        layer.msg('已退出登录');
        setTimeout(() => {
            window.location.href = '/index.html';
        }, 1000);
        layer.close(index);
    });
}

