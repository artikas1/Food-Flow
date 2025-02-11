import { useState, useEffect } from 'react';
import { useProtectedAxios } from './useProtectedAxios.tsx';
import { FoodDto } from '../dtos/FoodDto.tsx';
import {API_ENDPOINTS} from "../apiConfig.ts";

interface PaginatedResponse<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

const useFetchAllFoods = (page: number, pageSize: number) => {
    const [data, setData] = useState<PaginatedResponse<FoodDto> | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const axios = useProtectedAxios();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(API_ENDPOINTS.ALL, {
                    params: { page, pageSize }
                });
                setData(response.data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An error occurred');
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [axios, page, pageSize]);

    return { data, error, loading };
};

export default useFetchAllFoods;