import React, { useEffect, useState } from 'react';
import { Paper, Typography, Box, TextField, Button, Rating, CircularProgress } from '@mui/material';
import axios from 'axios';
import { API_ENDPOINTS } from '../../apiConfig.ts';
import { useAuth } from '../../contexts/AuthContext.tsx';

interface Review {
    review: {
        content: string;
        rating: number;
    };
    user: {
        id: string;
        firstName: string;
        lastName: string;
    };
}

const ReviewsSection: React.FC<{ userId: string; targetId: string; isCurrentUser: boolean }> = ({ userId, targetId, isCurrentUser }) => {
    const [reviews, setReviews] = useState<Review[]>([]);
    const [newReview, setNewReview] = useState({ content: '', rating: 3 });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const { getAccessToken } = useAuth();

    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const response = await axios.post(API_ENDPOINTS.REVIEWS_GET, null, {
                    params: { userId },
                });
                setReviews(response.data);
            } catch (err) {
                setError('Failed to load reviews');
            } finally {
                setLoading(false);
            }
        };
        fetchReviews();
    }, [userId, getAccessToken]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const token = getAccessToken();
            await axios.post(API_ENDPOINTS.REVIEWS_CREATE, {
                ...newReview,
                target: targetId
            }, {
                headers: { Authorization: `Bearer ${token}` },
            });
            const response = await axios.post(API_ENDPOINTS.REVIEWS_GET, null, {
                params: { userId },
                headers: { Authorization: `Bearer ${token}` },
            });
            setReviews(response.data);
            setNewReview({ content: '', rating: 3 });
        } catch (err) {
            setError('Failed to submit review');
        }
    };

    if (loading) return <CircularProgress />;

    return (
        <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
            <Paper elevation={3} sx={{ p: 4, borderRadius: 4, width: '100%', maxWidth: 600 }}>
                <Typography variant="h6" gutterBottom align="center">Reviews</Typography>

                {!isCurrentUser && (
                    <form onSubmit={handleSubmit}>
                        <TextField
                            label="Review Content"
                            fullWidth
                            margin="normal"
                            value={newReview.content}
                            onChange={(e) => setNewReview({ ...newReview, content: e.target.value })}
                        />
                        <Box my={2} sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                            <Typography component="legend">Rating</Typography>
                            <Rating
                                value={newReview.rating}
                                onChange={(_, value) => setNewReview({ ...newReview, rating: value || 0 })}
                            />
                        </Box>
                        <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                            <Button type="submit" variant="contained" sx={{ mt: 2 }}>
                                Submit Review
                            </Button>
                        </Box>
                    </form>
                )}

                <Box mt={4}>
                    {reviews.map((review, index) => (
                        <Paper key={index} sx={{ p: 2, my: 2 }}>
                            <Typography>{review.review.content}</Typography>
                            <Rating value={review.review.rating} readOnly />
                            <Typography variant="caption">
                                By {review.user.firstName} {review.user.lastName}
                            </Typography>
                        </Paper>
                    ))}
                </Box>
            </Paper>
        </Box>
    );
};

export default ReviewsSection;