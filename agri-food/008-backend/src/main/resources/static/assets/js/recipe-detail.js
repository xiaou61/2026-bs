let recipeId = null;
let recipeData = null;

$(document).ready(function() {
    if (!checkAuth()) return;

    const user = getUser();
    $('#userName').text(user.nickname || user.username);

    const urlParams = new URLSearchParams(window.location.search);
    recipeId = urlParams.get('id');

    if (!recipeId) {
        layer.msg('菜谱不存在');
        setTimeout(() => {
            window.location.href = 'recipes.html';
        }, 1500);
        return;
    }

    loadRecipeDetail();
    loadNutrition();
});

function loadRecipeDetail() {
    request(`${API_ENDPOINTS.RECIPE_DETAIL}/${recipeId}`, {
        method: 'GET'
    }).done(function(res) {
        if (res.code === 200) {
            recipeData = res.data;
            renderRecipeDetail(res.data);
            updateCollectButton(res.data.isCollected);
        }
    }).fail(function() {
        $('#recipeDetail').html('<div class="empty-state"><i class="fas fa-exclamation-circle"></i><p>加载失败</p></div>');
    });
}

function renderRecipeDetail(recipe) {
    let html = `
        <div class="text-center mb-4">
            <img src="${recipe.image || 'https://via.placeholder.com/600x400?text=菜谱图片'}" 
                 alt="${recipe.name}" class="img-fluid rounded" style="max-height: 400px;">
        </div>
        <h2>${recipe.name}</h2>
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div>
                <span class="badge bg-${DIFFICULTY_CLASS[recipe.difficulty]} me-2">${DIFFICULTY_MAP[recipe.difficulty]}</span>
                <span class="badge bg-secondary me-2">${recipe.cuisineType}</span>
                <span class="badge bg-info">${recipe.servingSize}人份</span>
            </div>
            <div class="text-muted">
                <i class="fas fa-clock"></i> ${recipe.cookingTime}分钟
                <i class="fas fa-eye ms-2"></i> ${recipe.viewCount}
                <i class="fas fa-heart ms-2"></i> ${recipe.collectCount}
            </div>
        </div>
        <p class="text-muted">${recipe.description || '暂无描述'}</p>
        <hr>
        <h4><i class="fas fa-carrot"></i> 所需食材</h4>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>食材名称</th>
                        <th>用量</th>
                        <th>必需</th>
                    </tr>
                </thead>
                <tbody>
    `;

    recipe.ingredients.forEach(ing => {
        html += `
            <tr>
                <td>${ing.ingredientName}</td>
                <td>${ing.quantity}${ing.unit}</td>
                <td>${ing.isRequired === 1 ? '<span class="badge bg-danger">必需</span>' : '<span class="badge bg-secondary">可选</span>'}</td>
            </tr>
        `;
    });

    html += `
                </tbody>
            </table>
        </div>
        <hr>
        <h4><i class="fas fa-list-ol"></i> 制作步骤</h4>
    `;

    recipe.steps.forEach(step => {
        html += `
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">步骤 ${step.stepNumber}</h5>
                    <p class="card-text">${step.description}</p>
                    ${step.duration ? `<small class="text-muted"><i class="fas fa-clock"></i> 约${step.duration}分钟</small>` : ''}
                </div>
            </div>
        `;
    });

    $('#recipeDetail').html(html);
}

function updateCollectButton(isCollected) {
    if (isCollected) {
        $('#collectBtn').html('<i class="fas fa-heart"></i> 已收藏').addClass('active');
    } else {
        $('#collectBtn').html('<i class="far fa-heart"></i> 收藏菜谱').removeClass('active');
    }
}

function toggleCollect() {
    const isCollected = $('#collectBtn').hasClass('active');
    
    if (isCollected) {
        request(`${API_ENDPOINTS.RECIPE_COLLECT}/${recipeId}`, {
            method: 'DELETE'
        }).done(function(res) {
            if (res.code === 200) {
                layer.msg('已取消收藏');
                updateCollectButton(false);
            }
        });
    } else {
        request(`${API_ENDPOINTS.RECIPE_COLLECT}/${recipeId}`, {
            method: 'POST'
        }).done(function(res) {
            if (res.code === 200) {
                layer.msg('收藏成功');
                updateCollectButton(true);
            }
        });
    }
}

function addToShopping() {
    request(API_ENDPOINTS.SHOPPING_GENERATE, {
        method: 'POST',
        data: JSON.stringify({ recipeIds: [parseInt(recipeId)] })
    }).done(function(res) {
        if (res.code === 200) {
            layer.confirm('已加入购物清单，是否前往查看？', {
                btn: ['查看', '取消']
            }, function(index) {
                window.location.href = 'shopping.html';
                layer.close(index);
            });
        }
    });
}

function showCookingForm() {
    layer.open({
        type: 1,
        title: '做菜打卡',
        area: ['500px', '400px'],
        content: `
            <div class="p-3">
                <form id="cookingForm">
                    <div class="mb-3">
                        <label class="form-label">味道评分</label>
                        <select class="form-select" name="tasteRating">
                            <option value="5">5分 - 非常好吃</option>
                            <option value="4">4分 - 好吃</option>
                            <option value="3">3分 - 一般</option>
                            <option value="2">2分 - 不太好吃</option>
                            <option value="1">1分 - 难吃</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">难度评分</label>
                        <select class="form-select" name="difficultyRating">
                            <option value="1">1分 - 很简单</option>
                            <option value="2">2分 - 简单</option>
                            <option value="3" selected>3分 - 中等</option>
                            <option value="4">4分 - 有点难</option>
                            <option value="5">5分 - 很难</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">时间评分</label>
                        <select class="form-select" name="timeRating">
                            <option value="1">1分 - 超时很多</option>
                            <option value="2">2分 - 超时一些</option>
                            <option value="3" selected>3分 - 刚好</option>
                            <option value="4">4分 - 快一些</option>
                            <option value="5">5分 - 很快</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">心得体会</label>
                        <textarea class="form-control" name="note" rows="3"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">提交打卡</button>
                </form>
            </div>
        `
    });

    $(document).off('submit', '#cookingForm').on('submit', '#cookingForm', function(e) {
        e.preventDefault();
        
        const formData = {
            recipeId: parseInt(recipeId),
            tasteRating: parseInt($('select[name="tasteRating"]', this).val()),
            difficultyRating: parseInt($('select[name="difficultyRating"]', this).val()),
            timeRating: parseInt($('select[name="timeRating"]', this).val()),
            note: $('textarea[name="note"]', this).val()
        };

        request(API_ENDPOINTS.COOKING_RECORD, {
            method: 'POST',
            data: JSON.stringify(formData)
        }).done(function(res) {
            if (res.code === 200) {
                layer.closeAll();
                layer.msg('打卡成功！');
            }
        });
    });
}

function loadNutrition() {
    request(`${API_ENDPOINTS.NUTRITION_RECIPE}/${recipeId}`, {
        method: 'GET'
    }).done(function(res) {
        if (res.code === 200) {
            renderNutrition(res.data);
        }
    });
}

function renderNutrition(data) {
    const chart = echarts.init(document.getElementById('nutritionChart'));
    
    const option = {
        tooltip: {
            trigger: 'item',
            formatter: '{b}: {c}g ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            top: 'center'
        },
        series: [{
            name: '营养成分',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            data: [
                { value: parseFloat(data.totalProtein || 0), name: '蛋白质' },
                { value: parseFloat(data.totalFat || 0), name: '脂肪' },
                { value: parseFloat(data.totalCarbohydrate || 0), name: '碳水' },
                { value: parseFloat(data.totalFiber || 0), name: '纤维' }
            ]
        }]
    };
    
    chart.setOption(option);

    let detailHtml = `
        <div class="list-group">
            <div class="list-group-item d-flex justify-content-between">
                <span>总热量：</span>
                <strong>${data.totalCalories} kcal</strong>
            </div>
            <div class="list-group-item d-flex justify-content-between">
                <span>蛋白质：</span>
                <strong>${data.totalProtein} g</strong>
            </div>
            <div class="list-group-item d-flex justify-content-between">
                <span>脂肪：</span>
                <strong>${data.totalFat} g</strong>
            </div>
            <div class="list-group-item d-flex justify-content-between">
                <span>碳水化合物：</span>
                <strong>${data.totalCarbohydrate} g</strong>
            </div>
            <div class="list-group-item d-flex justify-content-between">
                <span>膳食纤维：</span>
                <strong>${data.totalFiber} g</strong>
            </div>
        </div>
    `;
    $('#nutritionDetail').html(detailHtml);
}

