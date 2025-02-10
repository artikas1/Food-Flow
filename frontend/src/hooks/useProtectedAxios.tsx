import {useContext, useMemo} from "react";
import {AuthContext} from "../contexts/AuthContext";
import axios from "axios";
import {API_BASE_URL} from "../apiConfig";

export const useProtectedAxios = () => {
    const { getAccessToken } = useContext(AuthContext)!;

    return useMemo(() => {
        return axios.create({
            baseURL: API_BASE_URL,
            headers: {
                Authorization: `Bearer ${getAccessToken()}`,
            },
        });
    }, [getAccessToken]);
};