$(document).ready(function() {
    if (!checkAuth()) return;

    const user = getUser();
    $('#userName').text(user.nickname || user.username);

    loadTodayRecommend();
    loadHotRecipes();
    loadCookingStats();
    loadIngredientCount();
    loadDailyNutrition();
});

function loadTodayRecommend() {
    request(API_ENDPOINTS.RECOMMEND_TODAY, {
        method: 'GET'
    }).done(function(res) {
        if (res.code === 200 && res.data && res.data.length > 0) {
            let html = '<div class="row">';
            res.data.forEach(recipe => {
                html += `
                    <div class="col-md-6 mb-3">
                        <div class="recipe-card card" onclick="viewRecipe(${recipe.id})">
                            <img src="${recipe.image || 'https://via.placeholder.com/300x200?text=菜谱图片'}" alt="${recipe.name}">
                            <div class="card-body">
                                <div class="recipe-title">${recipe.name}</div>
                                <div class="recipe-meta">
                                    <span><i class="fas fa-clock"></i> ${recipe.cookingTime}分钟</span>
                                    <span class="badge bg-${DIFFICULTY_CLASS[recipe.difficulty]}">${DIFFICULTY_MAP[recipe.difficulty]}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
            });
            html += '</div>';
            $('#todayRecommend').html(html);
        } else {
            $('#todayRecommend').html('<div class="empty-state"><i class="fas fa-utensils"></i><p>暂无推荐菜谱</p></div>');
        }
    }).fail(function() {
        $('#todayRecommend').html('<div class="empty-state"><i class="fas fa-exclamation-circle"></i><p>加载失败</p></div>');
    });
}

function loadHotRecipes() {
    request(API_ENDPOINTS.RECOMMEND_HOT, {
        method: 'GET'
    }).done(function(res) {
        if (res.code === 200 && res.data && res.data.length > 0) {
            let html = '<div class="row">';
            res.data.slice(0, 6).forEach(recipe => {
                html += `
                    <div class="col-md-4 mb-3">
                        <div class="recipe-card card" onclick="viewRecipe(${recipe.id})">
                            <img src="${recipe.image || 'https://via.placeholder.com/300x200?text=菜谱图片'}" alt="${recipe.name}">
                            <div class="card-body">
                                <div class="recipe-title">${recipe.name}</div>
                                <div class="recipe-meta">
                                    <span><i class="fas fa-eye"></i> ${recipe.viewCount}</span>
                                    <span><i class="fas fa-star"></i> ${recipe.ratingScore}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
            });
            html += '</div>';
            $('#hotRecipes').html(html);
        } else {
            $('#hotRecipes').html('<div class="empty-state"><i class="fas fa-utensils"></i><p>暂无热门菜谱</p></div>');
        }
    }).fail(function() {
        $('#hotRecipes').html('<div class="empty-state"><i class="fas fa-exclamation-circle"></i><p>加载失败</p></div>');
    });
}

function loadCookingStats() {
    request(API_ENDPOINTS.COOKING_STATS, {
        method: 'GET'
    }).done(function(res) {
        if (res.code === 200) {
            $('#totalCookingCount').text(res.data.totalCount || 0);
            $('#weekCookingCount').text(res.data.weekCount || 0);
        }
    });
}

function loadIngredientCount() {
    request(API_ENDPOINTS.USER_INGREDIENT_LIST, {
        method: 'GET'
    }).done(function(res) {
        if (res.code === 200) {
            $('#ingredientCount').text(res.data.length || 0);
        }
    });
}

function loadDailyNutrition() {
    request(API_ENDPOINTS.NUTRITION_DAILY, {
        method: 'GET'
    }).done(function(res) {
        if (res.code === 200) {
            const data = res.data;
            const chart = echarts.init(document.getElementById('nutritionChart'));
            
            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c}g ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [{
                    name: '营养成分',
                    type: 'pie',
                    radius: '70%',
                    data: [
                        { value: parseFloat(data.totalProtein || 0), name: '蛋白质' },
                        { value: parseFloat(data.totalFat || 0), name: '脂肪' },
                        { value: parseFloat(data.totalCarbohydrate || 0), name: '碳水化合物' },
                        { value: parseFloat(data.totalFiber || 0), name: '膳食纤维' }
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            };
            
            chart.setOption(option);
        }
    });
}

function viewRecipe(id) {
    window.location.href = `recipe-detail.html?id=${id}`;
}

