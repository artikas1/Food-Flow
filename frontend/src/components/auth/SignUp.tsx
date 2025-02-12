import React from 'react';
import { useAuth } from "../../contexts/AuthContext.tsx";
import { Loader } from "../loader/Loader.tsx";
import { RegisterForm } from "../forms/SignUpForm.tsx";
import { UserDto } from "../../dtos/UserDto.tsx";
import axios from 'axios';
import {API_ENDPOINTS} from "../../apiConfig.ts";

export const SignUp: React.FC = () => {
    const [userDto, setUserDto] = React.useState<UserDto>({
        email: '',
        password: '',
        name: '',
        surname: '',
        birthDate: '',
        gender: ''
    });
    const { authenticate, loading, error } = useAuth();

    const handleSignUp = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await axios.post(API_ENDPOINTS.REGISTER, userDto, {
                headers: { 'Content-Type': 'application/json' },
            });

            if (response.status !== 200) {
                throw new Error('Registration failed');
            }

            await authenticate(API_ENDPOINTS.LOGIN, userDto, '/');
        } catch (err) {
            console.error(err);
        }
    };

    const handleFieldChange = (field: string, value: string) => {
        setUserDto((prevData) => ({
            ...prevData,
            [field]: value
        }));
    };

    return (
        <>
            {loading && <Loader />}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <RegisterForm
                title="Sign Up"
                userDto={userDto}
                onSubmit={handleSignUp}
                onChange={handleFieldChange}
            />
        </>
    );
};