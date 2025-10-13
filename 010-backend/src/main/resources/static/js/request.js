const API_BASE_URL = 'http://localhost:8010';

const request = {
    getToken() {
        return localStorage.getItem('token');
    },

    setToken(token) {
        localStorage.setItem('token', token);
    },

    removeToken() {
        localStorage.removeItem('token');
    },

    getUserInfo() {
        const userStr = localStorage.getItem('userInfo');
        return userStr ? JSON.parse(userStr) : null;
    },

    setUserInfo(user) {
        localStorage.setItem('userInfo', JSON.stringify(user));
    },

    removeUserInfo() {
        localStorage.removeItem('userInfo');
    },

    get(url, params) {
        return this.ajax('GET', url, params);
    },

    post(url, data) {
        return this.ajax('POST', url, data);
    },

    put(url, data) {
        return this.ajax('PUT', url, data);
    },

    delete(url, data) {
        return this.ajax('DELETE', url, data);
    },

    ajax(method, url, data) {
        const token = this.getToken();
        const headers = {
            'Content-Type': 'application/json'
        };
        
        if (token) {
            headers['Authorization'] = 'Bearer ' + token;
        }

        let ajaxUrl = API_BASE_URL + url;
        let ajaxData = null;

        if (method === 'GET' && data) {
            const queryString = Object.keys(data)
                .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(data[key]))
                .join('&');
            ajaxUrl += '?' + queryString;
        } else if (data) {
            ajaxData = JSON.stringify(data);
        }

        return new Promise((resolve, reject) => {
            $.ajax({
                url: ajaxUrl,
                type: method,
                headers: headers,
                data: ajaxData,
                success: function(response) {
                    if (response.code === 200) {
                        resolve(response.data);
                    } else {
                        layer.msg(response.message || '操作失败', {icon: 2});
                        reject(response);
                    }
                },
                error: function(xhr) {
                    if (xhr.status === 401) {
                        layer.msg('未登录或登录已过期', {icon: 2});
                        request.removeToken();
                        request.removeUserInfo();
                        setTimeout(() => {
                            window.location.href = '/login.html';
                        }, 1500);
                    } else {
                        const errorMsg = xhr.responseJSON?.message || '网络错误';
                        layer.msg(errorMsg, {icon: 2});
                    }
                    reject(xhr);
                }
            });
        });
    },

    checkLogin() {
        const token = this.getToken();
        if (!token) {
            window.location.href = '/login.html';
            return false;
        }
        return true;
    },

    logout() {
        this.removeToken();
        this.removeUserInfo();
        layer.msg('已退出登录', {icon: 1});
        setTimeout(() => {
            window.location.href = '/login.html';
        }, 1000);
    }
};

