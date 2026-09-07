$(document).ready(function() {
    loadHotClubs();
    loadLatestActivities();
});

function loadHotClubs() {
    request.get('/clubs', { page: 1, size: 4 })
        .done(function(res) {
            if (res.code === 200 && res.data.records) {
                renderClubs(res.data.records);
            } else {
                $('#hotClubs').html('<div class="col-12 text-center text-muted">暂无数据</div>');
            }
        })
        .fail(function() {
            $('#hotClubs').html('<div class="col-12 text-center text-danger">加载失败</div>');
        });
}

function renderClubs(clubs) {
    if (clubs.length === 0) {
        $('#hotClubs').html('<div class="col-12 text-center text-muted">暂无社团</div>');
        return;
    }
    
    const html = clubs.map(club => `
        <div class="col-md-3 col-sm-6">
            <div class="card h-100 shadow-sm">
                <img src="${club.cover || 'https://via.placeholder.com/300x200?text=' + encodeURIComponent(club.name)}" 
                     class="card-img-top club-logo" alt="${club.name}">
                <div class="card-body">
                    <h5 class="card-title">${club.name}</h5>
                    <p class="card-text text-muted small">${club.description ? club.description.substring(0, 50) + '...' : '暂无简介'}</p>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="badge bg-primary">${club.category}</span>
                        <small class="text-muted">
                            <i class="bi bi-people-fill"></i> ${club.memberCount}人
                        </small>
                    </div>
                </div>
                <div class="card-footer bg-transparent border-0">
                    <a href="club-detail.html?id=${club.id}" class="btn btn-sm btn-outline-primary w-100">
                        <i class="bi bi-eye"></i> 查看详情
                    </a>
                </div>
            </div>
        </div>
    `).join('');
    
    $('#hotClubs').html(html);
}

function loadLatestActivities() {
    request.get('/activities', { page: 1, size: 4, status: 0 })
        .done(function(res) {
            if (res.code === 200 && res.data.records) {
                renderActivities(res.data.records);
            } else {
                $('#latestActivities').html('<div class="col-12 text-center text-muted">暂无数据</div>');
            }
        })
        .fail(function() {
            $('#latestActivities').html('<div class="col-12 text-center text-danger">加载失败</div>');
        });
}

function renderActivities(activities) {
    if (activities.length === 0) {
        $('#latestActivities').html('<div class="col-12 text-center text-muted">暂无活动</div>');
        return;
    }
    
    const html = activities.map(activity => `
        <div class="col-md-3 col-sm-6">
            <div class="card h-100 shadow-sm">
                <img src="${activity.cover || 'https://via.placeholder.com/300x180?text=' + encodeURIComponent(activity.title)}" 
                     class="card-img-top activity-cover" alt="${activity.title}">
                <div class="card-body">
                    <h5 class="card-title">${activity.title}</h5>
                    <p class="card-text">
                        <small class="text-muted">
                            <i class="bi bi-building"></i> ${activity.clubName || '未知社团'}
                        </small>
                    </p>
                    <p class="card-text">
                        <small class="text-muted">
                            <i class="bi bi-calendar"></i> ${utils.formatDate(activity.startTime)}
                        </small>
                    </p>
                    <p class="card-text">
                        <small class="text-muted">
                            <i class="bi bi-geo-alt"></i> ${activity.location || '待定'}
                        </small>
                    </p>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="badge bg-${utils.getStatusClass(activity.status)}">${utils.getStatusText(activity.status)}</span>
                        <small class="text-muted">${activity.currentParticipants}/${activity.maxParticipants}人</small>
                    </div>
                </div>
                <div class="card-footer bg-transparent border-0">
                    <a href="activity-detail.html?id=${activity.id}" class="btn btn-sm btn-outline-primary w-100">
                        <i class="bi bi-eye"></i> 查看详情
                    </a>
                </div>
            </div>
        </div>
    `).join('');
    
    $('#latestActivities').html(html);
}

