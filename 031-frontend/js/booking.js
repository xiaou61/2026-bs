let currentMatch = null;
let pricingOptions = [];
let currentPricing = null;
let currentSeats = [];
let selectedSeatIds = [];

const urlParams = new URLSearchParams(window.location.search);
const matchId = urlParams.get('id');

async function loadMatchInfo() {
    try {
        const response = await fetch(`/api/matches/${matchId}`);
        const result = await response.json();

        if (result.code === 200) {
            currentMatch = result.data;
            renderMatchInfo(currentMatch);
            await loadPricing();
        } else {
            showBookingError(result.message || '比赛信息加载失败');
        }
    } catch (error) {
        console.error('Failed to load match info:', error);
        showBookingError('比赛信息加载失败');
    }
}

function renderMatchInfo(match) {
    document.getElementById('match-info').innerHTML = `
        <h4>${match.title}</h4>
        <p><strong>比赛时间：</strong>${formatDateTime(match.matchDate)}</p>
        <p><strong>赛事类型：</strong>${match.league} ${match.season ? `· ${match.season}赛季` : ''}</p>
        <p><strong>比赛场馆：</strong>${match.stadiumName || '待定场馆'}</p>
        <p><strong>场馆地址：</strong>${match.stadiumLocation || '暂无'}</p>
        <p><strong>描述：</strong>${match.description || '暂无描述'}</p>
    `;
}

async function loadPricing() {
    try {
        const response = await fetch(`/api/matches/${matchId}/pricing`);
        const result = await response.json();

        if (result.code !== 200) {
            showBookingError(result.message || '票价信息加载失败');
            return;
        }

        pricingOptions = result.data || [];
        renderPricing(pricingOptions);
        if (pricingOptions.length > 0) {
            selectPricing(pricingOptions[0].id);
        }
    } catch (error) {
        console.error('Failed to load pricing:', error);
        showBookingError('票价信息加载失败');
    }
}

function renderPricing(pricingList) {
    const pricingListContainer = document.getElementById('pricing-list');

    if (!pricingList.length) {
        pricingListContainer.innerHTML = '<div class="col-12"><div class="alert alert-warning mb-0">当前比赛暂无可售票价</div></div>';
        return;
    }

    pricingListContainer.innerHTML = pricingList.map(pricing => `
        <div class="col-md-4">
            <div class="card pricing-card ${currentPricing && currentPricing.id === pricing.id ? 'border-primary' : ''}"
                 data-pricing-id="${pricing.id}"
                 onclick="selectPricing(${pricing.id})">
                <div class="card-body text-center">
                    <h5>${pricing.categoryName}</h5>
                    <h3 class="text-primary">¥${pricing.price}</h3>
                    <p class="text-muted mb-1">${pricing.categoryDescription || '暂无描述'}</p>
                    <p class="text-muted mb-0">剩余 ${pricing.availableSeats} 座</p>
                </div>
            </div>
        </div>
    `).join('');
}

async function selectPricing(pricingId) {
    currentPricing = pricingOptions.find(item => item.id === pricingId) || null;
    selectedSeatIds = [];
    updateOrderSummary();
    highlightPricingCard(pricingId);
    await loadSeatGrid();
}

function highlightPricingCard(pricingId) {
    document.querySelectorAll('.pricing-card').forEach(card => {
        card.classList.toggle('border-primary', Number(card.dataset.pricingId) === pricingId);
        card.classList.toggle('shadow', Number(card.dataset.pricingId) === pricingId);
    });
}

async function loadSeatGrid() {
    if (!currentPricing) {
        return;
    }

    try {
        const response = await fetch(`/api/matches/${matchId}/seats?categoryId=${currentPricing.categoryId}`);
        const result = await response.json();

        if (result.code !== 200) {
            showBookingError(result.message || '座位信息加载失败');
            return;
        }

        currentSeats = result.data || [];
        renderSeatGrid();
    } catch (error) {
        console.error('Failed to load seats:', error);
        showBookingError('座位信息加载失败');
    }
}

function renderSeatGrid() {
    const seatGrid = document.getElementById('seat-grid');

    if (!currentSeats.length) {
        seatGrid.innerHTML = '<p class="text-muted">当前分区暂无座位</p>';
        return;
    }

    seatGrid.style.gridTemplateColumns = `repeat(${currentPricing.columnCount || 6}, 1fr)`;
    seatGrid.innerHTML = currentSeats.map(seat => {
        const isSelected = selectedSeatIds.includes(seat.id);
        const isAvailable = seat.status === 'AVAILABLE';
        const statusClass = isSelected ? 'selected' : (isAvailable ? 'available' : 'occupied');
        const title = seat.status === 'SOLD' ? '已售' : (seat.status === 'LOCKED' ? '已锁定' : '可选');
        return `
            <div class="seat ${statusClass}"
                 data-seat-id="${seat.id}"
                 title="${title}"
                 onclick="toggleSeat(${seat.id})">
                ${seat.label}
            </div>
        `;
    }).join('');
}

function toggleSeat(seatId) {
    const seat = currentSeats.find(item => item.id === seatId);
    if (!seat || seat.status !== 'AVAILABLE') {
        return;
    }

    if (selectedSeatIds.includes(seatId)) {
        selectedSeatIds = selectedSeatIds.filter(id => id !== seatId);
    } else {
        selectedSeatIds = [...selectedSeatIds, seatId];
    }

    renderSeatGrid();
    updateOrderSummary();
}

function updateOrderSummary() {
    const selectedSeatsDiv = document.getElementById('selected-seats');
    const totalPriceSpan = document.getElementById('total-price');

    if (!selectedSeatIds.length || !currentPricing) {
        selectedSeatsDiv.innerHTML = '<p class="text-muted">暂未选择座位</p>';
        totalPriceSpan.textContent = '¥0';
        return;
    }

    const selectedSeats = currentSeats.filter(seat => selectedSeatIds.includes(seat.id));
    const totalPrice = selectedSeats.length * Number(currentPricing.price);

    selectedSeatsDiv.innerHTML = `
        <h6>已选座位 (${selectedSeats.length})</h6>
        <ul class="list-unstyled mb-0">
            ${selectedSeats.map(seat => `<li>座位：${seat.label} - ¥${currentPricing.price}</li>`).join('')}
        </ul>
    `;
    totalPriceSpan.textContent = `¥${totalPrice.toFixed(2)}`;
}

async function submitOrder() {
    const user = getUser();
    if (!user) {
        alert('请先登录');
        window.location.href = 'login.html';
        return;
    }

    if (!currentPricing) {
        alert('请先选择票价分区');
        return;
    }

    if (!selectedSeatIds.length) {
        alert('请先选择座位');
        return;
    }

    try {
        const response = await fetch('/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + getToken()
            },
            body: JSON.stringify({
                matchId: Number(matchId),
                pricingId: currentPricing.id,
                seatIds: selectedSeatIds
            })
        });

        const result = await response.json();
        if (result.code === 200) {
            alert('订单创建成功，请尽快完成支付');
            window.location.href = 'my-orders.html';
        } else {
            alert('订单创建失败：' + result.message);
            await loadPricing();
        }
    } catch (error) {
        console.error('Failed to create order:', error);
        alert('订单创建失败');
    }
}

function showBookingError(message) {
    const matchInfo = document.getElementById('match-info');
    matchInfo.innerHTML = `<div class="alert alert-danger mb-0">${message}</div>`;
}

document.addEventListener('DOMContentLoaded', function() {
    if (matchId) {
        loadMatchInfo();
    } else {
        showBookingError('缺少比赛编号');
    }
});
