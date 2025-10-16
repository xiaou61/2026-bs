let currentPage = 1;
const pageSize = 9;

$(document).ready(function() {
    loadClubs();

    $('#searchBtn').on('click', function() {
        currentPage = 1;
        loadClubs();
    });

    $('#searchKeyword').on('keypress', function(e) {
        if (e.which === 13) {
            currentPage = 1;
            loadClubs();
        }
    });

    $('#createClubBtn').on('click', function() {
        if (!utils.requireAuth()) return;
        window.location.href = 'create-club.html';
    });
});

function loadClubs() {
    const category = $('#categoryFilter').val();
    const keyword = $('#searchKeyword').val();

    request.get('/clubs', {
        page: currentPage,
        size: pageSize,
        category: category || undefined,
        keyword: keyword || undefined
    })
    .done(function(res) {
        if (res.code === 200) {
            renderClubs(res.data.records);
            renderPagination(res.data.total, res.data.size, res.data.current);
        } else {
            $('#clubsList').html('<div class="col-12 text-center text-danger">加载失败</div>');
        }
    })
    .fail(function() {
        $('#clubsList').html('<div class="col-12 text-center text-danger">加载失败，请稍后重试</div>');
    });
}

function renderClubs(clubs) {
    if (clubs.length === 0) {
        $('#clubsList').html('<div class="col-12 text-center text-muted py-5">暂无社团</div>');
        return;
    }

    const html = clubs.map(club => `
        <div class="col-md-4">
            <div class="card h-100 shadow-sm">
                ${club.cover ? `<img src="${club.cover}" class="card-img-top club-logo" alt="${club.name}">` : 
                  `<div class="card-img-top club-logo bg-secondary d-flex align-items-center justify-content-center text-white">
                      <i class="bi bi-building" style="font-size: 3rem;"></i>
                  </div>`}
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-2">
                        <h5 class="card-title mb-0">${club.name}</h5>
                        ${club.isRecruiting === 1 ? '<span class="badge bg-success">招新中</span>' : ''}
                    </div>
                    <p class="card-text text-muted small">${club.description ? club.description.substring(0, 80) + (club.description.length > 80 ? '...' : '') : '暂无简介'}</p>
                    <div class="mb-2">
                        <span class="badge bg-primary">${club.category}</span>
                        <span class="badge bg-info">${utils.getStatusText(club.status, 'club')}</span>
                    </div>
                    <div class="d-flex justify-content-between align-items-center text-muted small">
                        <span><i class="bi bi-people-fill"></i> ${club.memberCount}人</span>
                        <span><i class="bi bi-person-badge"></i> ${club.presidentName || '未知'}</span>
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

    $('#clubsList').html(html);
}

function renderPagination(total, size, current) {
    const totalPages = Math.ceil(total / size);
    
    if (totalPages <= 1) {
        $('#pagination').html('');
        return;
    }

    let html = '<nav><ul class="pagination justify-content-center">';
    
    html += `<li class="page-item ${current <= 1 ? 'disabled' : ''}">
        <a class="page-link" href="#" data-page="${current - 1}">上一页</a>
    </li>`;
    
    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || (i >= current - 2 && i <= current + 2)) {
            html += `<li class="page-item ${i === current ? 'active' : ''}">
                <a class="page-link" href="#" data-page="${i}">${i}</a>
            </li>`;
        } else if (i === current - 3 || i === current + 3) {
            html += '<li class="page-item disabled"><span class="page-link">...</span></li>';
        }
    }
    
    html += `<li class="page-item ${current >= totalPages ? 'disabled' : ''}">
        <a class="page-link" href="#" data-page="${current + 1}">下一页</a>
    </li>`;
    
    html += '</ul></nav>';
    
    $('#pagination').html(html);
    
    $('#pagination a').on('click', function(e) {
        e.preventDefault();
        const page = parseInt($(this).data('page'));
        if (page && page !== currentPage) {
            currentPage = page;
            loadClubs();
            $('html, body').scrollTop(0);
        }
    });
}

