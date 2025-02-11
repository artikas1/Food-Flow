import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Box, Typography, Avatar, Button, CircularProgress, Paper, IconButton } from '@mui/material';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import { API_ENDPOINTS } from '../../apiConfig.ts';
import axios from 'axios';
import { useAuth } from '../../contexts/AuthContext.tsx';
import UserListings from './UserListings.tsx';

interface UserProfile {
    id: string;
    name: string;
    email: string;
}

const Profile: React.FC = () => {
    const [user, setUser] = useState<UserProfile | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [showEmail, setShowEmail] = useState(false);
    const navigate = useNavigate();
    const { getAccessToken } = useAuth();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const token = getAccessToken();
                if (!token) throw new Error('No access token found');
                const response = await axios.get(API_ENDPOINTS.PROFILE, {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setUser(response.data);
            } catch (err) {
                setError('Failed to load profile');
            } finally {
                setLoading(false);
            }
        };
        fetchProfile();
    }, [getAccessToken]);

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
        <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
            <Paper elevation={3} sx={{ p: 4, borderRadius: 4, backgroundColor: '#f5f5f5' }}>
                <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <Avatar
                        sx={{
                            width: 96,
                            height: 96,
                            mb: 3,
                            bgcolor: '#AEC761',
                            fontSize: '2.5rem',
                            fontWeight: 500,
                            color: 'white'
                        }}
                    >
                        {user?.name?.[0]?.toUpperCase()}
                    </Avatar>
                    <Typography variant="h5" component="h1" sx={{ mb: 1, fontWeight: 600 }}>
                        {user?.name}
                    </Typography>
                    <Paper variant="outlined" sx={{ p: 3, width: '100%', maxWidth: 480, borderRadius: 3, mb: 3 }}>
                        <Box sx={{ display: 'flex', alignItems: 'center' }}>
                            <Typography variant="subtitle1" sx={{ fontWeight: 500, mr: 2 }}>
                                Email Address
                            </Typography>
                            <Typography variant="body1" color="text.secondary" sx={{ mr: 1 }}>
                                {showEmail ? user?.email : '••••••••••'}
                            </Typography>
                            <IconButton onClick={() => setShowEmail(!showEmail)} size="small">
                                {showEmail ? <VisibilityOffIcon /> : <VisibilityIcon />}
                            </IconButton>
                        </Box>
                    </Paper>
                    <Button
                        variant="outlined"
                        fullWidth
                        sx={{
                            maxWidth: 480,
                            py: 1.5,
                            borderRadius: 2,
                            textTransform: 'none',
                            fontSize: '1rem',
                            fontWeight: 500,
                            color: '#AEC761',
                            borderColor: '#AEC761',
                            '&:hover': { borderColor: '#94A857' }
                        }}
                        onClick={handleChangePassword}
                    >
                        Change Password
                    </Button>
                </Box>
            </Paper>
            <UserListings />
        </Container>
    );
};

export default Profile;
