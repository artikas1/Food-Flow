import { useState, useEffect } from 'react';
import { useProtectedAxios } from './useProtectedAxios.tsx';
import { API_ENDPOINTS } from '../apiConfig.ts';

export const useFetchAverageRating = (userId: string) => {
    const [rating, setRating] = useState<number | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const axios = useProtectedAxios();

    useEffect(() => {
        const fetchAverageRating = async () => {
            try {
                const response = await axios.get(`${API_ENDPOINTS.REVIEW}?userId=${userId}`);
                setRating(response.data); // Assuming the response is a number
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An error occurred');
            } finally {
                setLoading(false);
            }
        };

        fetchAverageRating();
    }, [axios, userId]);

    return { rating, loading, error };
};