import React from 'react';
import { Avatar, Typography, Box } from '@mui/material';

interface ProfileHeaderProps {
    firstName: string;
    lastName: string;
}

const ProfileHeader: React.FC<ProfileHeaderProps> = ({ firstName, lastName }) => (
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
            {firstName?.[0]?.toUpperCase()}
        </Avatar>
        <Typography variant="h5" component="h1" sx={{ mb: 1, fontWeight: 600 }}>
            {firstName} {lastName}
        </Typography>
    </Box>
);

export default ProfileHeader;