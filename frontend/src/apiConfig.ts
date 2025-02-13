export const API_BASE_URL = 'http://localhost:8080/api/v1';

export const API_ENDPOINTS = {
    LOGIN: `${API_BASE_URL}/auth/login`,
    REGISTER: `${API_BASE_URL}/auth/register`,
    CATEGORIES: `${API_BASE_URL}/food/categories`,
    PROFILE: `${API_BASE_URL}/users/me`,
    CHANGE_PASSWORD: `${API_BASE_URL}/users/change-password`,
    USER_FOODS: `${API_BASE_URL}/food/all/me`,
    USER_FOODS_BY_ID: (userId: string) => `${API_BASE_URL}/food/all/${userId}`,
    REVIEWS_CREATE: `${API_BASE_URL}/reviews/create`,
    REVIEWS_GET: `${API_BASE_URL}/reviews/get`,
    USER_BY_ID: (userId: string) => `${API_BASE_URL}/users/${userId}`,
    DELETE_FOOD: (userId: string)=> `${API_BASE_URL}/food/${userId}`,
};