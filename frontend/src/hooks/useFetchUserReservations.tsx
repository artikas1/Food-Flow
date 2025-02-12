import { useState, useEffect } from "react";
import { useProtectedAxios } from "./useProtectedAxios.tsx";
import {API_ENDPOINTS} from "../apiConfig.ts";

interface FoodResponseDto {
    id: string;
    title: string;
    city: string;
    image: string;
}

interface FoodReservationResponseDto {
    id: string;
    food: FoodResponseDto;
    userId: string;
    reservationDate: string;
    expirationDate: string;
    isCancelled: boolean;
}

interface PaginatedResponse {
    content: FoodReservationResponseDto[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

interface FetchReservationsParams {
    page: number;
    pageSize: number;
}

export const useFetchUserReservations = ({ page, pageSize }: FetchReservationsParams) => {
    const [data, setData] = useState<PaginatedResponse | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const axios = useProtectedAxios();

    useEffect(() => {
        const fetchReservations = async () => {
            try {
                setLoading(true);
                const response = await axios.get<PaginatedResponse>(API_ENDPOINTS.USER_RESERVATIONS, {
                    params: {
                        page,
                        pageSize,
                    },
                });
                setData(response.data);
            } catch (err) {
                setError(err instanceof Error ? err.message : "An error occurred");
            } finally {
                setLoading(false);
            }
        };

        fetchReservations();
    }, [axios, page, pageSize]);

    return { data, error, loading };
};
