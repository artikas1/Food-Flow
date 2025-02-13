import { useState, useEffect } from "react";
import { useProtectedAxios } from "./useProtectedAxios.tsx";
import { API_ENDPOINTS } from "../apiConfig.ts";

interface Food {
    id: string;
    userId: string;
}

interface Ratings {
    [key: string]: string;
}

export const useFetchAverageRating = (foods: Food[] | undefined) => {
    const [ratings, setRatings] = useState<Ratings>({});
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const axios = useProtectedAxios();

    useEffect(() => {
        if (foods && foods.length > 0) {
            const fetchRatings = async () => {
                setLoading(true);
                setError(null);

                const ratingsMap: Ratings = {};

                try {
                    for (const food of foods) {
                        try {
                            const response = await axios.get(`${API_ENDPOINTS.REVIEW}?userId=${food.userId}`);
                            ratingsMap[food.id] = `${response.data.toFixed(1)}/5`; // Format the rating
                        } catch (err) {
                            ratingsMap[food.id] = "N/A"; // Handle errors
                        }
                    }

                    setRatings(ratingsMap);
                } catch (err) {
                    setError("Failed to fetch ratings");
                } finally {
                    setLoading(false);
                }
            };

            fetchRatings();
        }
    }, [foods, axios]);

    return { ratings, loading, error };
};