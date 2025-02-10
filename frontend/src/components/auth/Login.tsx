import React from 'react';
import {useAuth} from "../../contexts/AuthContext.tsx";
import {API_ENDPOINTS} from "../../apiConfig.ts";
import {Loader} from "../loader/Loader.tsx";
import {LoginForm} from "../forms/LoginForm.tsx";

export const Login: React.FC = () => {
    const [loginDto, setLoginDto] = React.useState({
        email: '',
        password: ''
    });

    const { authenticate, loading, error } = useAuth();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        await authenticate(API_ENDPOINTS.LOGIN, loginDto, '/');
    }

    const handleFieldChange = (field: string, value: string) => {
        setLoginDto((prevData: any) => ({
            ...prevData,
            [field]: value
        }));
    };

    return (
        <>
            {loading ? (
                <Loader />
            ) : (
                <LoginForm
                    title="Sign In"
                    email={loginDto.email}
                    password={loginDto.password}
                    onSubmit={handleLogin}
                    onChange={handleFieldChange}
                />
            )}
        </>
    );
}
