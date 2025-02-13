import { useState } from "react";
import {useProtectedAxios} from "./useProtectedAxios.tsx";

export const usePostData = (url: string) => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
    const axios = useProtectedAxios();

    const postData = async () => {
        setLoading(true);
        setError(null);
        try {
            await axios.post(url);
            return true;
        } catch (err) {
            setError("Failed to reserve food.");
            return false;
        } finally {
            setLoading(false);
        }
    };

    return { error, loading, postData };
};
