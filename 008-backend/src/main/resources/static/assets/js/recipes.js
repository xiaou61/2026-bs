let currentPage = 1;
const pageSize = 12;

$(document).ready(function() {
    if (!checkAuth()) return;

    const user = getUser();
    $('#userName').text(user.nickname || user.username);

    loadRecipes();
});

function loadRecipes(page = 1) {
    currentPage = page;
    
    const params = {
        pageNum: currentPage,
        pageSize: pageSize,
        name: $('#searchName').val(),
        cuisineType: $('#cuisineType').val(),
        difficulty: $('#difficulty').val(),
        maxCookingTime: $('#maxCookingTime').val()
    };

    const queryString = $.param(params);
    
    request(`${API_ENDPOINTS.RECIPE_LIST}?${queryString}`, {
        method: 'GET'
    }).done(function(res) {
        if (res.code === 200) {
            renderRecipes(res.data.records);
            renderPagination(res.data.total, res.data.pages);
        }
    }).fail(function() {
        $('#recipeList').html('<div class="empty-state"><i class="fas fa-exclamation-circle"></i><p>加载失败</p></div>');
    });
}

function renderRecipes(recipes) {
    if (!recipes || recipes.length === 0) {
        $('#recipeList').html('<div class="col-12"><div class="empty-state"><i class="fas fa-utensils"></i><p>暂无菜谱</p></div></div>');
        return;
    }

    let html = '';
    recipes.forEach(recipe => {
        html += `
            <div class="col-md-3 mb-3">
                <div class="recipe-card card" onclick="viewRecipe(${recipe.id})">
                    <img src="${recipe.image || 'https://via.placeholder.com/300x200?text=菜谱图片'}" alt="${recipe.name}">
                    <div class="card-body">
                        <div class="recipe-title" title="${recipe.name}">${recipe.name}</div>
                        <div class="recipe-meta">
                            <span><i class="fas fa-clock"></i> ${recipe.cookingTime}分钟</span>
                            <span class="badge bg-${DIFFICULTY_CLASS[recipe.difficulty]}">${DIFFICULTY_MAP[recipe.difficulty]}</span>
                        </div>
                        <div class="recipe-meta mt-2">
                            <span><i class="fas fa-eye"></i> ${recipe.viewCount}</span>
                            <span><i class="fas fa-star"></i> ${recipe.ratingScore}</span>
                            <span><i class="fas fa-heart"></i> ${recipe.collectCount}</span>
                        </div>
                    </div>
                </div>
            </div>
        `;
    });
    $('#recipeList').html(html);
}

function renderPagination(total, pages) {
    if (pages <= 1) {
        $('#pagination').html('');
        return;
    }

    let html = '<ul class="pagination justify-content-center">';
    
    html += `<li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
        <a class="page-link" href="#" onclick="loadRecipes(${currentPage - 1}); return false;">上一页</a>
    </li>`;

    for (let i = 1; i <= pages; i++) {
        if (i === 1 || i === pages || (i >= currentPage - 2 && i <= currentPage + 2)) {
            html += `<li class="page-item ${i === currentPage ? 'active' : ''}">
                <a class="page-link" href="#" onclick="loadRecipes(${i}); return false;">${i}</a>
            </li>`;
        } else if (i === currentPage - 3 || i === currentPage + 3) {
            html += '<li class="page-item disabled"><span class="page-link">...</span></li>';
        }
    }

    html += `<li class="page-item ${currentPage === pages ? 'disabled' : ''}">
        <a class="page-link" href="#" onclick="loadRecipes(${currentPage + 1}); return false;">下一页</a>
    </li>`;

    html += '</ul>';
    $('#pagination').html(html);
}

function searchRecipes() {
    loadRecipes(1);
}

function resetSearch() {
    $('#searchName').val('');
    $('#cuisineType').val('');
    $('#difficulty').val('');
    $('#maxCookingTime').val('');
    loadRecipes(1);
}

function viewRecipe(id) {
    window.location.href = `recipe-detail.html?id=${id}`;
}

