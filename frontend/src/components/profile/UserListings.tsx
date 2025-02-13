import React, { useEffect, useState } from 'react';
import { Box, Typography, CircularProgress, Pagination, Stack, Card, CardMedia, CardContent, Button, Chip, Paper } from '@mui/material';
import axios from 'axios';
import { API_ENDPOINTS } from '../../apiConfig.ts';
import { useAuth } from '../../contexts/AuthContext.tsx';
import { format } from 'date-fns';
import CreateListingForm from '../create-listing/CreateListingForm.tsx';

interface FoodItem {
    id: string;
    title: string;
    description: string;
    category: string;
    city: string;
    expiryDate: string;
    foodDetails: string;
    image: string;
}

interface Props {
    userId?: string;
    isCurrentUser: boolean;
}

const UserListings: React.FC<Props> = ({ userId, isCurrentUser }) => {
    const [listings, setListings] = useState<FoodItem[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [editingItem, setEditingItem] = useState<FoodItem | null>(null);
    const { getAccessToken } = useAuth();

    const fetchListings = async (page: number) => {
        setLoading(true);
        setError('');
        try {
            const token = getAccessToken();
            const endpoint = userId
                ? API_ENDPOINTS.USER_FOODS_BY_ID(userId)
                : API_ENDPOINTS.USER_FOODS;

            const response = await axios.get(endpoint, {
                params: { page: page - 1, pageSize: 10 },
                headers: { Authorization: `Bearer ${token}` },
            });
            setListings(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (err) {
            setError('Failed to fetch listings');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (foodId: string) => {
        try {
            const token = getAccessToken();
            await axios.delete(API_ENDPOINTS.DELETE_FOOD(foodId), {
                headers: { Authorization: `Bearer ${token}` },
                withCredentials: true
            });
            await fetchListings(page);
        } catch (err) {
            setError('Failed to delete listing');
        }
    };

    useEffect(() => {
        fetchListings(page);
    }, [page]);

    const handlePageChange = (_event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);
    };

    if (loading) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
                <CircularProgress />
            </Box>
        );
    }

    if (error) {
        return (
            <Typography variant="h6" color="error" sx={{ textAlign: 'center', mt: 4 }}>
                {error}
            </Typography>
        );
    }

    return (
        <Box sx={{ mt: 4, px: 3 }}>
            <Typography variant="h5" sx={{ mb: 2, color: '#AEC761', fontWeight: 600 }}>
                {isCurrentUser ? 'My Listings' : 'Listings'}
            </Typography>

            {editingItem ? (
                <CreateListingForm
                    existingData={editingItem}
                    isEditMode={true}
                    onCancel={() => setEditingItem(null)}
                    onSuccess={() => {
                        setEditingItem(null);
                        fetchListings(page);
                    }}
                />
            ) : listings.length === 0 ? (
                <Paper variant="outlined" sx={{ p: 4, textAlign: 'center' }}>
                    <Typography variant="body1" color="text.secondary">
                        No active listings found
                    </Typography>
                </Paper>
            ) : (
                <Stack spacing={3}>
                    {listings.map((item) => {
                        const imageSrc = item.image
                            ? `data:image/jpeg;base64,${item.image}`
                            : 'https://via.placeholder.com/160';

                        return (
                            <Card
                                key={item.id}
                                variant="outlined"
                                sx={{
                                    display: 'flex',
                                    borderRadius: 3,
                                    '&:hover': {
                                        boxShadow: 3,
                                        borderColor: '#AEC761'
                                    }
                                }}
                            >
                                <CardMedia
                                    component="img"
                                    sx={{
                                        width: 180,
                                        height: 180,
                                        objectFit: 'cover',
                                        display: { xs: 'none', sm: 'block' }
                                    }}
                                    image={imageSrc}
                                    alt="Listing image"
                                />
                                <CardContent sx={{ flex: 1 }}>
                                    <Typography variant="body1" sx={{ mb: 1.5, fontWeight: 500, fontSize: '1.1rem' }}>
                                        {item.description}
                                    </Typography>

                                    <Box sx={{ display: 'flex', gap: 1, mb: 2 }}>
                                        <Chip
                                            label={item.category.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, c => c.toUpperCase())}
                                            size="small"
                                            sx={{
                                                borderColor: '#AEC761',
                                                color: '#AEC761',
                                                backgroundColor: 'transparent',
                                                ml: -1
                                            }}
                                        />
                                        <Chip
                                            label={item.city}
                                            size="small"
                                            variant="outlined"
                                        />
                                    </Box>

                                    <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                                        {item.foodDetails.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, c => c.toUpperCase())}
                                    </Typography>

                                    <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', mt: 2 }}>
                                        <Typography variant="caption" color="text.secondary">
                                            Expires: {format(new Date(item.expiryDate), 'MMM dd, yyyy')}
                                        </Typography>
                                        {isCurrentUser && (
                                            <Box sx={{ display: 'flex', gap: 1 }}>
                                                <Button
                                                    size="small"
                                                    variant="contained"
                                                    sx={{
                                                        backgroundColor: '#AEC761',
                                                        '&:hover': { backgroundColor: '#94A857' },
                                                        borderRadius: 2,
                                                        textTransform: 'none',
                                                        px: 2,
                                                        py: 0.5,
                                                    }}
                                                    onClick={() => setEditingItem(item)}
                                                >
                                                    Update
                                                </Button>
                                                <Button
                                                    size="small"
                                                    variant="contained"
                                                    sx={{
                                                        backgroundColor: '#ff4444',
                                                        '&:hover': { backgroundColor: '#cc0000' },
                                                        borderRadius: 2,
                                                        textTransform: 'none',
                                                        px: 2,
                                                        py: 0.5,
                                                    }}
                                                    onClick={() => handleDelete(item.id)}
                                                >
                                                    Delete
                                                </Button>
                                            </Box>
                                        )}
                                    </Box>
                                </CardContent>
                            </Card>
                        );
                    })}
                </Stack>
            )}

            {totalPages > 1 && (
                <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
                    <Pagination
                        count={totalPages}
                        page={page}
                        onChange={handlePageChange}
                        sx={{
                            '& .MuiPaginationItem-root': {
                                color: '#AEC761',
                                '&.Mui-selected': {
                                    backgroundColor: '#AEC761',
                                    color: 'white'
                                }
                            }
                        }}
                    />
                </Box>
            )}
        </Box>
    );
};

export default UserListings;