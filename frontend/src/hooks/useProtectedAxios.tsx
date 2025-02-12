import {useContext, useMemo} from "react";
import {AuthContext} from "../contexts/AuthContext.tsx";
import axios from "axios";
import {API_BASE_URL} from "../apiConfig.ts";

export const useProtectedAxios = () => {
    const { getAccessToken } = useContext(AuthContext)!;

    return useMemo(() => {
        const token = getAccessToken();
        console.log('Token:', token);
        return axios.create({
            baseURL: API_BASE_URL,
            headers: {
                Authorization: `Bearer ${getAccessToken()}`,
            },
        });
    }, [getAccessToken]);
};