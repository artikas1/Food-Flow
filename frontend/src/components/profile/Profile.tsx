import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Box, Typography, Avatar, Button, CircularProgress } from '@mui/material';
import { API_ENDPOINTS } from '../../apiConfig.ts';
import axios from 'axios';
import { useAuth } from "../../contexts/AuthContext.tsx";

interface UserProfile {
    id: string;
    name: string;
    email: string;
    avatarUrl?: string;
}

const Profile: React.FC = () => {
    const [user, setUser] = useState<UserProfile | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const { getAccessToken } = useAuth(); // Call useAuth at the top level

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const token = getAccessToken(); // Use the token from AuthContext
                if (!token) {
                    throw new Error('No access token found');
                }

                const response = await axios.get(API_ENDPOINTS.PROFILE, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                    withCredentials: true, // Include credentials if needed
                });

                setUser(response.data);
            } catch (err) {
                setError('Failed to load profile');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchProfile();
    }, [getAccessToken]); // Add getAccessToken as a dependency

    const handleChangePassword = () => {
        navigate('/change-password');
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
        <Container maxWidth="md" sx={{ mt: 4 }}>
            <Box sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                bgcolor: 'background.paper',
                p: 4,
                borderRadius: 2,
                boxShadow: 3
            }}>
                <Avatar
                    sx={{
                        width: 100,
                        height: 100,
                        mb: 2,
                        bgcolor: '#AEC761',
                        fontSize: '2.5rem'
                    }}
                    src={user?.avatarUrl}
                >
                    {user?.name?.[0]}
                </Avatar>

                <Typography variant="h4" component="h1" sx={{ mb: 2, color: '#AEC761' }}>
                    {user?.name}
                </Typography>

                <Box sx={{ width: '100%', maxWidth: 400 }}>
                    <Box sx={{ mb: 3 }}>
                        <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
                            Email:
                        </Typography>
                        <Typography variant="body1">{user?.email}</Typography>
                    </Box>

                    <Button
                        fullWidth
                        variant="contained"
                        sx={{
                            backgroundColor: '#AEC761',
                            '&:hover': { backgroundColor: '#94A857' },
                            mb: 2
                        }}
                        onClick={() => {/* Implement edit profile */}}
                    >
                        Edit Profile
                    </Button>

                    <Button
                        fullWidth
                        variant="outlined"
                        sx={{
                            color: '#AEC761',
                            borderColor: '#AEC761',
                            '&:hover': { borderColor: '#94A857' }
                        }}
                        onClick={handleChangePassword}
                    >
                        Change Password
                    </Button>
                </Box>
            </Box>
        </Container>
    );
};

export default Profile;