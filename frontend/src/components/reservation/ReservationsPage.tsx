import React, { useState } from "react";
import { useFetchUserReservations } from "../../hooks/useFetchUserReservations.tsx";
import { Typography, Box, Pagination, Chip, Button } from "@mui/material";
import { Loader } from "../loader/Loader.tsx";
import FoodCardListing from "../main/FoodCardListing.tsx";
import { API_ENDPOINTS } from "../../apiConfig.ts";
import { useProtectedAxios } from "../../hooks/useProtectedAxios.tsx";

export default function ReservationsPage() {
    const [page, setPage] = useState(0);
    const pageSize = 5;
    const { data, error, loading, refetch } = useFetchUserReservations({ page, pageSize });
    const axios = useProtectedAxios();

    const handlePageChange = (_event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);
    };

    const formatDate = (dateString: string) => {
        return new Intl.DateTimeFormat("en-US", {
            year: "numeric",
            month: "long",
            day: "numeric",
        }).format(new Date(dateString));
    };

    const cancelReservation = async (reservationId: string) => {
        try {
            await axios.delete(`${API_ENDPOINTS.CANCEL_RESERVATION}/${reservationId}`);
            alert("Reservation canceled successfully!");
            refetch();  // Trigger refetch to refresh the data after cancellation
        } catch (error) {
            console.error("Error canceling reservation:", error);
            alert("Failed to cancel reservation. Please try again later.");
        }
    };

    return (
        <Box className="p-6 max-w-4xl mx-auto">
            <Typography variant="h4" className="mb-6 text-center font-semibold" marginBottom="12px">
                My Reservations
            </Typography>

            {loading && <Loader />}
            {error && <Typography color="error">Error: {error}</Typography>}

            {data?.content.length === 0 && !loading && (
                <Typography className="text-center text-gray-500">No reservations found.</Typography>
            )}

            <Box className="space-y-6">
                {data?.content.map((reservation) => (
                    <Box key={reservation.id} sx={{ display: "flex", alignItems: "center", gap: 2, p: 2, border: "1px solid #ddd", borderRadius: "8px" }}>
                        <FoodCardListing
                            id={reservation.food.id}
                            title={reservation.food.title}
                            city={reservation.food.city}
                            image={reservation.food.image}
                        />
                        <Box sx={{ display: "flex", flexDirection: "column", gap: 1 }}>
                            <Typography variant="body2" sx={{ fontWeight: "bold", color: "#555" }}>
                                Reservation Date: <span style={{ color: "#3f51b5" }}>{formatDate(reservation.reservationDate)}</span>
                            </Typography>
                            <Typography variant="body2" sx={{ fontWeight: "bold", color: "#555" }}>
                                Expiration Date: <span style={{ color: "#d32f2f" }}>{formatDate(reservation.expirationDate)}</span>
                            </Typography>
                            <Chip
                                label={reservation.isCancelled ? "Cancelled" : "Active"}
                                color={reservation.isCancelled ? "error" : "success"}
                                sx={{ fontWeight: "bold", fontSize: "0.85rem" }}
                            />
                            {!reservation.isCancelled && (
                                <Button
                                    onClick={() => cancelReservation(reservation.id)}
                                    color="error"
                                    variant="contained"
                                    sx={{ mt: 2 }}
                                >
                                    Cancel Reservation
                                </Button>
                            )}
                        </Box>
                    </Box>
                ))}
            </Box>

            {data && data.totalPages > 1 && (
                <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
                    <Pagination
                        count={data.totalPages}
                        page={data.number + 1}
                        onChange={handlePageChange}
                        sx={{
                            "& .MuiPaginationItem-root": {
                                color: "#AEC761",
                                "&.Mui-selected": {
                                    backgroundColor: "#AEC761",
                                    color: "white",
                                },
                            },
                        }}
                    />
                </Box>
            )}
        </Box>
    );
}
