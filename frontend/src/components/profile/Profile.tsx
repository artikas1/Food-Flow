import React, { useEffect, useState } from 'react';
import { Container, Paper, CircularProgress, Typography, Box } from '@mui/material';
import { useParams } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext.tsx';
import axios from 'axios';
import { API_ENDPOINTS } from '../../apiConfig.ts';
import ProfileHeader from './ProfileHeader.tsx';
import EmailSection from './EmailSection.tsx';
import ChangePasswordButton from './ChangePasswordButton.tsx';
import UserListings from './UserListings.tsx';
import ReviewsSection from './ReviewsSection.tsx';
import ResponsiveAppBar from '../Header.tsx';

interface UserProfile {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
}

const Profile: React.FC = () => {
    const { userId } = useParams<{ userId?: string }>();
    const [user, setUser] = useState<UserProfile | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [isCurrentUser, setIsCurrentUser] = useState(false);
    const { getAccessToken } = useAuth();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const token = getAccessToken();
                const config = {
                    headers: { Authorization: `Bearer ${token}` },
                    withCredentials: true
                };

                if (userId) {
                    const targetUser = await axios.get(API_ENDPOINTS.USER_BY_ID(userId));
                    setUser(targetUser.data);

                    try {
                        const currentUser = await axios.get(API_ENDPOINTS.PROFILE, config);
                        setIsCurrentUser(targetUser.data.id === currentUser.data.id);
                    } catch {
                        setIsCurrentUser(false);
                    }
                } else {
                    try {
                        const response = await axios.get(API_ENDPOINTS.PROFILE, config);
                        setUser(response.data);
                        setIsCurrentUser(true);
                    } catch {
                        setError('Failed to load profile');
                        setIsCurrentUser(false);
                    }
                }
            } catch (err) {
                setError('Failed to load profile');
                setIsCurrentUser(false);
            } finally {
                setLoading(false);
            }
        };

        fetchProfile();
    }, [getAccessToken, userId]);

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
        <div className="flex flex-col w-full h-screen">
            <ResponsiveAppBar onMenuIconClick={() => {}} />
            <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
                <Paper elevation={3} sx={{ p: 4, borderRadius: 4, backgroundColor: '#f5f5f5' }}>
                    <ProfileHeader
                        firstName={user?.firstName || ''}
                        lastName={user?.lastName || ''}
                    />
                    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        {isCurrentUser && (
                            <>
                                <EmailSection email={user?.email || ''} />
                                <ChangePasswordButton />
                            </>
                        )}
                    </Box>
                </Paper>
                <UserListings userId={userId} isCurrentUser={isCurrentUser} />
                {user && <ReviewsSection userId={user.id} targetId={user.id} isCurrentUser={isCurrentUser} />}
            </Container>
        </div>
    );
};

export default Profile;