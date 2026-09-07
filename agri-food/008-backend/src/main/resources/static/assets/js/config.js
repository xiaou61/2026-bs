const API_BASE_URL = 'http://localhost:8008/api';

const API_ENDPOINTS = {
    USER_REGISTER: '/user/register',
    USER_LOGIN: '/user/login',
    USER_INFO: '/user/info',
    
    INGREDIENT_LIST: '/ingredient/list',
    USER_INGREDIENT_LIST: '/user-ingredient/list',
    USER_INGREDIENT_ADD: '/user-ingredient/add',
    USER_INGREDIENT_UPDATE: '/user-ingredient',
    USER_INGREDIENT_DELETE: '/user-ingredient',
    
    RECIPE_LIST: '/recipe/list',
    RECIPE_DETAIL: '/recipe',
    RECIPE_SEARCH: '/recipe/search',
    RECIPE_COLLECT: '/recipe/collect',
    RECIPE_COLLECT_LIST: '/recipe/collect/list',
    
    RECOMMEND_TODAY: '/recommend/today',
    RECOMMEND_BY_INGREDIENT: '/recommend/by-ingredient',
    RECOMMEND_HOT: '/recommend/hot',
    RECOMMEND_EASY: '/recommend/easy',
    
    NUTRITION_RECIPE: '/nutrition/recipe',
    NUTRITION_DAILY: '/nutrition/daily',
    
    SHOPPING_GENERATE: '/shopping/generate',
    SHOPPING_LIST: '/shopping/list',
    SHOPPING_CHECK: '/shopping',
    SHOPPING_ADD_TO_STOCK: '/shopping/add-to-stock',
    SHOPPING_CLEAR: '/shopping/clear',
    
    COOKING_RECORD: '/cooking/record',
    COOKING_RECORD_LIST: '/cooking/record/list',
    COOKING_STATS: '/cooking/stats',
    
    ADMIN_RECIPE_LIST: '/admin/recipe/list',
    ADMIN_RECIPE: '/admin/recipe',
    ADMIN_INGREDIENT_LIST: '/admin/ingredient/list',
    ADMIN_INGREDIENT: '/admin/ingredient',
    ADMIN_USER_LIST: '/admin/user/list',
    ADMIN_USER_STATUS: '/admin/user',
    ADMIN_STATS: '/admin/stats'
};

const DIFFICULTY_MAP = {
    1: '简单',
    2: '中等',
    3: '困难'
};

const DIFFICULTY_CLASS = {
    1: 'success',
    2: 'warning',
    3: 'danger'
};

const CATEGORY_MAP = {
    '蔬菜类': 'success',
    '肉类': 'danger',
    '禽蛋类': 'warning',
    '水产类': 'info',
    '谷物类': 'secondary',
    '豆制品': 'primary',
    '调料类': 'dark'
};

