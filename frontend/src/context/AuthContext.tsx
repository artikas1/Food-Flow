import React, { createContext, useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie'; // Import js-cookie

interface AuthContextProps {
    isAuthenticated: boolean;
    token: string | null;
    authenticate: (url: string, userDto: { username: string; password: string }, redirectPath: string) => Promise<void>;
    logout: () => void;
    getAccessToken: () => string | null;
    error: string | null;
    loading: boolean;
}

export const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [token, setToken] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const storedToken = Cookies.get('token');
        if (storedToken) {
            setToken(storedToken);
            setIsAuthenticated(true);
        }
    }, []);

    const getAccessToken = () => token;

    const authenticate = async (url: string, userDto: { username: string; password: string }, redirectPath: string) => {
        setLoading(true);
        setError(null);

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userDto),
            });

            if (!response.ok) {
                throw new Error('Invalid credentials');
            }

            const data = await response.json();

            Cookies.set('token', data.token, { expires: 7, secure: true, sameSite: 'Strict' });

            setToken(data.token);
            setIsAuthenticated(true);
            navigate(redirectPath);
        } catch (err) {
            setError((err as Error).message);
            setIsAuthenticated(false);
            setToken(null);
        } finally {
            setLoading(false);
        }
    };

    const logout = () => {
        Cookies.remove('token'); // Remove token from cookies
        setToken(null);
        setIsAuthenticated(false);
        navigate('/login');
    };

    return (
        <AuthContext.Provider value={{ isAuthenticated, token, getAccessToken, authenticate, logout, error, loading }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};
