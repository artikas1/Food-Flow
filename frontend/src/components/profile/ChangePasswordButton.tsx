import React, { useState } from 'react'
import { Box, Button, TextField, Typography } from '@mui/material'
import axios from 'axios'
import { API_ENDPOINTS } from '../../apiConfig.ts'
import { useAuth } from '../../contexts/AuthContext.tsx';

const ChangePassword: React.FC = () => {
    const [currentPassword, setCurrentPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [confirmNewPassword, setConfirmNewPassword] = useState('')
    const [error, setError] = useState('')
    const [success, setSuccess] = useState('')
    const { getAccessToken } = useAuth();

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if (newPassword !== confirmNewPassword) {
            setError('New passwords do not match')
            setSuccess('')
            return
        }
        const token = getAccessToken();
        try {
            await axios.put(
                API_ENDPOINTS.CHANGE_PASSWORD,
                { currentPassword, newPassword, confirmNewPassword },
                { headers: { Authorization: `Bearer ${token}` }, withCredentials: true }
            )
            setSuccess('Password changed successfully')
            setError('')
        } catch (err: any) {
            const errorMsg =
                typeof err.response?.data === 'string'
                    ? err.response.data
                    : err.response?.data?.message || 'Error changing password'
            setError(errorMsg)
            setSuccess('')
        }
    }

    return (
        <Box sx={{ maxWidth: 480, mx: 'auto', mt: 4, px: 2 }}>
            <Typography variant="h5" align="center" gutterBottom>
                Change Password
            </Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate>
                <TextField
                    label="Current Password"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    required
                />
                <TextField
                    label="New Password"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    required
                />
                <TextField
                    label="Confirm New Password"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={confirmNewPassword}
                    onChange={(e) => setConfirmNewPassword(e.target.value)}
                    required
                />
                {error && (
                    <Typography color="error" variant="body2" sx={{ mt: 1 }}>
                        {error}
                    </Typography>
                )}
                {success && (
                    <Typography color="primary" variant="body2" sx={{ mt: 1 }}>
                        {success}
                    </Typography>
                )}
                <Button
                    type="submit"
                    variant="contained"
                    fullWidth
                    sx={{
                        mt: 2,
                        py: 1.5,
                        borderRadius: 2,
                        textTransform: 'none',
                        fontSize: '1rem',
                        fontWeight: 500
                    }}
                >
                    Change Password
                </Button>
            </Box>
        </Box>
    )
}

export default ChangePassword
