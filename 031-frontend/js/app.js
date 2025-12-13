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
        if (event.detail.xhr.status === 401) {
            logout();
        }
        
        if (event.detail.successful) {
            const response = JSON.parse(event.detail.xhr.responseText);
            
            if (event.detail.pathInfo.requestPath.includes('/auth/login')) {
                if (response.code === 200) {
                    setToken(response.data.token);
                    setUser(response.data.user);
                    window.location.href = 'index.html';
                } else {
                    event.detail.target.innerHTML = 
                        `<div class="alert alert-danger">${response.message}</div>`;
                }
            }
            
            if (event.detail.pathInfo.requestPath.includes('/matches')) {
                if (response.code === 200 && Array.isArray(response.data)) {
                    renderMatches(response.data, event.detail.target);
                }
            }
        }
    });
});

function renderMatches(matches, container) {
    if (matches.length === 0) {
        container.innerHTML = '<div class="col-12 text-center"><p>暂无比赛</p></div>';
        return;
    }
    
    container.innerHTML = matches.map(match => `
        <div class="col-md-6 col-lg-4">
            <div class="card match-card">
                <div class="card-body">
                    <div class="match-date">
                        ${formatDateTime(match.matchDate)}
                    </div>
                    <div class="team-vs">
                        <div class="team-name">${match.homeTeamId}</div>
                        <div class="vs-text">VS</div>
                        <div class="team-name">${match.awayTeamId}</div>
                    </div>
                    <div class="text-center mb-3">
                        <small class="text-muted">${match.league} - ${match.season}</small>
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
