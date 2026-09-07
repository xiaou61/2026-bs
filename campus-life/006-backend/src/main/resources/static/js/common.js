function loadUser() {
    $.get('/api/user/current', function(res) {
        if (res.code === 200 && res.data) {
            $('#username').text(res.data.realName);
            if (res.data.role === 'admin') {
                $('#adminMenu').html(`
                    <div style="margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee;">
                        <a href="/admin/users" class="menu-item">用户管理</a>
                        <a href="/admin/categories" class="menu-item">分类管理</a>
                    </div>
                `);
            }
        }
    });
}

function loadUnreadCount() {
    $.get('/api/notification/unread-count', function(res) {
        if (res.code === 200 && res.data > 0) {
            $('#unreadCount').text(res.data).show();
        } else {
            $('#unreadCount').hide();
        }
    });
}

function logout() {
    if (confirm('确定要退出登录吗？')) {
        $.get('/api/user/logout', function(res) {
            if (res.code === 200) {
                window.location.href = '/login';
            }
        });
    }
}

function formatDate(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    return date.toLocaleString('zh-CN');
}

function getUrlParam(name) {
    const params = new URLSearchParams(window.location.search);
    return params.get(name);
}

