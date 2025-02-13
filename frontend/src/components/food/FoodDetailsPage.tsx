import { useParams } from "react-router-dom";
import { useFetchData } from "../../hooks/useFetchData.tsx";
import { API_ENDPOINTS } from "../../apiConfig.ts";
import { Loader } from "../loader/Loader.tsx";
import React from "react";

const FoodDetailsPage = () => {
    const { id } = useParams();
    const { data: food, error, loading } = useFetchData(`${API_ENDPOINTS.GET_FOOD}/${id}`);

    if (loading) {
        return (
            <div className="flex justify-center mt-4">
                <Loader />
            </div>
        );
    }

    if (error) {
        return (
            <div className="text-left mt-4 text-red-500">
                <p>Error: {error}</p>
            </div>
        );
    }

    const formattedExpiryDate = food.expiryDate
        ? new Intl.DateTimeFormat("en-US", { year: "numeric", month: "long", day: "numeric" }).format(new Date(food.expiryDate))
        : "N/A";

    return (
        <div className="mt-4 px-6 max-w-2xl">
            <h1 className="text-2xl font-bold mb-2">{food.title}</h1>
            <img
                src={`data:image/jpeg;base64,${food.image}`}
                alt={food.title}
                className="w-64 h-64 object-cover rounded-lg mb-4"
            />
            <p className="text-lg mb-2">{food.description}</p>
            <p className="text-gray-600">Category: {food.category}</p>
            <p className="text-gray-600">City: {food.city}</p>
            <p className="text-gray-600">Available: {food.available ? "Yes" : "No"}</p>
            <p className="text-gray-600">Expiry Date: {formattedExpiryDate}</p>
        </div>
    );
};

export default FoodDetailsPage;
