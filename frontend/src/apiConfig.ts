export const API_BASE_URL = 'http://localhost:8080/api/v1';

export const API_ENDPOINTS = {
    LOGIN: `${API_BASE_URL}/auth/login`,
    REGISTER: `${API_BASE_URL}/auth/register`,
    CATEGORIES: `${API_BASE_URL}/food/categories`,
    PROFILE: `${API_BASE_URL}/users/me`,
    CHANGE_PASSWORD: `${API_BASE_URL}/users/change-password`,
    USER_FOODS: `${API_BASE_URL}/food/all/me`,
}