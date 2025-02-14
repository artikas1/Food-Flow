import {useNavigate, useParams} from "react-router-dom";
import { useFetchData } from "../../hooks/useFetchData.tsx";
import { usePostData } from "../../hooks/usePostData.tsx";
import { API_ENDPOINTS } from "../../apiConfig.ts";
import { Loader } from "../loader/Loader.tsx";
import React, { useState, useEffect } from "react";
import Avatar from '@mui/material/Avatar';
import axios from "axios";
import {useProtectedAxios} from "../../hooks/useProtectedAxios.tsx";

const FoodDetailsPage = () => {
    const { id } = useParams();
    const { data: food, error, loading } = useFetchData(`${API_ENDPOINTS.GET_FOOD}/${id}`);
    const { postData, loading: reserving, error: reserveError } = usePostData(`${API_ENDPOINTS.RESERVE_FOOD}/${id}`);
    const [isReservedByMe, setIsReservedByMe] = useState(false);
    const axios = useProtectedAxios();
    const navigate = useNavigate();  // Initialize useNavigate

    useEffect(() => {
        const checkReservation = async () => {
            try {
                const response = await axios.get(`${API_ENDPOINTS.CHECK_RESERVATION}`, {
                    params: { foodId: id }
                });
                setIsReservedByMe(response.data.isReservedByMe);
            } catch (err) {
                console.error("Failed to check reservation status.");
            }
        };

        checkReservation();
    }, [id]);

    const handleReserve = async () => {
        const success = await postData();
        if (success) {
            alert("Food reserved successfully!");
            setIsReservedByMe(true);
        }
    };

    const handleCancelReservation = async () => {
        try {
            await axios.delete(`${API_ENDPOINTS.CANCEL_RESERVATION_BY_FOOD_ID}`, {
                params: { foodId: id }
            });
            alert("Reservation cancelled successfully!");
            setIsReservedByMe(false);
        } catch (err) {
            alert("Failed to cancel reservation.");
        }
    };

    const handleAvatarClick = () => {
        if (food.userId) {
            navigate(`/profile/${food.userId}`);
        }
    };


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
        <div  className="flex justify-center">
            <div className="mt-4 px-6 max-w-md border border-gray-300 rounded-lg shadow-md p-4">
                <h1 className="text-2xl font-bold mb-2">{food.title}</h1>
                <img
                    src={`data:image/jpeg;base64,${food.image}`}
                    alt={food.title}
                    className="w-64 h-64 object-cover rounded-lg mb-4"
                />
                <div style={{paddingBottom:"4px", cursor: "pointer", display: "flex", alignItems: "center"}} onClick={handleAvatarClick}>
                    <Avatar>U</Avatar>
                    <p style={{marginLeft: "6px"}}>User</p>
                </div>
                <p className="text-lg mb-2">{food.description}</p>
                <p className="text-gray-600">Category: {food.category}</p>
                <p className="text-gray-600">City: {food.city}</p>
                <p className="text-gray-600">Available: {food.available ? "Yes" : "No"}</p>
                <p className="text-gray-600">Expiry Date: {formattedExpiryDate}</p>

                {reserveError && <p className="text-red-500 mt-2">{reserveError}</p>}

                {food.available && !isReservedByMe ? (
                    <button
                        onClick={handleReserve}
                        disabled={reserving}
                        className="mt-4 bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 disabled:bg-gray-400"
                    >
                        {reserving ? "Reserving..." : "Reserve"}
                    </button>
                ) : (
                    <button
                        onClick={handleCancelReservation}
                        className="mt-4 bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600"
                    >
                        Cancel Reservation
                    </button>
                )}


            </div>
        </div>
    );
};

export default FoodDetailsPage;