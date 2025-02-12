export const API_BASE_URL = 'http://localhost:8080/api/v1';

export const API_ENDPOINTS = {
    LOGIN: `${API_BASE_URL}/auth/login`,
    REGISTER: `${API_BASE_URL}/auth/register`,
    CATEGORIES: `${API_BASE_URL}/food/categories`,
    DETAILS: `${API_BASE_URL}/food/details`,
    ALL: `${API_BASE_URL}/search`,
    CREATE_FOOD: `${API_BASE_URL}/food`,

}