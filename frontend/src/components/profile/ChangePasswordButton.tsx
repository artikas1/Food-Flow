import React from 'react';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const ChangePasswordButton: React.FC = () => {
    const navigate = useNavigate();

    const handleChangePassword = () => {
        navigate('/change-password');
    };

    return (
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
    );
};

export default ChangePasswordButton;