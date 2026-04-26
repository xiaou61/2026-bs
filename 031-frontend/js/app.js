const API_BASE = '/api';

function getToken() {
    return localStorage.getItem('token');
}

function setToken(token) {
    localStorage.setItem('token', token);
}

function getUser() {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
}

function setUser(user) {
    localStorage.setItem('user', JSON.stringify(user));
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = 'index.html';
}

function checkAuth() {
    const token = getToken();
    const user = getUser();

    if (token && user) {
        const navUser = document.getElementById('nav-user');
        if (navUser) {
            navUser.innerHTML = `
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        ${user.username}
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="profile.html">个人中心</a></li>
                        <li><a class="dropdown-item" href="my-orders.html">我的订单</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#" onclick="logout()">退出登录</a></li>
                    </ul>
                </li>
            `;
        }
    }
}

document.addEventListener('DOMContentLoaded', function() {
    checkAuth();

    document.body.addEventListener('htmx:configRequest', function(event) {
        const token = getToken();
        if (token) {
            event.detail.headers['Authorization'] = 'Bearer ' + token;
        }
    });

    document.body.addEventListener('htmx:afterRequest', function(event) {
        let response;
        try {
            response = JSON.parse(event.detail.xhr.responseText);
        } catch (error) {
            return;
        }

        if (event.detail.xhr.status === 401 || response.code === 401) {
            logout();
            return;
        }

        const requestPath = event.detail.pathInfo.requestPath || '';

        if (requestPath.includes('/auth/login')) {
            handleLoginResponse(response, event.detail.target);
            return;
        }

        if (requestPath.includes('/auth/register')) {
            handleRegisterResponse(response, event.detail.target);
            return;
        }

        if (requestPath.includes('/matches')) {
            if (response.code === 200 && Array.isArray(response.data)) {
                renderMatches(response.data, event.detail.target);
            }
        }
    });
});

function handleLoginResponse(response, target) {
    if (response.code === 200) {
        setToken(response.data.token);
        setUser(response.data.user);
        window.location.href = 'index.html';
        return;
    }
    target.innerHTML = `<div class="alert alert-danger">${response.message}</div>`;
}

function handleRegisterResponse(response, target) {
    if (response.code === 200) {
        target.innerHTML = '<div class="alert alert-success">注册成功，正在跳转到登录页...</div>';
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 1200);
        return;
    }
    target.innerHTML = `<div class="alert alert-danger">${response.message}</div>`;
}

function renderMatches(matches, container) {
    if (matches.length === 0) {
        container.innerHTML = '<div class="col-12 text-center"><p>暂无比赛</p></div>';
        return;
    }

    container.innerHTML = matches.map(match => `
        <div class="col-md-6 col-lg-4">
            <div class="card match-card h-100">
                <div class="card-body">
                    <div class="match-date">
                        ${formatDateTime(match.matchDate)}
                    </div>
                    <div class="team-vs">
                        <div class="team-name">${match.homeTeamName || match.homeTeamId}</div>
                        <div class="vs-text">VS</div>
                        <div class="team-name">${match.awayTeamName || match.awayTeamId}</div>
                    </div>
                    <div class="text-center mb-2">
                        <small class="text-muted">${match.league} ${match.season ? `· ${match.season}赛季` : ''}</small>
                    </div>
                    <div class="text-center mb-3">
                        <small class="text-muted">${match.stadiumName || '待定场馆'}</small>
                    </div>
                    <div class="d-grid">
                        <a href="booking.html?id=${match.id}" class="btn btn-primary">
                            立即购票
                        </a>
                    </div>
                </div>
            </div>
        </div>
    `).join('');
}

function formatDateTime(dateTimeStr) {
    const date = new Date(dateTimeStr);
    return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}
