import { useState, useEffect } from "react";
import {API_ENDPOINTS} from "../apiConfig.ts";
import {useProtectedAxios} from "./useProtectedAxios.tsx";

interface FoodResponseDto {
    id: string;
    title: string;
    city: string;
    image: string;
}

interface PaginatedFoodResponse {
    foods: FoodResponseDto[];
    currentPage: number;
    totalPages: number;
    totalItems: number;
}

export const useFoodSearch = () => {
    const [data, setData] = useState<PaginatedFoodResponse | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [page, setPage] = useState<number>(1);
    const [pageSize, setPageSize] = useState<number>(10);
    const [search, setSearch] = useState<string>("");
    const [categories, setCategories] = useState<string[]>([]);
    const axios = useProtectedAxios();

    useEffect(() => {
        const fetchFoods = async () => {
            setLoading(true);
            setError(null);

            try {
                const response = await axios.get<PaginatedFoodResponse>(API_ENDPOINTS.ALL, {
                    params: {
                        limit: pageSize,
                        offset: (page - 1) * pageSize,
                        search: search || undefined,
                        categories: categories.length > 0 ? categories.join(",") : undefined,
                    },
                });

                setData(response.data);
            } catch (err) {
                setError("Failed to fetch food items.");
            } finally {
                setLoading(false);
            }
        };

        fetchFoods();
    }, [page, pageSize, search, categories]);

    return { data, loading, error, setPage, setPageSize, setSearch, setCategories };
};
