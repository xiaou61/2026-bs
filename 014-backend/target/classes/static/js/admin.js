$(document).ready(function() {
    if (!auth.isLoggedIn()) {
        window.location.href = 'admin-login.html';
        return;
    }

    const user = auth.getUser();
    $('#adminName').text(user.username || '管理员');

    $('#logoutBtn').on('click', function() {
        if (confirm('确定要退出登录吗？')) {
            auth.logout();
            window.location.href = 'admin-login.html';
        }
    });

    $('.list-group-item').on('click', function(e) {
        e.preventDefault();
        const tab = $(this).data('tab');
        
        $('.list-group-item').removeClass('active');
        $(this).addClass('active');
        
        $('.tab-content').addClass('d-none');
        $('#' + tab).removeClass('d-none');
        
        switch(tab) {
            case 'statistics':
                loadStatistics();
                break;
            case 'users':
                loadUsers();
                break;
            case 'clubs':
                loadPendingClubs();
                break;
            case 'topics':
                loadTopics();
                break;
        }
    });

    $('#searchUserBtn').on('click', loadUsers);
    
    loadStatistics();
});

function loadStatistics() {
    request.get('/admin/statistics')
        .done(function(res) {
            if (res.code === 200) {
                renderStatistics(res.data);
            }
        })
        .fail(function() {
            $('#statsCards').html('<div class="col-12 text-center text-danger">加载失败</div>');
        });
}

function renderStatistics(stats) {
    const html = `
        <div class="col-md-3 mb-3">
            <div class="card text-center border-primary">
                <div class="card-body">
                    <i class="bi bi-people-fill text-primary" style="font-size: 2rem;"></i>
                    <h3 class="mt-2">${stats.userCount || 0}</h3>
                    <p class="text-muted mb-0">用户总数</p>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card text-center border-success">
                <div class="card-body">
                    <i class="bi bi-building text-success" style="font-size: 2rem;"></i>
                    <h3 class="mt-2">${stats.clubCount || 0}</h3>
                    <p class="text-muted mb-0">社团总数</p>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card text-center border-info">
                <div class="card-body">
                    <i class="bi bi-calendar-event text-info" style="font-size: 2rem;"></i>
                    <h3 class="mt-2">${stats.activityCount || 0}</h3>
                    <p class="text-muted mb-0">活动总数</p>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card text-center border-warning">
                <div class="card-body">
                    <i class="bi bi-chat-dots text-warning" style="font-size: 2rem;"></i>
                    <h3 class="mt-2">${stats.topicCount || 0}</h3>
                    <p class="text-muted mb-0">话题总数</p>
                </div>
            </div>
        </div>
    `;
    $('#statsCards').html(html);
}

function loadUsers() {
    const keyword = $('#userSearch').val();
    request.get('/admin/users', { page: 1, size: 20, keyword: keyword || undefined })
        .done(function(res) {
            if (res.code === 200) {
                renderUsers(res.data.records);
            }
        })
        .fail(function() {
            $('#usersList').html('<div class="alert alert-danger">加载失败</div>');
        });
}

function renderUsers(users) {
    if (users.length === 0) {
        $('#usersList').html('<div class="alert alert-info">暂无用户</div>');
        return;
    }

    let html = `
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>用户名</th>
                    <th>真实姓名</th>
                    <th>学号</th>
                    <th>邮箱</th>
                    <th>积分</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
    `;

    users.forEach(user => {
        html += `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.realName || '-'}</td>
                <td>${user.studentId || '-'}</td>
                <td>${user.email || '-'}</td>
                <td><span class="badge bg-primary">${user.points}</span></td>
                <td>
                    ${user.status === 0 ? 
                        '<span class="badge bg-success">正常</span>' : 
                        '<span class="badge bg-danger">禁用</span>'}
                </td>
                <td>
                    ${user.status === 0 ? 
                        `<button class="btn btn-sm btn-danger" onclick="updateUserStatus(${user.id}, 1)">禁用</button>` :
                        `<button class="btn btn-sm btn-success" onclick="updateUserStatus(${user.id}, 0)">启用</button>`}
                </td>
            </tr>
        `;
    });

    html += '</tbody></table>';
    $('#usersList').html(html);
}

function updateUserStatus(userId, status) {
    const action = status === 1 ? '禁用' : '启用';
    if (!confirm(`确定要${action}该用户吗？`)) return;

    $.ajax({
        url: `/api/admin/users/${userId}/status?status=${status}`,
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + auth.getToken()
        }
    })
    .done(function(res) {
        if (res.code === 200) {
            utils.showToast(`${action}成功`);
            loadUsers();
        } else {
            utils.showToast(res.message || `${action}失败`, 'error');
        }
    })
    .fail(function() {
        utils.showToast(`${action}失败`, 'error');
    });
}

function loadPendingClubs() {
    request.get('/admin/clubs/pending', { page: 1, size: 20 })
        .done(function(res) {
            if (res.code === 200) {
                renderPendingClubs(res.data.records);
            }
        })
        .fail(function() {
            $('#clubsList').html('<div class="alert alert-danger">加载失败</div>');
        });
}

function renderPendingClubs(clubs) {
    if (clubs.length === 0) {
        $('#clubsList').html('<div class="alert alert-info">暂无待审核社团</div>');
        return;
    }

    const html = clubs.map(club => `
        <div class="card mb-3">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8">
                        <h5>${club.name}</h5>
                        <p class="text-muted mb-2">${club.description || '暂无简介'}</p>
                        <div>
                            <span class="badge bg-primary me-2">${club.category}</span>
                            <span class="text-muted">创建时间：${utils.formatDate(club.createTime)}</span>
                        </div>
                    </div>
                    <div class="col-md-4 text-end">
                        <button class="btn btn-success me-2" onclick="approveClub(${club.id}, 1)">
                            <i class="bi bi-check-circle"></i> 通过
                        </button>
                        <button class="btn btn-danger" onclick="approveClub(${club.id}, 2)">
                            <i class="bi bi-x-circle"></i> 拒绝
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `).join('');

    $('#clubsList').html(html);
}

function approveClub(clubId, status) {
    const action = status === 1 ? '通过' : '拒绝';
    if (!confirm(`确定要${action}该社团吗？`)) return;

    $.ajax({
        url: `/api/admin/clubs/${clubId}/approve?status=${status}`,
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + auth.getToken()
        }
    })
    .done(function(res) {
        if (res.code === 200) {
            utils.showToast(`${action}成功`);
            loadPendingClubs();
        } else {
            utils.showToast(res.message || `${action}失败`, 'error');
        }
    })
    .fail(function() {
        utils.showToast(`${action}失败`, 'error');
    });
}

function loadTopics() {
    request.get('/admin/topics', { page: 1, size: 20 })
        .done(function(res) {
            if (res.code === 200) {
                renderTopics(res.data.records);
            }
        })
        .fail(function() {
            $('#topicsList').html('<div class="alert alert-danger">加载失败</div>');
        });
}

function renderTopics(topics) {
    if (topics.length === 0) {
        $('#topicsList').html('<div class="alert alert-info">暂无话题</div>');
        return;
    }

    let html = `
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>标题</th>
                    <th>发布者</th>
                    <th>浏览/点赞/评论</th>
                    <th>发布时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
    `;

    topics.forEach(topic => {
        html += `
            <tr>
                <td>${topic.title}</td>
                <td>${topic.username || '未知'}</td>
                <td>
                    <i class="bi bi-eye"></i> ${topic.viewCount}
                    <i class="bi bi-heart ms-2"></i> ${topic.likeCount}
                    <i class="bi bi-chat ms-2"></i> ${topic.commentCount}
                </td>
                <td>${utils.formatDate(topic.createTime)}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="topTopic(${topic.id}, ${topic.isTop === 1 ? 0 : 1})">
                        ${topic.isTop === 1 ? '取消置顶' : '置顶'}
                    </button>
                    <button class="btn btn-sm btn-danger ms-1" onclick="deleteTopic(${topic.id})">删除</button>
                </td>
            </tr>
        `;
    });

    html += '</tbody></table>';
    $('#topicsList').html(html);
}

function topTopic(topicId, isTop) {
    $.ajax({
        url: `/api/admin/topics/${topicId}/top?isTop=${isTop}`,
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + auth.getToken()
        }
    })
    .done(function(res) {
        if (res.code === 200) {
            utils.showToast(isTop === 1 ? '置顶成功' : '取消置顶成功');
            loadTopics();
        }
    })
    .fail(function() {
        utils.showToast('操作失败', 'error');
    });
}

function deleteTopic(topicId) {
    if (!confirm('确定要删除该话题吗？')) return;

    request.delete(`/admin/topics/${topicId}`)
        .done(function(res) {
            if (res.code === 200) {
                utils.showToast('删除成功');
                loadTopics();
            }
        })
        .fail(function() {
            utils.showToast('删除失败', 'error');
        });
}

