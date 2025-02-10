import React from "react";
import {Box, Button, TextField} from '@mui/material';
import {Link} from 'react-router-dom';

type FormCardProps = {
    title: string;
    email: string;
    password: string;
    onSubmit: (e: React.FormEvent) => void;
    onChange: (field: string, value: string) => void;
};

export const LoginForm: React.FC<FormCardProps> = ({title, onSubmit, onChange, email, password}) => {
    return (
        <div className="flex justify-center items-center h-screen">
            <div className="absolute inset-0 bg-[#AEC761] opacity-20"></div>
            <Box className="relative p-6 bg-white rounded shadow w-96">
                <img src="/ff-logo.png" alt="Logo" className="absolute top-5 right-2 w-12 h-12" />
                <h3 className="text-4xl mb-7 text-left">{title}</h3>
                <h3 className="text-left font-bold">Username or email</h3>
                <form onSubmit={onSubmit}>
                    <TextField
                        label="Username or email"
                        type="text"
                        value={email}
                        onChange={(e) => onChange('email', e.target.value)}
                        fullWidth
                        margin="dense"
                    />
                    <div className="mt-5">
                        <h3 className="text-left font-bold">Password</h3>
                    </div>
                    <TextField
                        label="Password"
                        type="password"
                        value={password}
                        onChange={(e) => onChange('password', e.target.value)}
                        fullWidth
                        margin="dense"
                    />
                    <div className="flex justify-start mt-5">
                        <Button
                            variant="contained"
                            type="submit"
                            sx={{
                                backgroundColor: '#AEC761',
                                borderRadius: '20px',
                                textTransform: 'none',
                                '&:hover': {
                                    backgroundColor: '#9BBF5A'
                                }
                            }}
                        >
                            Log In
                        </Button>
                    </div>
                    <div className="mt-5 text-center">
                        <p>Don't have an account yet? <Link to="/register" className="text-blue-500">Sign Up</Link></p>
                    </div>
                </form>
            </Box>
        </div>
    );
};