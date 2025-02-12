import { useState, useEffect } from 'react';
import { useProtectedAxios } from './useProtectedAxios.tsx';

export const useFetchFoodDetails = (endpoint: string) => {
    const [data, setData] = useState<string[]>([]); // Change to string array since food details are strings
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const axios = useProtectedAxios();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get<string[]>(endpoint); // Expected data type is string array
                setData(response.data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An error occurred');
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [axios, endpoint]);

    return { data, error, loading };
};
