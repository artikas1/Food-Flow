export const API_BASE_URL = 'http://localhost:8080/api/v1';

export const API_ENDPOINTS = {
    LOGIN: `${API_BASE_URL}/auth/login`,
    REGISTER: `${API_BASE_URL}/auth/register`,
    CATEGORIES: `${API_BASE_URL}/food/categories`,
    DETAILS: `${API_BASE_URL}/food/details`,
    ALL: `${API_BASE_URL}/search`,
    CREATE_FOOD: `${API_BASE_URL}/food`,
    USER_RESERVATIONS: `${API_BASE_URL}/reservations/user`,
    GET_FOOD: `${API_BASE_URL}/food`,
    RESERVE_FOOD: `${API_BASE_URL}/reservations`,
    CANCEL_RESERVATION: `${API_BASE_URL}/reservations`,
    CHECK_RESERVATION: `${API_BASE_URL}/reservations/check`,
    CANCEL_RESERVATION_BY_FOOD_ID: `${API_BASE_URL}/reservations/delete`,

}